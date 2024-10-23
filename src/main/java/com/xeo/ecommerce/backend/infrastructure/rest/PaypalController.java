package com.xeo.ecommerce.backend.infrastructure.rest;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.xeo.ecommerce.backend.domain.model.DataPayment;
import com.xeo.ecommerce.backend.domain.model.URLPaypalResponse;
import com.xeo.ecommerce.backend.infrastructure.service.PaypalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/payments")
public class PaypalController {
    private final PaypalService paypalService;
    private final String SUCCESS_URL = "http://localhost:8080/api/v1/payments/success";
    private final String CANCEL_URL = "http://localhost:8080/api/v1/payments/cancel";

    @PostMapping
    public URLPaypalResponse createPayment(@RequestBody DataPayment dataPayment){
       // log.info("dat {}", dataPayment);
        try {
            Payment payment = paypalService.createPayment(
                    Double.valueOf(dataPayment.getAmount()),
                    dataPayment.getCurrency(),
                    dataPayment.getMethod(),
                    "sale",
                    dataPayment.getDescription(),
                    CANCEL_URL,
                    SUCCESS_URL
            );
            for (Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return new URLPaypalResponse( links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        return new URLPaypalResponse("http://localhost:4200") ;

    }
    @GetMapping("/success")
    public RedirectView paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")){
                return new RedirectView("http://localhost:4200/payment/success");
                //return new RedirectView("http://localhost:4200");
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return new RedirectView("http://localhost:4200");
    }

    @GetMapping("/cancel")
    public  RedirectView patmentCancel(){
        return new RedirectView("http://localhost:4200");
    }

}

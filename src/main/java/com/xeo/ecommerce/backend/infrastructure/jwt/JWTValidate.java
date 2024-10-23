package com.xeo.ecommerce.backend.infrastructure.jwt;

import com.xeo.ecommerce.backend.infrastructure.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static com.xeo.ecommerce.backend.infrastructure.jwt.Constants.*;

public class JWTValidate {
    //valida que el token venga en la peticion
    public static boolean tokenExist(HttpServletRequest request, HttpServletResponse response){
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if(header == null || !header.startsWith(TOKEN_BEARER_PREFIX))
            return false;
        return true;
    }

    //valida que el token sea el correcto
    public static Claims JWTValid(HttpServletRequest request){
        String jwtToken = request.getHeader(HEADER_AUTHORIZATION).replace(TOKEN_BEARER_PREFIX, "");
        return Jwts.parserBuilder().setSigningKey(getSignedKey(SUPER_SECRET_KEY)).build()
                .parseClaimsJwt(jwtToken).getBody();
    }

    //autenticar al usuario en el flujo spring
    public static void setAuthenticacion(Claims claims, CustomUserDetailService customUserDetailService){
        UserDetails userDetails = customUserDetailService.loadUserByUsername(claims.getSubject());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null,userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}

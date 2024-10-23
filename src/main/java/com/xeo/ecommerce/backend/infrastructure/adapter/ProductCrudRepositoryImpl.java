package com.xeo.ecommerce.backend.infrastructure.adapter;

import com.xeo.ecommerce.backend.domain.model.Product;
import com.xeo.ecommerce.backend.domain.port.IProductRepository;
import com.xeo.ecommerce.backend.infrastructure.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ProductCrudRepositoryImpl implements IProductRepository {
    private final IProductCrudRepository iProductCrudRepository;
    private final ProductMapper productMapper;


    @Override
    public Product save(Product product) {
        return productMapper.toProduct( iProductCrudRepository.save(productMapper.toProductEntity(product)));
    }

    @Override
    public Iterable<Product> findAll() {
        return productMapper.toProductList( iProductCrudRepository.findAll());
    }

    @Override
    public Product findById(Integer id) {
        return productMapper.toProduct( iProductCrudRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No existe el producto con id: "+id+"no se encuentra")
        ));
    }

    @Override
    public void deleteById(Integer id) {
        iProductCrudRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No existe el producto con id: "+id+"no se encuentra")
        );
        iProductCrudRepository.deleteById(id);
    }
}

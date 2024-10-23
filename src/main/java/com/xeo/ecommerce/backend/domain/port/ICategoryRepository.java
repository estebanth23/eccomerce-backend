package com.xeo.ecommerce.backend.domain.port;

import com.xeo.ecommerce.backend.domain.model.Category;

public interface ICategoryRepository {
    Category save(Category category);
    Category findById(Integer id);
    Iterable<Category> findAll();
    void deleteById(Integer id);
}

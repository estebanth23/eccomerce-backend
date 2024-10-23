package com.xeo.ecommerce.backend.infrastructure.adapter;

import com.xeo.ecommerce.backend.domain.model.Category;
import com.xeo.ecommerce.backend.domain.port.ICategoryRepository;
import com.xeo.ecommerce.backend.infrastructure.mapper.CategoryMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryCrudRepositoryImpl implements ICategoryRepository {

    private final ICategoryCrudRepository iCategoryCrudRepository;
    private final CategoryMapper categoryMapper;

    public CategoryCrudRepositoryImpl(ICategoryCrudRepository iCategoryCrudRepository, CategoryMapper categoryMapper) {
        this.iCategoryCrudRepository = iCategoryCrudRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category save(Category category) {
        return categoryMapper.toCategory(iCategoryCrudRepository.save(categoryMapper.toCategoryEntity(category)));
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.toCategory(iCategoryCrudRepository.findById(id).orElseThrow(
                //funcion lambda
                () -> new RuntimeException("Categoria con id:" +id+ "no existe")
        ));
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryMapper.toCategoryList(iCategoryCrudRepository.findAll());
    }

    @Override
    public void deleteById(Integer id) {
        iCategoryCrudRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Categoria con id:" +id+ "no existe")
        );
        iCategoryCrudRepository.deleteById(id);
    }
}

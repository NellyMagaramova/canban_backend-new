package com.backend.canban.canban_backend.service;

import org.springframework.stereotype.Service;
import com.backend.canban.canban_backend.entity.Category;
import com.backend.canban.canban_backend.repo.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository  repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll(String email){
        return repository.findByUserEmailOrderByTitleAsc(email);
    }

    public Category add(Category category){
        return repository.save(category);
    }

    public Category  update(Category category){
        return repository.save(category);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public List<Category> findByTitle(String text, String email) {
        return repository.findByTitle(text, email);
    }

    public Category findById(Long id){
        System.out.println("< < --------------");
        return repository.findById(id).get();
    }
}

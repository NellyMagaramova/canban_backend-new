package com.backend.canban.canban_backend.controllers;

import com.backend.canban.canban_backend.entity.Category;
import com.backend.canban.canban_backend.service.CategoryService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.backend.canban.canban_backend.search.CategorySearchValues;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;
    public CategoryController(CategoryService  categoryService){
        System.out.println("< < <------------------" + categoryService);
        this.categoryService =  categoryService;
    }

    @PostMapping("/all")
    public List<Category> findAll(@RequestBody  String email){
        return categoryService.findAll(email);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category  category){

        /* проверка на обязательные параметры */
        if(category.getId() != null && category.getId() != 0){
            /* id создается автоматически в БД*/
            return new ResponseEntity("redundant  param: id MUST be null",
                    HttpStatus.NOT_ACCEPTABLE);
        }

        /* если передали пустое значение title */
        if(category.getTitle() == null  ||  category.getTitle().trim().length() == 0){
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(categoryService.add(category) );
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category){
        /* проверка  на обязательные параметры */
        if(category.getId() == null  ||  category.getId() == 0 ){
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        /* если передали пустое значение title */
        if(category.getTitle() == null || category.getTitle().trim().length() == 0 ){
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        categoryService.update(category);
        return  new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){

        try{
            categoryService.deleteById(id);
        }catch(EmptyResultDataAccessException  e){
            e.printStackTrace();
            /*возвращаем ошибку 406*/
            return new ResponseEntity("id=" + id + "not found", HttpStatus.NOT_ACCEPTABLE);
        }

        //отправляем статус 200
        return new ResponseEntity(HttpStatus.OK);
    }

    // поиск по любым параметрам CategorySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues) {

        // проверка на обязательные параметры
        if (categorySearchValues.getEmail() == null || categorySearchValues.getEmail().trim().length() == 0) {
            return new ResponseEntity("missed param: email", HttpStatus.NOT_ACCEPTABLE);
        }

        // поиск категорий пользователя по названию
        List<Category> list = categoryService.findByTitle(categorySearchValues.getTitle(),
                categorySearchValues.getEmail());

        return ResponseEntity.ok(list);
    }

    @PostMapping("/id")
    public ResponseEntity<Category> findById(@RequestBody Long id){
        Category  category = null;
        try{
            category = categoryService.findById(id);
        }catch(NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity("id=" + id + "not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(category);
    }


}

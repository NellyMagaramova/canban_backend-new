package com.backend.canban.canban_backend.controllers;


import com.backend.canban.canban_backend.service.PriorityService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.canban.canban_backend.entity.Priority ;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.backend.canban.canban_backend.search.PrioritySearchValues;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/priority")
public class PriorityController {
    private PriorityService  priorityService;

    public PriorityController(PriorityService  priorityService){
        this.priorityService = priorityService;
    }
    //---------------------------------------------------------------------------
    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority){

        if(priority.getId() != null  &&  priority.getId() !=0 ){
            return new ResponseEntity( "redundant param: id MUST be null",
                    HttpStatus.NOT_ACCEPTABLE);
        }

        /*если передали пустое значение title*/
        if(priority.getTitle() == null  ||  priority.getTitle().trim().length() == 0 ){
            return new ResponseEntity("missed param: title",
                    HttpStatus.NOT_ACCEPTABLE);
        }

        /*если передали пустое значение color*/
        if(priority.getColor() == null  ||  priority.getColor().trim().length() == 0 ){
            return new ResponseEntity("missed param: color",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priorityService.add(priority));
    }
    //--------------------------------------------------------------------------
    @PostMapping("/all")
    public List<Priority> findAll(@RequestBody String email){
        return priorityService.findAll(email);
    }
    //---------------------------------------------------------------------------
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody  Priority priority)
    {
        if(priority.getId() == null || priority.getId() == 0 ){
            return new ResponseEntity("missed param: id",
                    HttpStatus.NOT_ACCEPTABLE);
        }

        if(priority.getTitle() == null || priority.getTitle().trim().length() == 0){
            return new ResponseEntity("missed param: title",
                    HttpStatus.NOT_ACCEPTABLE);
        }

        if(priority.getColor() == null || priority.getColor().trim().length() == 0){
            return new ResponseEntity("missed  param: color",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        priorityService.update(priority);
        return new ResponseEntity(HttpStatus.OK);
    }
    //---------------------------------------------------------------------------
    @PostMapping("/id")
    public ResponseEntity<Priority> findById(@RequestBody Long id)
    {
        Priority priority = null;
        try{
            priority = priorityService.findById(id);
        }catch(NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity("id=" + id + "not found",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priority);
    }
//---------------------------------------------------------------------------
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id ){
        try{
            priorityService.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id=" + id + "not found",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    //---------------------------------------------------------------------------
    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues  pSearchValues)
    {
        if(pSearchValues.getEmail() == null  ||
                pSearchValues.getEmail().trim().length() == 0){
            return new ResponseEntity("missed  param: email",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priorityService.find( pSearchValues.getTitle(),
                pSearchValues.getEmail() ));
    }
    //---------------------------------------------------------------------------

}

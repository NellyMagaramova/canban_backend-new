package com.backend.canban.canban_backend.service;

import javax.transaction.Transactional;
/* import jakarta.transaction.Transactional;*/
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.backend.canban.canban_backend.entity.Task;
import com.backend.canban.canban_backend.repo.TaskRepository;
import java.util.List;

import java.util.Date;

@Service
@Transactional
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {

        this.repository = repository;
    }

    public List<Task> findAll(String email) {
        return repository.findByUserEmailOrderByTitleAsc(email);
    }




    public Task add(Task task) {
        return repository.save(task);
    }

    public Task update(Task task) {
        return repository.save(task);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public Page<Task> findByParams(String text,  Boolean completed, Long priorityId, Long categoryId, String email, Date dateFrom, Date dateTo, PageRequest paging) {
        return repository.findByParams(text, completed, priorityId, categoryId, email, dateFrom, dateTo, paging);
    }




    public Task findById(Long id) {
        return repository.findById(id).get();
    }

}

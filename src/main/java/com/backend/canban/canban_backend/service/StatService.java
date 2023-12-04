package com.backend.canban.canban_backend.service;


import javax.transaction.Transactional;
/* import jakarta.transaction.Transactional;*/
import org.springframework.stereotype.Service;
import com.backend.canban.canban_backend.repo.StatRepository;
import com.backend.canban.canban_backend.entity.Stat;
import java.util.List;

@Service
@Transactional
public class StatService {
    private final StatRepository repository; // сервис имеет право обращаться к репозиторию (БД)
    public StatService(StatRepository repository) {
        this.repository = repository;
    }
    public Stat findStat(String email) {
        return repository.findByUserEmail(email);
    }
}

package com.backend.canban.canban_backend.repo;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.backend.canban.canban_backend.entity.Stat;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends CrudRepository<Stat, Long>{
    Stat findByUserEmail(String email);
}

package com.backend.canban.canban_backend.repo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.backend.canban.canban_backend.entity.Priority;

@Repository
public interface PriorityRepository  extends JpaRepository<Priority, Long>{

    /* Поиск всех значений данного пользователя */
    List<Priority> findByUserEmailOrderByIdAsc(String email);

    @Query("SELECT p FROM Priority p where " +
            "(:title is null or :title='' " +
            " or lower(p.title) like lower(concat('%', :title, '%')) )" +
            " and p.user.email=:email " +
            "order  by  p.title asc")
    List<Priority> findByTitle(@Param("title") String title,
                               @Param("email") String email);

}

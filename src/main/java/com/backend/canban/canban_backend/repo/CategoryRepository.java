package com.backend.canban.canban_backend.repo;

import com.backend.canban.canban_backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
    List<Category> findByUserEmailOrderByTitleAsc(String  email);

    /* поиск значений по названию для конкретного пользователя */
    /* сортировка по названию */
    @Query("SELECT c FROM Category c where " +
        "(:title is null  or :title='' " +
        " or lower(c.title) like  lower(concat('%', :title, '%' ))) " +
        " and c.user.email=:email " +
        " order by c.title asc")
    List<Category> findByTitle(@Param("title") String title,
                               @Param("email") String email);


}

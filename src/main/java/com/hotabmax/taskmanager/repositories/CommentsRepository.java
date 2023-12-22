package com.hotabmax.taskmanager.repositories;

import com.hotabmax.taskmanager.entities.Comments;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from comments where email = :email",
            nativeQuery = true)
    void deleteByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "delete from comments where taskid = :taskid",
            nativeQuery = true)
    void deleteByTaskId(int taskid);

    @Query(value = "select * from comments where taskid = :taskid",
            nativeQuery = true)
    List <Comments> findByTaskId(int taskid);
}

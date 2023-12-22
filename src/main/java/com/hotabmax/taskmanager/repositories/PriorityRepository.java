package com.hotabmax.taskmanager.repositories;


import com.hotabmax.taskmanager.entities.Priority;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PriorityRepository extends JpaRepository<Priority, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from priority where name = :name",
            nativeQuery = true)
    void deleteByName(String name);

    @Query(value = "select id, name from priority where name = :name",
            nativeQuery = true)
    Priority findByName(String name);
}

package com.hotabmax.taskmanager.repositories;


import com.hotabmax.taskmanager.entities.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StatusRepository extends JpaRepository<Status, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from status where name = :name",
            nativeQuery = true)
    void deleteByName(String name);

    @Query(value = "select id, name from status where name = :name",
            nativeQuery = true)
    Status findByName(String name);
}

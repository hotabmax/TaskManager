package com.hotabmax.taskmanager.repositories;

import com.hotabmax.taskmanager.entities.UserRoles;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from userroles where name = :name",
            nativeQuery = true)
    void deleteByName(String name);

    @Query(value = "select * from userroles where name = :name",
            nativeQuery = true)
    UserRoles findByName(String name);
}

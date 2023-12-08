package com.hotabmax.taskmanager.repositories;

import com.hotabmax.taskmanager.models.Tasks;
import com.hotabmax.taskmanager.models.UserRoles;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from userroles where name = :name",
            nativeQuery = true)
    void deleteByName(String name);

    @Query(value = "select id, name from userroles where name = :name",
            nativeQuery = true)
    UserRoles findByName(String name);
}

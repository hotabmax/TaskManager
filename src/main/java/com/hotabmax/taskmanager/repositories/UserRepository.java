package com.hotabmax.taskmanager.repositories;

import com.hotabmax.taskmanager.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from userdata where userroleid = :userroleid",
            nativeQuery = true)
    List<User> findByRoleId(int userroleid);

    @Query(value = "select * from userdata where email = :email",
            nativeQuery = true)
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "delete from userdata where email = :email",
            nativeQuery = true)
    void deleteByEmail(String email);
}

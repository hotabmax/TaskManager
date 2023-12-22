package com.hotabmax.taskmanager.repositories;

import com.hotabmax.taskmanager.entities.Tasks;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks, Long> {

    @Query(value = "select * from tasks offset :myOffset limit :myLimit",
            nativeQuery = true)
    List<Tasks> findAll(int myOffset, int myLimit);
    @Query(value = "select * from tasks where customerid = :customerid offset :myOffset limit :myLimit",
            nativeQuery = true)
    List<Tasks> findByCustomerId(int myOffset, int myLimit, int customerid);

    @Query(value = "select * from tasks where executorid = :executorid offset :myOffset limit :myLimit",
            nativeQuery = true)
    List<Tasks> findByExecutorId(int myOffset, int myLimit, int executorid);

    @Query(value = "select * from tasks where name = :name",
            nativeQuery = true)
    Tasks findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "delete from tasks where name = :name",
            nativeQuery = true)
    void deleteByName(String name);

    @Modifying
    @Transactional
    @Query(value = "update tasks set name = :newName where name = :oldName",
            nativeQuery = true)
    void tranzactionEditName(String oldName, String newName);

    @Modifying
    @Transactional
    @Query(value = "update tasks set description = :newDescription where name = :name",
            nativeQuery = true)
    void tranzactionEditDescription(String name, String newDescription);

    @Modifying
    @Transactional
    @Query(value = "update tasks set statusid = :newStatusid where name = :name",
            nativeQuery = true)
    void tranzactionEditStatus(String name, int newStatusid);

    @Modifying
    @Transactional
    @Query(value = "update tasks set priorityid = :newPriorityid where name = :name",
            nativeQuery = true)
    void tranzactionEditPriority(String name, int newPriorityid);

    @Modifying
    @Transactional
    @Query(value = "update tasks set executorid = :newExecutorid where name = :name",
            nativeQuery = true)
    void tranzactionEditExecutor(String name, int newExecutorid);
}

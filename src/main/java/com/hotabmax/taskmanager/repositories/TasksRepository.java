package com.hotabmax.taskmanager.repositories;

import com.hotabmax.taskmanager.models.Tasks;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks, Long> {
    @Query(value = "select id, name, description, statusid, priorityid, customerid, executorid from tasks where customerid = :customerid",
            nativeQuery = true)
    List<Tasks> findByCustomerId(int customerid);

    @Query(value = "select id, name, description, statusid, priorityid, customerid, executorid from tasks where executorid = :executorid",
            nativeQuery = true)
    List<Tasks> findByExecutorId(int executorid);

    @Query(value = "select id, name, description, statusid, priorityid, customerid, executorid from tasks where name = :name",
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

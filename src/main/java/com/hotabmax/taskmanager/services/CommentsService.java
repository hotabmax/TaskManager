package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.models.Comments;
import com.hotabmax.taskmanager.models.Tasks;
import com.hotabmax.taskmanager.repositories.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;


    public CommentsService() {
    }

    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public void createComment(Comments comment) {
        commentsRepository.save(comment);
    }

    public void deleteAll() {
        commentsRepository.deleteAll();
    }

    public void deleteByEmail(String email) {
        commentsRepository.deleteByEmail(email);
    }

    public void deleteByTaskId(int taskid) {
        commentsRepository.deleteByTaskId(taskid);
    }
    public List<Comments> findByTaskId(int taskid){
        return commentsRepository.findByTaskId(taskid);
    }

    public List<Comments> findAll() {
        return commentsRepository.findAll();
    }


}

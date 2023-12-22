package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.dtos_database.CommentResponse;
import com.hotabmax.taskmanager.dtos_database.CreateCommentRequest;
import com.hotabmax.taskmanager.exceptions_database.CommentsNotFoundException;
import com.hotabmax.taskmanager.entities.Comments;
import com.hotabmax.taskmanager.entities.User;
import com.hotabmax.taskmanager.repositories.CommentsRepository;
import com.hotabmax.taskmanager.repositories.TasksRepository;
import com.hotabmax.taskmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final TasksRepository tasksRepository;

    public CommentsService(CommentsRepository commentsRepository, UserRepository userRepository, TasksRepository tasksRepository) {
        this.commentsRepository = commentsRepository;
        this.userRepository = userRepository;
        this.tasksRepository = tasksRepository;
    }

    @Transactional
    public void createComment(CreateCommentRequest commentRequest, String email) {
        User user = userRepository.findByEmail(email);
        commentsRepository.save(new Comments(
                user.getLastname(), user.getFirstname(), user.getSurname(),
                user.getEmail(), commentRequest.getComment(),
                tasksRepository.findByName(commentRequest.getTaskName()).getId())
        );
    }

    @Transactional
    public void deleteAll() {
        commentsRepository.deleteAll();
    }

    @Transactional
    public void deleteByEmail(String email) {
        commentsRepository.deleteByEmail(email);
    }

    @Transactional
    public void deleteByTaskId(int taskid) {
        commentsRepository.deleteByTaskId(taskid);
    }
    @Transactional
    public List<CommentResponse> findByTaskName(String task) throws CommentsNotFoundException {
        List<Comments> comments = commentsRepository.findByTaskId(tasksRepository.findByName(task).getId());
        if (!comments.isEmpty()){
            List<CommentResponse> commentResponses = new ArrayList<>();
            for(Comments c : comments){
                commentResponses.add(new CommentResponse(
                        c.getLastname(),c.getFirstname(), c.getSurname(), c.getEmail(), c.getComment(),
                        tasksRepository.findById((long) c.getTaskid()).get().getName()
                ));
            }
            return commentResponses;
        } else {
            throw new CommentsNotFoundException();
        }
    }

    @Transactional
    public List<Comments> findAll() {
        return commentsRepository.findAll();
    }


}

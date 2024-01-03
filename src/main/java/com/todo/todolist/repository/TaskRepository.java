package com.todo.todolist.repository;

import com.todo.todolist.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {
//    List<Task> findByUserInfoId(String userId);

    Optional<List<Task>> findByUserId(String id);

}

package com.todo.todolist.controller;

import com.todo.todolist.config.UserInfoDetailsService;
import com.todo.todolist.models.Task;
import com.todo.todolist.models.UserInfo;
import com.todo.todolist.repository.TaskRepository;
import com.todo.todolist.repository.UserInfoRepository;
import com.todo.todolist.security.JwtTokenProvider;
import com.todo.todolist.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {


    private final TaskService taskService;

    @PostMapping("/add")
    public Task addTaskByUserId(@RequestBody Task task, @RequestHeader("Authorization") String token){

        return taskService.addTaskById(task, token);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks(@RequestHeader ("Authorization") String token ){
        return taskService.getAllTaskByToken(token);
    }
    @GetMapping("/tasks/{id}")
    public Optional<Task> getTaskById(@PathVariable(value = "id" ) String id) {
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/{id}")
    public Task deleteTaskById(@PathVariable(value = "id") String id) {
        return taskService.deleteTaskById(id);
    }

    @PutMapping("/{id}")
    public Optional<Task> updateTaskById(@PathVariable(value = "id") String id, @RequestBody Task task) {
        return taskService.updateTaskById(id,task);

    }
    @PutMapping("/comp/{id}")
    public Optional<Task> updateTaskByCompleted(@PathVariable(value = "id") String id) {
        return taskService.updateTaskByCompleted(id);
    }

}

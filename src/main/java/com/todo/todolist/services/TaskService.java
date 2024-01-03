package com.todo.todolist.services;

import com.todo.todolist.config.UserInfoDetailsService;
import com.todo.todolist.models.Task;
import com.todo.todolist.repository.TaskRepository;
import com.todo.todolist.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    public static final String SECRET = "34743677397A24432646294A404E635266556A586E327235753878214125442A";
    private final TaskRepository taskRepository;
    @Autowired
    private final UserInfoDetailsService userInfoDetailsService;

    public Task addTaskById(Task task, String token) {
        String userName = JwtTokenProvider.extractUserId(token, SECRET);
        String userId = userInfoDetailsService.getIdByUserName(userName);
        String id = task.key + System.currentTimeMillis();
        task.setId(id);
        task.setUserId(userId);
        taskRepository.save(task);
        return task;
    }

    public List<Task> getAllTaskByToken(String token) {
        String userName = JwtTokenProvider.extractUserId(token, SECRET);
        String userId = userInfoDetailsService.getIdByUserName(userName);
        return taskRepository.findByUserId(userId).orElse(new ArrayList<>());
    }

    public Task deleteTaskById(String id){
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + id));

        taskRepository.deleteById(id);
        return existingTask;
    }

    public Optional<Task> updateTaskById(String id, Task updatedTask) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .map(task -> {
                    // Update the task with new information
//                    if(task.isCompleted() != updatedTask.isCompleted()) task.setCompleted(updatedTask.isCompleted());
                    if(updatedTask.getMessage() != null) task.setMessage(updatedTask.getMessage());
                    if(updatedTask.getPriority() != null) task.setPriority(updatedTask.getPriority());

                    // Save the updated task to the repository
                    taskRepository.save(task);

                    return task;
                });

    }


    public Optional<Task> updateTaskByCompleted(String id) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .map(task -> {
                    // Update the task with new information
                    task.setCompleted(!task.isCompleted());

                    // Save the updated task to the repository
                    taskRepository.save(task);

                    return task;
                });
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findAll().stream().filter(task -> task.getId().equals(id)).findFirst().map(task -> {return task;});
    }

}

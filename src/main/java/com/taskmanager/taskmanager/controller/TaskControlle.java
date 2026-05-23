package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.Task;
import com.taskmanager.taskmanager.User;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")

public class TaskControlle {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // GET all tasks for logged in user
    @GetMapping
    public List<Task> getAllTasks(Authentication authentication) {
        User user = userRepository.findByUsername(
                authentication.getName()).get();
        return taskRepository.findByUser(user);
    }

    // POST create a new task
    @PostMapping
    public Task createTask(@RequestBody Task task,
                           Authentication authentication) {
        User user = userRepository.findByUsername(
                authentication.getName()).get();
        task.setUser(user);
        return taskRepository.save(task);
    }

    // PUT update a task
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id,
                                        @RequestBody Task taskDetails,
                                        Authentication authentication) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());

        return ResponseEntity.ok(taskRepository.save(task));
    }

    // DELETE a task
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id,
                                        Authentication authentication) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.delete(task);
        return ResponseEntity.ok("Task deleted successfully");
    }
}





package com.taskmanager.taskmanager.repository;

import com.taskmanager.taskmanager.Task;
import com.taskmanager.taskmanager.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByUser(User user);

}

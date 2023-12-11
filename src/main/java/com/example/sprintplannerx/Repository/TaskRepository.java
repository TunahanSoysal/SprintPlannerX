package com.example.sprintplannerx.Repository;

import com.example.sprintplannerx.Entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

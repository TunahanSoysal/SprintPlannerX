package com.example.sprintplannerx.Repository;

import com.example.sprintplannerx.Entities.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    Optional<TaskComment>findByUserName(String username);

}

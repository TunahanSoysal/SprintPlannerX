package com.example.sprintplannerx.Repository;

import com.example.sprintplannerx.Entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT COUNT(*) FROM tasks WHERE developer_id = (SELECT id FROM users WHERE username = :username) AND status = 'done'", nativeQuery = true)
    int getDoneTaskCountForUser(@Param("username") String username);

    @Query(value = "SELECT COUNT(*) FROM tasks WHERE developer_id = (SELECT id FROM users WHERE username = :username) AND status = 'todo'", nativeQuery = true)
    int getToDoTaskCountForUser(@Param("username") String username);

    @Query(value = "SELECT COUNT(*) FROM tasks WHERE developer_id = (SELECT id FROM users WHERE username = :username) AND status = 'overdue'", nativeQuery = true)
    int getOverdueTaskCountForUser(@Param("username") String username);

}

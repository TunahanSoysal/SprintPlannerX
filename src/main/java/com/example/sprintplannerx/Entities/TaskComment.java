package com.example.sprintplannerx.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TaskComment {
    @Id
    @GeneratedValue
    private Long Id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task Task;
}

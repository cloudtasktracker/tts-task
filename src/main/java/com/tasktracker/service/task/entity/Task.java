package com.tasktracker.service.task.entity;

import java.io.Serializable;

/**
 *
 * Created by u6019943 on 09/07/2015.
 */
public class Task implements Serializable{
    private Long id;
    private String name;

    public Task(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

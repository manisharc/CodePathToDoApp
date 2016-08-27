package com.example.chmanish.todoapp;

import java.io.Serializable;

/**
 * Created by chmanish on 8/27/16.
 */
public class itemRecord implements Serializable {

    // Do we really need this
    private static final long serialVersionUID = 5177222050535318633L;

    public Long _id; // for cupboard
    // Task Description
    public String taskDescription;
    // Task Priority
    public Enum<priority> taskPriority;
    // Task Due Date


    public static enum priority {
        LOW, MEDIUM, HIGH;
    }

    public itemRecord(){
        this.taskDescription="";
        this.taskPriority = priority.LOW;
    }
    public itemRecord(String name){
        this.taskDescription = name;
        this.taskPriority = priority.LOW;
    }

    public Enum<priority> getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(Enum<priority> priority) {
        this.taskPriority = priority;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String name){
        this.taskDescription = name;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

}

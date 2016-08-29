package com.example.chmanish.todoapp;

import java.io.Serializable;

import nl.qbusict.cupboard.annotation.Column;

/**
 * Created by chmanish on 8/27/16.
 */
public class itemRecord implements Serializable {

    // Do we really need this
    private static final long serialVersionUID = 5177222050535318633L;

    public Long _id; //
    // Task Description
    @Column("taskDescription")
    public String taskDescription;

    // Task Priority
    @Column("taskPriority")
    public int taskPriority;

    public static int LOW_TO_INT = 2;
    public static int MEDIUM_TO_INT = 1;
    public static int HIGH_TO_INT = 0;
   /* // Task Due Date
    public static enum priority {
        LOW, MEDIUM, HIGH;
    }*/

    public itemRecord(){
        this.taskDescription="";
        this.taskPriority = LOW_TO_INT;
    }
    public itemRecord(String name){
        this.taskDescription = name;
        this.taskPriority = LOW_TO_INT;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int priority) {
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

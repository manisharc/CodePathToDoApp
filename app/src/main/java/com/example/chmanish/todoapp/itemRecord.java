package com.example.chmanish.todoapp;

import java.io.Serializable;

import nl.qbusict.cupboard.annotation.Column;

/**
 * The task is stored in this form in the databse.
 */
public class itemRecord implements Serializable {

    public Long _id;
    // Task Description
    @Column("taskDescription")
    public String taskDescription;

    // Task Priority
    @Column("taskPriority")
    public int taskPriority;

    @Column("taskDueDateYear")
    public int taskDueDateYear;

    @Column("taskDueDateMonth")
    public int taskDueDateMonth;

    @Column("taskDueDateDay")
    public int taskDueDateDay;

    public static int LOW_TO_INT = 2;
    public static int MEDIUM_TO_INT = 1;
    public static int HIGH_TO_INT = 0;

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

    public void setDate(int y, int m, int d){
        this.taskDueDateDay = d;
        this.taskDueDateMonth= m;
        this.taskDueDateYear = y;
    }

    public int getTaskDueDateYear(){
        return this.taskDueDateYear;
    }

    public int getTaskDueDateMonth(){
        return this.taskDueDateMonth;

    }

    public int getTaskDueDateDay(){
        return this.taskDueDateDay;
    }
}

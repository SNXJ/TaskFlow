package com.snxj.taskflow;


public interface Tasker {

    /**
     * 执行任务
     *
     * @param current 当前
     */
    void doTask(Event current);

}
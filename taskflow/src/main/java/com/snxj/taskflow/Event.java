package com.snxj.taskflow;


public interface Event {

    /**
     * id
     *
     * @return 当前id
     */
    int getId();

    /**
     * 任务完成时触发
     */
    void onCompleted();

}

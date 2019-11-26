package com.snxj.taskflow;


public class TaskEvent implements Event {


    private int nodeId;

    private Tasker tasker;

    private TaskCallBack callBack;

    public static TaskEvent build(int nodeId, Tasker tasker) {
        return new TaskEvent(nodeId, tasker);
    }

    public TaskEvent(int nodeId, Tasker tasker) {
        this.nodeId = nodeId;
        this.tasker = tasker;
    }

    void doWork(TaskCallBack callBack) {
        this.callBack = callBack;
        tasker.doTask(this);
    }

    void removeCallBack() {
        this.callBack = null;
    }

    @Override
    public int getId() {
        return nodeId;
    }

    @Override
    public void onCompleted() {
        if (null != callBack) {
            callBack.onTaskCompleted();
        }
    }

    @Override
    public String toString() {
        return "nodeId : " + getId();
    }

//    interface TaskCallBack {
//
//        void onTaskCompleted();
//
//    }
}

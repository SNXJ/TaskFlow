package com.snxj.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.snxj.demo.utils.HttpCallBack;
import com.snxj.demo.utils.Utils;
import com.snxj.taskflow.Event;
import com.snxj.taskflow.TaskFlow;
import com.snxj.taskflow.Tasker;
import com.snxj.taskflow.TaskEvent;


public class AfterActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_H5 = 1;

    /**
     * 初次广告弹框
     */
    private static final int NODE_FIRST_AD = 10;

    /**
     * 初次进入h5页
     */
    private static final int NODE_CHECK_H5 = 20;

    /**
     * 初次进入的注册协议
     */
    private static final int NODE_REGISTER_AGREEMENT = 30;

    private TaskFlow taskFlow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        ((TextView) findViewById(R.id.tv_desc)).setText("使用后");
        startTaskFlow();
    }

    private void startTaskFlow() {
        taskFlow = new TaskFlow.Builder()
                .withNode(getFirstAdNode())
                .withNode(getShowRegisterAgreementNode())
                .withNode(getShowH5Node())
                .create();
        taskFlow.start();
    }

    private TaskEvent getFirstAdNode() {
        return TaskEvent.build(NODE_FIRST_AD, new Tasker() {
            @Override
            public void doTask(final Event current) {
                Utils.fakeRequest("http://www.api1.com", new HttpCallBack() {
                    @Override
                    public void onOk() {
                        new AlertDialog.Builder(AfterActivity.this)
                                .setTitle("这是一条有态度的广告")
                                .setPositiveButton("我看完了", null)
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        //仅仅只需关心自己是否完成，下一个节点会自动执行
                                        current.onCompleted();
                                    }
                                }).create().show();
                    }

                    @Override
                    public void onFailure() {
                        //仅仅只需关心自己是否完成，下一个节点会自动执行
                        current.onCompleted();
                    }
                });
            }
        });
    }

//    Tasker worker= new Tasker() ;


    private TaskEvent getShowRegisterAgreementNode() {
        return TaskEvent.build(NODE_REGISTER_AGREEMENT, new Tasker() {
            @Override
            public void doTask(final Event current) {
                Utils.fakeRequest("http://www.api2.com", new HttpCallBack() {
                    @Override
                    public void onOk() {
                        new AlertDialog.Builder(AfterActivity.this)
                                .setTitle("这是注册协议")
                                .setPositiveButton("我看完了", null)
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        current.onCompleted();
                                    }
                                }).create().show();
                    }

                    @Override
                    public void onFailure() {
                        current.onCompleted();
                    }
                });
            }
        });
    }

    private TaskEvent getShowH5Node() {
        return (TaskEvent.build(NODE_CHECK_H5, new Tasker() {
            @Override
            public void doTask(final Event current) {
                Utils.fakeRequest("http://www.api3.com", new HttpCallBack() {
                    @Override
                    public void onOk() {
                        startActivityForResult(new Intent(AfterActivity.this, TestH5Activity.class), REQUEST_CODE_H5);
                    }

                    @Override
                    public void onFailure() {
                        current.onCompleted();
                    }
                });
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_H5:
                taskFlow.continueWork();
                break;
            default:
                break;
        }
    }
}

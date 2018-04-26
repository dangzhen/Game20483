package com.example.apple.game2048;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //创建一个线程
        new Thread(new Runnable() {       //实现runnable接口
            @Override
            public void run() {
                try{
                    //调用sleep()函数，等待2000
                    Thread.sleep(2000);
                    //通过intent，跳转到登录界面
                    Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

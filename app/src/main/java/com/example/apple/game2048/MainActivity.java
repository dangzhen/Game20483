package com.example.apple.game2048;

import android.app.AlertDialog;
import android.content.DialogInterface;
//import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvScore;  //成绩
    private static MainActivity mainActivity = null;    //在外界能访问到mainActivity的实例
    private int score = 0;
    private ImageView imageView;
    private GameView gameView;

    //外界可以访问
    public static MainActivity getMainActivity(){
        return mainActivity;
    }

    public MainActivity(){
        mainActivity = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView) findViewById(R.id.tvScore);
        imageView = (ImageView) findViewById(R.id.img_head);
        gameView = (GameView) findViewById(R.id.gameView);
    }

    public void onClick(View view){
        switch (view.getId()){
            //帮助按钮，弹出对话框
            case R.id.btn_help:
                //animation= AnimationUtils.loadAnimation(this,);
                AlertDialog.Builder dialog2=new AlertDialog.Builder(this);
                dialog2.setTitle("小喵咪都会的游戏喔");
                dialog2.setMessage("上滑~下滑~左滑~右滑~over！！");
                dialog2.setPositiveButton("我再看看", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog2.setNegativeButton("继续玩儿", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog2.show();
                break;

            //退出按钮点击事件
            case R.id.btn_quit:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("小喵咪的挽留啊");
                dialog.setMessage("喵了个咪的，小可爱你真的要离开我了么？");
                dialog.setPositiveButton("是的呢", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                });
                dialog.setNegativeButton("手滑了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
    }
    //清除分数
    public void clearScore(){
        score=0;
        showScore();
    }

    //展示分数
    public void showScore(){
        tvScore.setText(score + "");
    }

    //添加分数
    public void addScore(int s){
        score += s;
        showScore();
    }
}

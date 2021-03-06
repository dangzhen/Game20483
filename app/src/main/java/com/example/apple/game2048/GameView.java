package com.example.apple.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 18/4/18.
 */

//继承网格布局
public class GameView extends GridLayout {
    //定义一个二维数组来记录这些卡片
    private Card[][] cardsMap = new Card[4][4];
    //用来存放cardsMap下标用的Point类的集合，方便随机取，空点的位置
    private List<Point> emptyPoints = new ArrayList<>();

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }


    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGameView();
    }


    //初始化游戏
    private void initGameView() {

        setColumnCount(4);      //设置列数
        setBackgroundColor(0xffddada0);      //设置FrameLayout的背景颜色

        //设置监听器
        setOnTouchListener(new OnTouchListener() {
            //添加触摸事件
            private float startX,startY,offsetX,offsetY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {      //添加触发事件
                switch(motionEvent.getAction()){
                    //向下
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;
                    //向上
                    case MotionEvent.ACTION_UP:
                        //大于0则代表向右滑
                        offsetX = motionEvent.getX() - startX;
                        //小于0则代表向上滑
                        offsetY = motionEvent.getY() - startY;

                        //比较XY方向的偏移量
                        if (Math.abs(offsetX) >Math.abs(offsetY)){
                            if (offsetX > 5){
                                swipeRight();
                            }else if (offsetX < -5){
                                swipeLeft();
                            }
                        } else {
                            if (offsetY > 5) {
                                swipeDown();
                            }else if(offsetY < 5){
                                swipeUp();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    //适应
    protected void onSizeChanged(int w,int h, int oldw, int oldh){
        //该方法就是宽高发生改变的时候我们可以得到当前的宽高是多少
        //该方法也是在游戏一被创建的时候就调用，也就是用来初始宽高的方法
        super.onSizeChanged(w,h,oldw,oldh);

        //获取手机较窄的长度，-10是用来间隔每个卡片的距离，用手机的宽除以4就是每个卡片的长度了
        int cardWidth = (Math.min(w,h) - 10)/4;
        //添加卡片
        addCards(cardWidth,cardWidth);
        //开始游戏
        startGame();
    }

    //开始游戏，初始化16个Card
    private void startGame() {
        MainActivity.getMainActivity().clearScore();
        //对所有的值进行清理
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }
        //添加两个随机数
        addRondomNum();
        addRondomNum();
    }

    //增加卡片，形成4*4的矩阵
    private void addCards(int cardWidth, int cardHeight) {
        Card c;
        //一行一行添加进来
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());    //创建卡片
                c.setNum(0);    //数字初始化
                addView(c, cardWidth, cardHeight);     //添加到当前GameView中
                //顺便把初始化时新建的卡片存放在新建的二维数组中
                cardsMap[x][y] = c;
            }
        }


    }

    //添加随机数方法
    private  void addRondomNum(){
        //把这个point清空，每次调用添加随机数时就清空之前的emptyPoints
        emptyPoints.clear();
        //对所有的位置进行遍历：即为每个卡片加上了可以控制的Point
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() <= 0) {      //空点
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        //通过随机的从存放了的Point的List集合里去获取Card的位置，并给这个card设置文本属性，并且只能存2或
        //通过point对象来充当下标的角色来控制存放card的二维数组cardsMap，然后随机给定位到的card对象赋值
        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }

    //结束游戏
    private void endGame(){
        //判断卡片是否铺满的标志来量
        boolean isfull = true;

        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                if (cardsMap[x][y].getNum()==0
                        |(x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))
                        ||(x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))
                        ||(y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))
                        ||(y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
                    isfull=false;
                    break;
                }
            }
        }
        if (isfull){
            AlertDialog.Builder dialog=new AlertDialog.Builder(getContext());
            dialog.setTitle("Game Over");
            dialog.setMessage("你输了，还要再来一次吗？");
            dialog.setCancelable(false);

            dialog.setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }

    }

    //右移
    private void swipeRight() {
        boolean add = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            add = true;
                            break;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(
                                    cardsMap[x][y].getNum());
                            add = true;
                        }
                        break;
                    }
                }
            }
        }

        if (add){
            addRondomNum();
        }else{
            endGame();

        }
    }

    //左移
    private void swipeLeft(){
        boolean add=false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNum() > 0) {//除了第一列以外的数，如果存在一个数大于0
                        if (cardsMap[x][y].getNum() <= 0) {//如果左边没有数
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());//将右边的数移到左边
                            cardsMap[x1][y].setNum(0);//右边数清零
                            x--;
                            add=true;
                            break;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(
                                    cardsMap[x][y].getNum());
                            add=true;
                        }
                        break;
                    }
                }
            }
        }
        if (add) {
            addRondomNum();
            endGame();//判断是否结束
        }
                        }

    //下移
    private  void swipeDown(){
        boolean add=false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            add=true;
                            break;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(
                                    cardsMap[x][y].getNum());
                            add=true;
                        }
                        break;
                    }
                }
            }
        }
        if (add) {
            addRondomNum();
            endGame();
        }
    }

    //上移
    private void swipeUp(){
        boolean add=false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            add=true;
                            break;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(
                                    cardsMap[x][y].getNum());
                            add=true;

                        }
                        break;
                    }
                }
            }
        }
        if (add) {
            addRondomNum();
            endGame();
        }

    }

    }


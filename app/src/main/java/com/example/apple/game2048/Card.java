package com.example.apple.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by apple on 18/4/18.
 */

public class Card extends FrameLayout {
    private int num=0;
    private TextView label;    //呈现文字
;
    public Card(Context context) {
        super(context);
        label=new TextView(getContext());    //初始化
        label.setTextSize(32);   //设置文本大小
        label.setGravity(Gravity.CENTER);    //将文字放在中间

        //设置每个卡片的颜色
        label.setBackgroundColor(0x33ffffff);     //文字的颜色
        //该类用来初始化layout控件textView里的宽高属性，布局参数，-1，-1填充满整个父级容器
        LayoutParams lp=new LayoutParams(-1,-1);
        //设置间隔
        lp.setMargins(10, 10, 0, 0);
        addView(label,lp);    //添加进来
        setNum(0);
    }

    public void setNum(int num) {
        this.num = num;
        if (num <= 0){
            label.setText("");
        }else{
            label.setText(num+"");    //ID
        }
    }

    public int getNum() {
        return num;
    }

    //重写equals方法，判断卡片绑定的数字是否相等
    public boolean equals(Card o){
        return getNum() ==o.getNum();
    }
}

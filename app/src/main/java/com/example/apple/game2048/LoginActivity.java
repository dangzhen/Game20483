package com.example.apple.game2048;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText etAccount,etPassword;
    private Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.btn_login);
        btn2 = (Button) findViewById(R.id.btn_register);

        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_pwd);

        btn1.setOnClickListener(new View.OnClickListener() {     //设置按钮的监听器
            @Override
            //登录按钮监听
            public void onClick(View view) {
                //保存数据
                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                // 使用getString方法获得value，注意第2个参数是value的默认值
                String number = sharedPreferences.getString("number", "0");
                int a, b;
                a = Integer.parseInt(number);
                String account;
                String password;
                for (b = 0; b < a; b++) {
                    account = "account" + b;
                    password = "password" + b;
                    //账号
                    String jname = sharedPreferences.getString(account, "");
                    //密码
                    String jpwd = sharedPreferences.getString(password, "");
                    //判断账号或者密码是否一致
                    if (jname.equals(etAccount.getText().toString()) || jpwd.equals(etPassword.getText().toString())) {
                        //如果账号密码一致
                        if (jname.equals(etAccount.getText().toString()) && jpwd.equals(etPassword.getText().toString())) {
                            finish();
                            //跳转到主界面
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;
                            //只有账号正确
                        } else if (jname.equals(etAccount.getText().toString())) {
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                //用户个数为0
                if (b == a) {
                    Toast.makeText(LoginActivity.this, "用户未注册", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //注册按钮的监听
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account;
                String password;
                //数据保存
                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                //使用getString方法获得value，注意第2个参数是value的默认值
                String number = sharedPreferences.getString("number", "0");
                int a, b;

                    a = Integer.parseInt(number);
                for (b = 0; b < a; b++) {
                    account = "account" + b;
                    password = "password" + b;
                    String jname = sharedPreferences.getString(account, "");
                    if (jname.equals(etAccount.getText().toString())) {
                        break;
                    }
                }
                if (b == a) {
                    //如果账号密码为空
                    if (etAccount.getText().toString().equals("") && etPassword.getText().toString().equals("")) {
                        Toast.makeText(LoginActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        account = "account" + b;
                        password = "password" + b;
                        a++;
                        //实例化SharedPreferences.Editor对象
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //用putString的方法保存数据
                        editor.putString(account, etAccount.getText().toString());
                        editor.putString(password, etPassword.getText().toString());
                        editor.putString("number",number+1);

                        //提交当前数据
                        editor.commit();
                        //提示注册成功
                        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,"该账户已存在",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}



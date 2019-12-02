package com.ycm.myapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;
import com.ycm.myapp.R;
import com.ycm.myapp.bean.LoginResponse2;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterActivity extends AppCompatActivity{
    @BindView(R.id.et_username)EditText et_username;
    @BindView(R.id.et_email)EditText et_email;
    @BindView(R.id.et_pwd1)EditText et_pwd1;
    @BindView(R.id.et_pwd2)EditText et_pwd2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.iv_back})
    void back(){
        finish();
    }
    @OnClick(R.id.bt_register)
    void register(){
        String uname = et_username.getText().toString();
        if(TextUtils.isEmpty(uname)){
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return;
        }

        String email = et_email.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"请输入邮箱",Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd1 = et_pwd1.getText().toString();
        if(TextUtils.isEmpty(pwd1)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd2 = et_pwd2.getText().toString();
        if(TextUtils.isEmpty(pwd2)){
            Toast.makeText(this,"请输入确认密码",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!pwd1.equals(pwd2)){
            Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpUtils
                .post()
                .url("http://10.10.16.23:8088/MobileShop/member")
                .addParams("uname", uname)
                .addParams("email", email)
                .addParams("password",pwd1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(RegisterActivity.this,"注册失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        return;
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Gson gson = new Gson();
                        LoginResponse2 loginResponse2 = gson.fromJson(response, LoginResponse2.class);
                        if(loginResponse2!=null&&loginResponse2.getStatus()==0){

                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this,"注册失败："+loginResponse2.getMsg(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}

package com.czw.project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.czw.project.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText edit_mobilePhone;
    private EditText edit_password;
    private EditText edit_name;
    private BmobUser user, tempUser;

    private Button btn_login, btn_sign;
    private Button btn_time;

    private ProgressBar mProgressBar,timeProgress;

    private String userName, userPassword, userMobilePhone;

    BmobQuery<BmobUser> query = new BmobQuery<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        initView();
        setTextChangeListener();
    }

    private void initView() {
        edit_mobilePhone = (EditText) findViewById(R.id.phoneNum);
        edit_password = (EditText) findViewById(R.id.password);
        edit_name = (EditText) findViewById(R.id.user_name);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_sign = (Button) findViewById(R.id.btn_sign);
        btn_time = (Button) findViewById(R.id.btn_time);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        timeProgress = (ProgressBar) findViewById(R.id.time_progress);
        btn_login.setOnClickListener(this);
        btn_sign.setOnClickListener(this);
    }

    /*用于检测输入框的变化*/
    public void setTextChangeListener() {
        edit_mobilePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_mobilePhone.getText().length() == 11) {
                    edit_password.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (btn_login.getText().toString().equals("下一步")) {
                    if (edit_mobilePhone.getText().length() == 11 && edit_password.getText().length() >= 6 && !edit_name.getText().toString().equals("")) {
                        btn_login.setEnabled(true);
                    } else {
                        btn_login.setEnabled(false);
                    }
                }
            }
        });
        edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_password.getText().length() >= 6) {
                    btn_login.setEnabled(true);
                } else {
                    btn_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (btn_login.getText().toString().equals("下一步")) {
                    if (edit_mobilePhone.getText().length() == 11 && edit_password.getText().length() >= 6 && !edit_name.getText().toString().equals("")) {
                        btn_login.setEnabled(true);
                    } else {
                        btn_login.setEnabled(false);
                    }
                }
            }
        });
        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edit_mobilePhone.getText().length() == 11 && edit_password.getText().length() >= 6 && !edit_name.getText().toString().equals("")) {
                    btn_login.setEnabled(true);
                } else {
                    btn_login.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign:
                edit_password.setVisibility(View.VISIBLE);
                edit_name.setVisibility(View.VISIBLE);
                btn_login.setText("下一步");
                btn_login.setEnabled(false);
                btn_sign.setVisibility(View.GONE);
                break;
            case R.id.btn_login:
                if (btn_login.getText().toString().equals("下一步")) {
                    query.addWhereEqualTo("mobilePhoneNumber", edit_mobilePhone.getText().toString());
                    query.findObjects(this, new FindListener<BmobUser>() {
                        @Override
                        public void onSuccess(List<BmobUser> object) {
                            if (object.size() == 0) {
                                userMobilePhone = edit_mobilePhone.getText().toString();
                                userPassword = edit_password.getText().toString();
                                userName = edit_name.getText().toString();
                                edit_name.setVisibility(View.GONE);
                                edit_password.setText(null);
                                edit_password.setHint("短信验证码");
                                btn_login.setText("完成注册");
                                btn_time.setVisibility(View.VISIBLE);
                                timeProgress.setVisibility(View.VISIBLE);
                                sendSmsCode();
                            } else {
                                toast("该手机号已经注册，请直接登陆");
                            }
                        }
                        @Override
                        public void onError(int code, String msg) {
                            toast("检查手机网络");
                        }
                    });
                } else if (btn_login.getText().toString().equals("登陆")) {
                    userLogin();
                } else if (btn_login.getText().toString().equals("完成注册")) {
                    BmobSMS.verifySmsCode(this, userMobilePhone, edit_password.getText().toString(), new VerifySMSCodeListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                userSign();
                            }else {
                                edit_password.setText("");
                                toast("验证码错误");
                            }
                        }
                    });
                }
                break;
            case R.id.btn_time:
                if (btn_time.getText().toString().equals("重新发送")) {
                    sendSmsCode();
                }
                break;
        }
    }

    /*发送短信验证码*/
    public void sendSmsCode() {
        BmobSMS.requestSMSCode(getApplicationContext(), edit_mobilePhone.getText().toString(), "模板名称", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {//验证码发送成功
                    new Timer().schedule(timerTask, 1000, 1000);
                }
            }
        });
    }

    /*用户注册*/
    public void userSign() {
        mProgressBar.setVisibility(View.VISIBLE);
        BmobUser bu = new BmobUser();
        bu.setUsername(userName);
        bu.setPassword(userPassword);
        bu.setMobilePhoneNumber(userMobilePhone);
        //注意：不能用save方法进行注册
        bu.signUp(getApplicationContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                toast("注册成功:");
                finish();
            }
            @Override
            public void onFailure(int code, String msg) {
                toast("注册失败:" + msg);
            }
        });
        mProgressBar.setVisibility(View.GONE);
    }

    /*用户登录*/
    public void userLogin() {
        mProgressBar.setVisibility(View.VISIBLE);
        query.addWhereEqualTo("mobilePhoneNumber", edit_mobilePhone.getText().toString());
        query.findObjects(this, new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> object) {
                if (object.size() == 0) {
                    toast("该手机号尚未注册优农家");
                } else {
                    tempUser = object.get(0);
                    user = new BmobUser();
                    user.setUsername(tempUser.getUsername());
                    user.setPassword(edit_password.getText().toString());
                    user.login(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            toast("登陆成功");
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            toast("密码错误");
                        }
                    });
                }
            }

            @Override
            public void onError(int code, String msg) {
                toast("检查手机网络");
            }
        });
        mProgressBar.setVisibility(View.GONE);
    }

    /*短信验证数字倒计时*/
    final TimerTask timerTask = new TimerTask() {
        int i = 60;

        @Override
        public void run() {
            i--;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btn_time.setText(i + "");
                    if (i == 0) {
                        btn_time.setText("重新发送");
                        timeProgress.setVisibility(View.GONE);
                        timerTask.cancel();
                    }
                }
            });
        }
    };

    /*信息提示*/
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

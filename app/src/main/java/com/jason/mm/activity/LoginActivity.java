package com.jason.mm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jason.mm.R;


/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 *
 * @author Reginer on 2016/7/25 12:56.
 *         Description:
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText mEtAccount;
    private EditText mEtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void register(final String account, final String pwd) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    EMClient.getInstance().createAccount(account, pwd);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            int errorCode = e.getErrorCode();
                            if (errorCode == EMError.NETWORK_ERROR) {
                                Toast.makeText(LoginActivity.this, "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                                Toast.makeText(LoginActivity.this, "用户已存在！", Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
                                Toast.makeText(LoginActivity.this, "注册失败，无权限！", Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                                Toast.makeText(LoginActivity.this, "用户名不合法", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        }).start();
    }

    private void initView() {
        mEtAccount = (EditText) findViewById(R.id.et_account);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);

        mEtAccount.setText("Reginer");
        mEtPwd.setText("123456");
        Button mRegister = (Button) findViewById(R.id.btn_register);
        Button mLogin = (Button) findViewById(R.id.btn_login);

        mRegister.setOnClickListener(this);
        mLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                submit();
                register(mEtAccount.getText().toString().trim(), mEtPwd.getText().toString().trim());
                break;
            case R.id.btn_login:
                submit();
                login(mEtAccount.getText().toString().trim(), mEtPwd.getText().toString().trim());
                break;
        }
    }

    private void login(String account, String pwd) {
        EMClient.getInstance().login(account, pwd, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginActivity.this, "登录聊天服务器成功！", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(LoginActivity.this,MainActivity.class));

                finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginActivity.this, "登录聊天服务器失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void submit() {
        // validate
        String account = mEtAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "account不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = mEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "pwd不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
    }
}

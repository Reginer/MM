package com.jason.mm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.jason.mm.R;

public class ChatActivity extends EaseBaseActivity implements View.OnClickListener {
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;
    private Button mBtnVideo;
    private RelativeLayout mContainer;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_chat);
        initView();
        activityInstance = this;
        //user or group id
        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        chatFragment = new EaseChatFragment();
        //set arguments
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // enter to chat activity when click notification bar, here make sure only one chat activiy
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

    private void initView() {
        mBtnVideo = (Button) findViewById(R.id.btn_video);
        mContainer = (RelativeLayout) findViewById(R.id.container);

        mBtnVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_video:
//                startActivity(new Intent(ChatActivity.this, VideoCallActivity.class).putExtra("username", toChatUsername)
//                        .putExtra("isComingCall", false));
                break;
        }
    }
}

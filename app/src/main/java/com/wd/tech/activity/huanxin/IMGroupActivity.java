package com.wd.tech.activity.huanxin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.wd.tech.R;

public class IMGroupActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
           EaseChatFragment chatFragment = new EaseChatFragment();
           chatFragment.setArguments(getIntent().getExtras());
           getSupportFragmentManager().beginTransaction().add(R.id.hx_ok,chatFragment).commit();

    }


}

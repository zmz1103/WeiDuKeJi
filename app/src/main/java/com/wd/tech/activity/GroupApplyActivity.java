package com.wd.tech.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/2/27 13:34
 * author:赵明珠(啊哈)
 * function:申请加群
 */
public class GroupApplyActivity extends WDActivity {

    @BindView(R.id.group_background)
    SimpleDraweeView mBackground;

    @BindView(R.id.group_head)
    SimpleDraweeView mGroupHeadPic;

    @BindView(R.id.group_apply_name)
    TextView mGroupNames;
    @BindView(R.id.group_number)
    TextView mGroupNumber;

    @BindView(R.id.group_intro)
    TextView mGroupIntro;

    @BindView(R.id.group_members)
    RelativeLayout mGroupMembers;
    private String mGroupId;
    private String mGroupImage;
    private String mGroupName;
    private String mCurrentCount;
    private String mDescription;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_apply_item;
    }

    @Override
    protected void initView() {
        Uri parse = Uri.parse("res://" + getPackageName() + "/" + R.mipmap.group_apply);
        mBackground.setImageURI(parse);

        Intent intent = getIntent();

        mGroupId = intent.getStringExtra("mGroupId");
        mGroupImage = intent.getStringExtra("mGroupImage");
        mGroupName = intent.getStringExtra("mGroupName");
        mCurrentCount = intent.getStringExtra("mCurrentCount");
        mDescription = intent.getStringExtra("mDescription");


        mGroupHeadPic.setImageURI(Uri.parse(mGroupImage));
        mGroupNames.setText(mGroupName);
        mGroupIntro.setText(mDescription);
        mGroupNumber.setText("共"+mCurrentCount+"人");


        mGroupMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    @OnClick({R.id.apply_group,R.id.group_apply_back})
    public void onClick(View view){
        if (view.getId() == R.id.group_apply_back){
            finish();
        }
        if (view.getId() == R.id.apply_group){
            Intent intent = new Intent(GroupApplyActivity.this,SendApplyActivity.class);

            intent.putExtra("mApplyGroupId",mGroupId);

            startActivity(intent);
        }
    }
    @Override
    protected void destoryData() {
    }
}

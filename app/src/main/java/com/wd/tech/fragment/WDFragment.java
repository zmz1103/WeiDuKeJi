package com.wd.tech.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * date:2019/2/19 10:31
 * author:赵明珠(啊哈)
 * function:
 */
public abstract class WDFragment extends Fragment {
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContent(), container, false);

        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }
    public abstract int getContent();
    public abstract void initView(View view);
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

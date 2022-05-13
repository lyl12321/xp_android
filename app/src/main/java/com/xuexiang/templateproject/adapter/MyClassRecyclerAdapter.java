package com.xuexiang.templateproject.adapter;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.Collection;

public class MyClassRecyclerAdapter extends BaseRecyclerAdapter<UserDTORes> {

    public MyClassRecyclerAdapter() {
    }

    public MyClassRecyclerAdapter(Collection<UserDTORes> list) {
        super(list);
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, UserDTORes item) {
        ((SuperTextView) holder.findViewById(R.id.st_username)).setLeftString(item.getUsername());
        SuperTextView v = holder.findViewById(R.id.st_username);
        v.setRightString(item.getUserStatus());
        if ("已登陆".equals(item.getUserStatus())) {
            v.setRightTextColor(Color.parseColor("#9FD661"));
        } else {
            v.setRightTextColor(Color.parseColor("#FE6D4B"));
        }
        v.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @SingleClick
            @Override
            public void onClick(SuperTextView superTextView) {
                //点击的时候问一下是否要发起聊天

            }
        });
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_my_class;
    }

}

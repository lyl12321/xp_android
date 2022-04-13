package com.xuexiang.templateproject.adapter;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;
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
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_my_class;
    }

}

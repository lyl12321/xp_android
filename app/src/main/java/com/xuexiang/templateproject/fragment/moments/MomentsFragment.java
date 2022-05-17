package com.xuexiang.templateproject.fragment.moments;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentMomentsBinding;
import com.xuexiang.templateproject.http.moments.api.MomentsApi;
import com.xuexiang.templateproject.http.moments.entity.MomentsListDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.templateproject.utils.MMKVUtils;
import com.xuexiang.templateproject.utils.PageUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

@Page(anim = CoreAnim.none)
public class MomentsFragment extends BaseFragment<FragmentMomentsBinding> {


    private SimpleDelegateAdapter<MomentsListDTO.MomentsItem> mAdapter;

    private int listCurrentFrom = 1;  //当前页数

    private boolean isFirst = true;

    @NonNull
    @Override
    protected FragmentMomentsBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentMomentsBinding.inflate(inflater,container,false);
    }

    /**
     * @return 返回为 null意为不需要导航栏
     */
    @Override
    protected TitleBar initTitle() {
        return null;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

        WidgetUtils.initRecyclerView(binding.recyclerView);

        mAdapter = new SimpleDelegateAdapter<MomentsListDTO.MomentsItem>(R.layout.item_moments,new LinearLayoutHelper()) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, MomentsListDTO.MomentsItem item) {
                SuperTextView user = holder.findViewById(R.id.st_username);
                if (item.getUserInfo() == null) {
                    user.setLeftString(item.getId());
                } else {
                    user.setLeftString(item.getUserInfo().getUsername());
                }



//                user.setLeftBottomString(item.getContent());
//
//                SuperTextView content = holder.findViewById(R.id.st_content);
//                content.setCenterString(item.getContent());

                holder.text(R.id.st_content,item.getContent());


                Glide.with(binding.getRoot())
                        .load(item.getUserInfo().getAvatarUrl())
                        .into(user.getLeftIconIV());

                SuperTextView createTime = holder.findViewById(R.id.st_create_time);
                createTime.setRightBottomString(item.getCreateTime());
            }
        };

        binding.recyclerView.setAdapter(mAdapter);

        // 开启自动加载功能（非必须）
        binding.refreshLayout.setEnableAutoLoadMore(true);
        // 下拉刷新
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            listCurrentFrom = 1;  //刷新只加载第一页
            MomentsApi.getMomentsList(listCurrentFrom, new TipCallBack<MomentsListDTO>() {
                @Override
                public void onSuccess(MomentsListDTO response) throws Throwable {
                    PageUtils.finishRefreshData(refreshLayout,binding.loading,response.getPages(),listCurrentFrom,response.getTotal() > 0);
                    if (response.getList() != null && response.getList().size() > 0) {
                        mAdapter.refresh(response.getList());
                    }
                }
            });
        });
        // 上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            listCurrentFrom++;

            MomentsApi.getMomentsList(listCurrentFrom, new TipCallBack<MomentsListDTO>() {
                @Override
                public void onSuccess(MomentsListDTO response) throws Throwable {
                    PageUtils.finishRefreshData(refreshLayout,binding.loading,response.getPages(),listCurrentFrom,response.getTotal() > 0);

                    if (response.getList() != null && response.getList().size() > 0) {
                        mAdapter.loadMore(response.getList());
                    }
                }
            });
        });

        // 触发自动刷新
//        binding.refreshLayout.autoRefresh();
    }


    @Override
    protected void initListeners() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View view) {
                //打开发朋友圈的页面
                openNewPage(PublishMomentsFragment.class);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //重新显示的时候判断一下是否需要刷新
        if (MMKVUtils.getBoolean(Constant.momentsListIsRefresh, false) || isFirst) {
            binding.refreshLayout.autoRefresh();
            isFirst = false;  //走过一次onResume就不是第一次进入了
            //刷新完就不用重复刷新了
            MMKVUtils.put(Constant.momentsListIsRefresh, false);
        }
    }
}

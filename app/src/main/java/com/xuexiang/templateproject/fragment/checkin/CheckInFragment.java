package com.xuexiang.templateproject.fragment.checkin;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.base.broccoli.BroccoliSimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentRefreshBasicBinding;
import com.xuexiang.templateproject.http.check.api.CheckApi;
import com.xuexiang.templateproject.http.check.entity.CheckListDTO;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.ArrayList;

import me.samlss.broccoli.Broccoli;


@Page(anim = CoreAnim.none)
public class CheckInFragment extends BaseFragment<FragmentRefreshBasicBinding> {

    private SimpleDelegateAdapter<CheckListDTO.CheckListItem> mAdapter;

    private int listCurrentFrom = 1;  //当前页数

    @NonNull
    @Override
    protected FragmentRefreshBasicBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentRefreshBasicBinding.inflate(inflater, container, false);
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

        mAdapter = new BroccoliSimpleDelegateAdapter<CheckListDTO.CheckListItem>(R.layout.item_check_in_list, new LinearLayoutHelper(), new ArrayList<>()) {
            @Override
            protected void onBindData(RecyclerViewHolder holder, CheckListDTO.CheckListItem model, int position) {
                if (model != null) {

                    SuperTextView view = holder.findViewById(R.id.st_item);
                    view.setLeftString(model.getCheckTitle());
                    view.setRightString(model.getUserCheckStatus());
                    if (model.getUserCheckStatus() != null) {
                        if (model.getUserCheckStatus().equals("已过期") || model.getUserCheckStatus().equals("未签到")) {
                            view.setRightTextColor(Color.parseColor("#FFD4172A"));
                        } else if (model.getUserCheckStatus().equals("已签到")) {
                            view.setRightTextColor(Color.parseColor("#FF7DEA51"));
                        }
                    }

                    holder.click(R.id.st_item, new View.OnClickListener() {
                        @SingleClick
                        @Override
                        public void onClick(View view) {
                            openNewPage(CheckInfoViewAndCommit.class,CheckInfoViewAndCommit.CHECK_ID,model.getId());
                        }
                    });

                }
            }

            @Override
            protected void onBindBroccoli(RecyclerViewHolder holder, Broccoli broccoli) {
                broccoli.addPlaceholders(
                        holder.findView(R.id.st_item)
                );
            }
        };

        binding.recyclerView.setAdapter(mAdapter);

        // 开启自动加载功能（非必须）
        binding.refreshLayout.setEnableAutoLoadMore(true);
        // 下拉刷新
        binding.refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            listCurrentFrom = 1;  //刷新只加载第一页
            CheckApi.getCheckList(listCurrentFrom, new TipCallBack<CheckListDTO>() {
                @Override
                public void onSuccess(CheckListDTO response) throws Throwable {
                    binding.refreshLayout.setEnableLoadMore(response.getPages() > listCurrentFrom);
                    if (response.getPages() < listCurrentFrom) {
                        refreshLayout12.resetNoMoreData();
                    }
                    if (response.getList() != null && response.getList().size() > 0) {
                        mAdapter.refresh(response.getList());
                    }
                    refreshLayout12.finishRefresh();
                }
            });
        });
        // 上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            listCurrentFrom++;

            CheckApi.getCheckList(listCurrentFrom, new TipCallBack<CheckListDTO>() {
                @Override
                public void onSuccess(CheckListDTO response) throws Throwable {
                    binding.refreshLayout.setEnableLoadMore(response.getPages() > listCurrentFrom);
                    if (response.getList() != null && response.getList().size() > 0) {
                        mAdapter.loadMore(response.getList());
                    }
                    if (response.getPages() < listCurrentFrom) {
                        refreshLayout1.finishLoadMore();
                    } else {
                        refreshLayout1.finishLoadMoreWithNoMoreData();
                    }
                }
            });
        });

        // 触发自动刷新
        binding.refreshLayout.autoRefresh();
    }


    @Override
    protected void initListeners() {

    }
}

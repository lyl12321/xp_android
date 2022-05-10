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

import java.util.ArrayList;

import me.samlss.broccoli.Broccoli;


@Page(anim = CoreAnim.none)
public class CheckInFragment extends BaseFragment<FragmentRefreshBasicBinding> {

    private SimpleDelegateAdapter<CheckListDTO.CheckListItem> mAdapter;

    private int listCurrentFrom = 1;  //当前页数

    private boolean isFirst = true;

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
                        } else if (model.getUserCheckStatus().equals("补签") || model.getUserCheckStatus().equals("代签") ){
                            view.setRightTextColor(Color.parseColor("#FFFFC107"));
                        } else {
                            view.setRightTextColor(Color.parseColor("#FF373737"));
                        }
                    }

                    holder.click(R.id.st_item, new View.OnClickListener() {
                        @SingleClick
                        @Override
                        public void onClick(View view) {
                            openNewPage(CheckInfoViewAndCommit.class,CheckInfoViewAndCommit.CHECK_OBJECT,model);
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
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            listCurrentFrom = 1;  //刷新只加载第一页
            CheckApi.getCheckList(listCurrentFrom, new TipCallBack<CheckListDTO>() {
                @Override
                public void onSuccess(CheckListDTO response) throws Throwable {
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

            CheckApi.getCheckList(listCurrentFrom, new TipCallBack<CheckListDTO>() {
                @Override
                public void onSuccess(CheckListDTO response) throws Throwable {
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

    }

    @Override
    public void onResume() {
        super.onResume();
        //重新显示的时候判断一下是否需要刷新
        if (MMKVUtils.getBoolean(Constant.checkListIsRefresh, false) || isFirst) {
            binding.refreshLayout.autoRefresh();
            isFirst = false;  //走过一次onResume就不是第一次进入了
            //刷新完就不用重复刷新了
            MMKVUtils.put(Constant.checkListIsRefresh, false);
        }
    }

    //    @Override
//    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
//        super.onFragmentResult(requestCode, resultCode, data);
//        if (data != null) {
//            Bundle extras = data.getExtras();
//            if (resultCode == 1000) {
//                if (extras.getBoolean("isNeedRefreshList",false)) {
//                    binding.refreshLayout.autoRefresh();
//                }
//            }
//        }
//    }
}

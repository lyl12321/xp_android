package com.xuexiang.templateproject.fragment.profile;


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
import com.xuexiang.templateproject.core.http.loader.MiniLoadingDialogLoader;
import com.xuexiang.templateproject.databinding.FragmentRefreshBasicBinding;
import com.xuexiang.templateproject.fragment.checkin.CheckInfoViewAndCommit;
import com.xuexiang.templateproject.http.check.api.CheckSubApi;
import com.xuexiang.templateproject.http.check.entity.CheckListDTO;
import com.xuexiang.templateproject.http.check.entity.CheckUserListDTO;
import com.xuexiang.templateproject.utils.PageUtils;
import com.xuexiang.templateproject.utils.UserUtils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.samlss.broccoli.Broccoli;

@Page(name = "签到人员列表")
public class CheckUserListFragment extends BaseFragment<FragmentRefreshBasicBinding> {

    private SimpleDelegateAdapter<CheckUserListDTO.CheckUserItem> mAdapter;

    public static final String CHECK_OBJECT = "check_object";

    private int listCurrentFrom = 1;  //当前页数

    @AutoWired(name = CHECK_OBJECT)
    CheckListDTO.CheckListItem mainCheckInfo;

    private MiniLoadingDialogLoader miniLoading;

    @Override
    protected void initArgs() {
        // 自动注入参数必须在initArgs里进行注入
        XRouter.getInstance().inject(this);
    }

    @NonNull
    @Override
    protected FragmentRefreshBasicBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentRefreshBasicBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initViews() {
        miniLoading = new MiniLoadingDialogLoader(getActivity());
        miniLoading.setCancelable(true);
        miniLoading.setOnProgressCancelListener(this::popToBack);
        WidgetUtils.initRecyclerView(binding.recyclerView);

        mAdapter = new BroccoliSimpleDelegateAdapter<CheckUserListDTO.CheckUserItem>(R.layout.item_check_user, new LinearLayoutHelper(), new ArrayList<>()) {
            @Override
            protected void onBindData(RecyclerViewHolder holder, CheckUserListDTO.CheckUserItem model, int position) {
                if (model != null) {

                    SuperTextView view = holder.findViewById(R.id.st_username);
                    view.setLeftString(model.getCheckUserInfo().getUsername());
                    String statusView = "";
                    if (model.getStatus() != null) {
                        if (model.getStatus().equals("0")) {
                            statusView = "未签到";
                            view.setRightTextColor(Color.parseColor("#FFD4172A"));
                        } else if (model.getStatus().equals("1")) {
                            statusView = "已签到";
                            view.setRightTextColor(Color.parseColor("#FF7DEA51"));
                        } else if (model.getStatus().equals("2")){
                            statusView = "代签";
                            view.setRightTextColor(Color.parseColor("#FFFFC107"));
                        } else if (model.getStatus().equals("3") ){
                            statusView = "补签";
                            view.setRightTextColor(Color.parseColor("#FFFFC107"));
                        } else {
                            statusView = "获取签到状态失败";
                            view.setRightTextColor(Color.parseColor("#FF373737"));
                        }
                        view.setRightString(statusView);
                    }
                    String finalStatusView = statusView;
                    holder.click(R.id.st_username, new View.OnClickListener() {
                        @SingleClick
                        @Override
                        public void onClick(View view) {
                            if (model.getStatus() != null) {
                                if (("1".equals(model.getStatus()) || "2".equals(model.getStatus())
                                        || "3".equals(model.getStatus())
                                )) {
                                    mainCheckInfo.setUserCheckStatus(finalStatusView);
                                    openPage(CheckInfoViewAndCommit.class,CheckInfoViewAndCommit.CHECK_OBJECT,mainCheckInfo);
                                } else if (model.getStatus().equals("0")) {
                                    new MaterialDialog.Builder(getActivity())
                                            .content("确定要帮"+model.getCheckUserInfo().getUsername()+"代签吗？")
                                            .positiveText(R.string.lab_yes)
                                            .negativeText(R.string.lab_no)
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                                    dialog.dismiss();
                                                    miniLoading.showLoading();
                                                    Map<String,Object> params = new HashMap<>();
                                                    params.put("rgReq1",0);
                                                    params.put("rgReq2",1);
                                                    params.put("rgReq3",2);

                                                    params.put("checkContent", JsonUtil.toJson(params));

                                                    params.put("status","2");


                                                    CheckSubApi.submitCheckInfo(mainCheckInfo.getId(), params, new TipCallBack<String>() {
                                                        @Override
                                                        public void onSuccess(String response) throws Throwable {
                                                            XToastUtils.success(response);

                                                            //放置一个永久的flag用来判断是否刷新
                                                            binding.refreshLayout.autoRefresh();
                                                            miniLoading.dismissLoading();
                                                        }

                                                        @Override
                                                        public void onError(ApiException e) {
                                                            super.onError(e);
                                                            miniLoading.dismissLoading();
                                                        }
                                                    });

                                                }
                                            })
                                            .show();
                                }
                            } else {
                                XToastUtils.warning("获取签到状态失败，无法操作");
                            }

                        }
                    });

                }
            }

            @Override
            protected void onBindBroccoli(RecyclerViewHolder holder, Broccoli broccoli) {
                broccoli.addPlaceholders(
                        holder.findView(R.id.st_username)
                );
            }
        };

        binding.recyclerView.setAdapter(mAdapter);

        // 开启自动加载功能（非必须）
        binding.refreshLayout.setEnableAutoLoadMore(true);
        // 下拉刷新
        binding.refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            listCurrentFrom = 1;  //刷新只加载第一页
            CheckSubApi.queryCheckUserList(listCurrentFrom,mainCheckInfo.getId(), UserUtils.getCurrentUser().getClassCode(), new TipCallBack<CheckUserListDTO>() {
                @Override
                public void onSuccess(CheckUserListDTO response) throws Throwable {
                    PageUtils.finishRefreshData(refreshLayout12,response.getPages(),listCurrentFrom,response.getTotal() > 0);

                    if (response.getList() != null && response.getList().size() > 0) {
                        mAdapter.refresh(response.getList());
                    }
                }
            });
        });
        // 上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            listCurrentFrom++;

            CheckSubApi.queryCheckUserList(listCurrentFrom,mainCheckInfo.getId(), UserUtils.getCurrentUser().getClassCode(), new TipCallBack<CheckUserListDTO>() {
                @Override
                public void onSuccess(CheckUserListDTO response) throws Throwable {
                    PageUtils.finishRefreshData(refreshLayout1,response.getPages(),listCurrentFrom,response.getTotal() > 0);

                    if (response.getList() != null && response.getList().size() > 0) {
                        mAdapter.loadMore(response.getList());
                    }
                }
            });
        });

        // 触发自动刷新
        binding.refreshLayout.autoRefresh();

    }
}

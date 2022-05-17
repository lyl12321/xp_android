/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.templateproject.fragment.profile;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.core.http.loader.MiniLoadingDialogLoader;
import com.xuexiang.templateproject.databinding.FragmentRefreshBasicBinding;
import com.xuexiang.templateproject.fragment.chat.ChatContentFragment;
import com.xuexiang.templateproject.http.chat.api.ChatApi;
import com.xuexiang.templateproject.http.chat.entity.ChatRoomListDTO;
import com.xuexiang.templateproject.http.grade.api.GradeApi;
import com.xuexiang.templateproject.http.grade.entity.UserListDTO;
import com.xuexiang.templateproject.http.login.api.LoginApi;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;
import com.xuexiang.templateproject.utils.PageUtils;
import com.xuexiang.templateproject.utils.UserUtils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/**
 * @author xuexiang
 * @since 2018/12/6 下午5:57
 */
@Page(name = "我的班级")
public class MyClassFragment extends BaseFragment<FragmentRefreshBasicBinding> {

    private SimpleDelegateAdapter<UserDTORes> mAdapter;

    private int listCurrentFrom = 1;  //当前页数

    private MiniLoadingDialogLoader miniLoading;

    @NonNull
    @Override
    protected FragmentRefreshBasicBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentRefreshBasicBinding.inflate(inflater, container, false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

        miniLoading = new MiniLoadingDialogLoader(getActivity());
        miniLoading.setCancelable(true);
        miniLoading.setOnProgressCancelListener(this::popToBack);


        WidgetUtils.initRecyclerView(binding.recyclerView);

        //必须在setAdapter之前调用
        UserDTORes userDTO = UserUtils.getCurrentUser();
        if (userDTO.getUserType() != null && userDTO.getUserType().equals("2")) {

        } else {
            binding.recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        }
        //必须在setAdapter之前调用
        binding.recyclerView.setOnItemMenuClickListener(mMenuItemClickListener);
        mAdapter = new SimpleDelegateAdapter<UserDTORes>(R.layout.item_my_class,new LinearLayoutHelper()) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, UserDTORes item) {
                ((SuperTextView) holder.findViewById(R.id.st_username)).setLeftString(item.getUsername());
                SuperTextView v = holder.findViewById(R.id.st_username);
                v.setRightString(item.getUserStatus());
                if ("已登陆".equals(item.getUserStatus())) {
                    v.setRightTextColor(Color.parseColor("#9FD661"));
                } else if ("在线".equals(item.getUserStatus())){
                    v.setRightTextColor(Color.parseColor("#2BBDF3"));
                } else {
                    v.setRightTextColor(Color.parseColor("#FE6D4B"));
                }
                v.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
                    @SingleClick
                    @Override
                    public void onClick(SuperTextView superTextView) {
                        if (UserUtils.getCurrentUser().getId().equals(item.getId())) {
                            //不能和自己聊天
                            XToastUtils.warning("不能和自己聊天");
                            return;
                        }
                        //点击的时候问一下是否要发起聊天

                        //不问了...
                        miniLoading.showLoading();


                        ChatApi.createChatRoom(item.getId(), new TipCallBack<ChatRoomListDTO.ChatRoomItem>() {
                            @Override
                            public void onSuccess(ChatRoomListDTO.ChatRoomItem response) throws Throwable {
                                miniLoading.dismissLoading();
                                openPage(ChatContentFragment.class,ChatContentFragment.KEY_CHAT_INFO, response);
                            }

                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                miniLoading.dismissLoading();
                            }
                        });

//                        new MaterialDialog.Builder(getContext())
//                                .content("确定要和" + userDTO.getUsername()+"聊天吗？")
//                                .positiveText("是")
//                                .negativeText("否")
//                                .onPositive((dialog, which) -> {
//                                    ChatApi.createChatRoom(item.getId(), new TipCallBack<ChatRoomListDTO.ChatRoomItem>() {
//                                        @Override
//                                        public void onSuccess(ChatRoomListDTO.ChatRoomItem response) throws Throwable {
//                                            openPage(ChatContentFragment.class,ChatContentFragment.KEY_CHAT_INFO, response);
//                                        }
//                                    });
//                                })
//                                .show();
                    }
                });
            }
        };
        binding.recyclerView.setAdapter(mAdapter);

        // 开启自动加载功能（非必须）
        binding.refreshLayout.setEnableAutoLoadMore(true);
        // 下拉刷新
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            listCurrentFrom = 1;  //刷新只加载第一页
            GradeApi.getClassMemberByUser(listCurrentFrom, new TipCallBack<UserListDTO>() {
                @Override
                public void onSuccess(UserListDTO response) throws Throwable {
                    PageUtils.finishRefreshData(refreshLayout, binding.loading, response.getPages(), listCurrentFrom, response.getTotal() > 0);

                    if (response.getList() != null && response.getList().size() > 0) {
                        mAdapter.refresh(response.getList());
                    }
                }
            });
        });
        // 上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            listCurrentFrom++;

            GradeApi.getClassMemberByUser(listCurrentFrom, new TipCallBack<UserListDTO>() {
                @Override
                public void onSuccess(UserListDTO response) throws Throwable {
                    PageUtils.finishRefreshData(refreshLayout, binding.loading, response.getPages(), listCurrentFrom, response.getTotal() > 0);

                    if (response.getList() != null && response.getList().size() > 0) {
                        mAdapter.loadMore(response.getList());
                    }
                }
            });
        });

        // 触发自动刷新
        binding.refreshLayout.autoRefresh();

    }


    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = (swipeLeftMenu, swipeRightMenu, position) -> {
        int width = getResources().getDimensionPixelSize(R.dimen.dp_70);

        // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
        // 2. 指定具体的高，比如80;
        // 3. WRAP_CONTENT，自身高度，不推荐;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        {
            SwipeMenuItem addItem = new SwipeMenuItem(getContext()).setBackground(R.drawable.menu_selector_red)
                    .setImage(R.drawable.ic_baseline_arrow_circle_down_24)
                    .setTextColor(Color.WHITE)
                    .setText("下线")
                    .setWidth(width)
                    .setHeight(height);
            swipeLeftMenu.addMenuItem(addItem); // 添加菜单到左侧。
        }


        // 添加右侧的，如果不添加，则右侧不会出现菜单。
        {
            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext()).setBackground(R.drawable.menu_selector_red)
                    .setImage(R.drawable.ic_baseline_delete_24)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private OnItemMenuClickListener mMenuItemClickListener = (menuBridge, position) -> {
        menuBridge.closeMenu();

        int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
        int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

        if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
//            Map<String,Object> params = new HashMap<>();
//            params.put("id",);
            GradeApi.deleteClassMemberFun(mAdapter.getData().get(position).getId(), new TipCallBack<String>() {
                @Override
                public void onSuccess(String response) throws Throwable {
                    XToastUtils.success(response);
                    // 触发自动刷新
                    binding.refreshLayout.autoRefresh();
                }
            });
        } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {

            LoginApi.forceLogout(mAdapter.getData().get(position).getId(), new TipCallBack<String>() {
                @Override
                public void onSuccess(String response) throws Throwable {
                    XToastUtils.success(response);
                    // 触发自动刷新
                    binding.refreshLayout.autoRefresh();
                }
            });

//            XToastUtils.toast("list第" + position + "; 左侧菜单第" + menuPosition);
        }
    };

}

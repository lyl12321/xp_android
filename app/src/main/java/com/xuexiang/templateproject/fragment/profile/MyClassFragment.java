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
import com.bumptech.glide.Glide;
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
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.templateproject.utils.MMKVUtils;
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
 * @since 2018/12/6 ??????5:57
 */
@Page(name = "????????????")
public class MyClassFragment extends BaseFragment<FragmentRefreshBasicBinding> {

    private SimpleDelegateAdapter<UserDTORes> mAdapter;

    private int listCurrentFrom = 1;  //????????????

    private MiniLoadingDialogLoader miniLoading;

    @NonNull
    @Override
    protected FragmentRefreshBasicBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentRefreshBasicBinding.inflate(inflater, container, false);
    }

    /**
     * ???????????????
     */
    @Override
    protected void initViews() {

        miniLoading = new MiniLoadingDialogLoader(getActivity());
        miniLoading.setCancelable(true);
        miniLoading.setOnProgressCancelListener(this::popToBack);


        WidgetUtils.initRecyclerView(binding.recyclerView);

        //?????????setAdapter????????????
        UserDTORes userDTO = UserUtils.getCurrentUser();
        if (userDTO.getUserType() != null && userDTO.getUserType().equals("2")) {

        } else {
            binding.recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        }
        //?????????setAdapter????????????
        binding.recyclerView.setOnItemMenuClickListener(mMenuItemClickListener);
        mAdapter = new SimpleDelegateAdapter<UserDTORes>(R.layout.item_my_class,new LinearLayoutHelper()) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, UserDTORes item) {
                ((SuperTextView) holder.findViewById(R.id.st_username)).setLeftString(item.getUsername()
                        + " (" + (item.getUserType().equals("2") ? "??????": "??????") + ")");
                SuperTextView v = holder.findViewById(R.id.st_username);
                v.setRightString(item.getUserStatus());
                if ("?????????".equals(item.getUserStatus())) {
                    v.setRightTextColor(Color.parseColor("#9FD661"));
                } else if ("??????".equals(item.getUserStatus())){
                    v.setRightTextColor(Color.parseColor("#2BBDF3"));
                } else {
                    v.setRightTextColor(Color.parseColor("#FE6D4B"));
                }

                Glide.with(binding.getRoot())
                        .load(item.getAvatarUrl())
                        .into(v.getLeftIconIV());
                v.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
                    @SingleClick
                    @Override
                    public void onClick(SuperTextView superTextView) {
                        if (UserUtils.getCurrentUser().getId().equals(item.getId())) {
                            //?????????????????????
                            XToastUtils.warning("?????????????????????");
                            return;
                        }
                        //?????????????????????????????????????????????

                        //?????????...
                        miniLoading.showLoading();


                        ChatApi.createChatRoom(item.getId(), new TipCallBack<ChatRoomListDTO.ChatRoomItem>() {
                            @Override
                            public void onSuccess(ChatRoomListDTO.ChatRoomItem response) throws Throwable {
                                miniLoading.dismissLoading();
                                //?????????????????? ???????????????????????????
                                MMKVUtils.put(Constant.chatRoomListIsRefreshByRemote, true);
                                openPage(ChatContentFragment.class,ChatContentFragment.KEY_CHAT_INFO, response);
                            }

                            @Override
                            public void onError(ApiException e) {
                                super.onError(e);
                                miniLoading.dismissLoading();
                            }
                        });

//                        new MaterialDialog.Builder(getContext())
//                                .content("????????????" + userDTO.getUsername()+"????????????")
//                                .positiveText("???")
//                                .negativeText("???")
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

        // ???????????????????????????????????????
        binding.refreshLayout.setEnableAutoLoadMore(true);
        // ????????????
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            listCurrentFrom = 1;  //????????????????????????
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
        // ????????????
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

        // ??????????????????
        binding.refreshLayout.autoRefresh();

    }


    /**
     * ?????????????????????Item?????????????????????????????????
     */
    private SwipeMenuCreator swipeMenuCreator = (swipeLeftMenu, swipeRightMenu, position) -> {
        int width = getResources().getDimensionPixelSize(R.dimen.dp_70);

        // 1. MATCH_PARENT ???????????????????????????Item?????????;
        // 2. ???????????????????????????80;
        // 3. WRAP_CONTENT???????????????????????????;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        {
            SwipeMenuItem addItem = new SwipeMenuItem(getContext()).setBackground(R.drawable.menu_selector_red)
                    .setImage(R.drawable.ic_baseline_arrow_circle_down_24)
                    .setTextColor(Color.WHITE)
                    .setText("??????")
                    .setWidth(width)
                    .setHeight(height);
            swipeLeftMenu.addMenuItem(addItem); // ????????????????????????
        }


        // ??????????????????????????????????????????????????????????????????
        {
            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext()).setBackground(R.drawable.menu_selector_red)
                    .setImage(R.drawable.ic_baseline_delete_24)
                    .setText("??????")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };

    /**
     * RecyclerView???Item???Menu???????????????
     */
    private OnItemMenuClickListener mMenuItemClickListener = (menuBridge, position) -> {
        menuBridge.closeMenu();

        int direction = menuBridge.getDirection(); // ???????????????????????????
        int menuPosition = menuBridge.getPosition(); // ?????????RecyclerView???Item??????Position???

        if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
//            Map<String,Object> params = new HashMap<>();
//            params.put("id",);
            GradeApi.deleteClassMemberFun(mAdapter.getData().get(position).getId(), new TipCallBack<String>() {
                @Override
                public void onSuccess(String response) throws Throwable {
                    XToastUtils.success(response);
                    // ??????????????????
                    binding.refreshLayout.autoRefresh();
                }
            });
        } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {

            LoginApi.forceLogout(mAdapter.getData().get(position).getId(), new TipCallBack<String>() {
                @Override
                public void onSuccess(String response) throws Throwable {
                    XToastUtils.success(response);
                    // ??????????????????
                    binding.refreshLayout.autoRefresh();
                }
            });

//            XToastUtils.toast("list???" + position + "; ???????????????" + menuPosition);
        }
    };

}

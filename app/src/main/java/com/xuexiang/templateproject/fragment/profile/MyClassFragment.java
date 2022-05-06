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

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.MyClassRecyclerAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentRefreshBasicBinding;
import com.xuexiang.templateproject.http.grade.api.GradeApi;
import com.xuexiang.templateproject.http.grade.entity.UserListDTO;
import com.xuexiang.templateproject.utils.PageUtils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.WidgetUtils;
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

    private MyClassRecyclerAdapter mAdapter;

    private int listCurrentFrom = 1;  //当前页数

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

        WidgetUtils.initRecyclerView(binding.recyclerView);

        //必须在setAdapter之前调用
        binding.recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        //必须在setAdapter之前调用
        binding.recyclerView.setOnItemMenuClickListener(mMenuItemClickListener);
        binding.recyclerView.setAdapter(mAdapter = new MyClassRecyclerAdapter());

        // 开启自动加载功能（非必须）
        binding.refreshLayout.setEnableAutoLoadMore(true);
        // 下拉刷新
        binding.refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            listCurrentFrom = 1;  //刷新只加载第一页
            GradeApi.getClassMemberByUser(listCurrentFrom, new TipCallBack<UserListDTO>() {
                @Override
                public void onSuccess(UserListDTO response) throws Throwable {
                    PageUtils.finishRefreshData(refreshLayout12, response.getPages(), listCurrentFrom, response.getTotal() > 0);

                    if (response.getList() != null && response.getList().size() > 0) {
                        mAdapter.refresh(response.getList());
                    }
                }
            });
        });
        // 上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            listCurrentFrom++;

            GradeApi.getClassMemberByUser(listCurrentFrom, new TipCallBack<UserListDTO>() {
                @Override
                public void onSuccess(UserListDTO response) throws Throwable {
                    PageUtils.finishRefreshData(refreshLayout1, response.getPages(), listCurrentFrom, response.getTotal() > 0);

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
        }
    };

}

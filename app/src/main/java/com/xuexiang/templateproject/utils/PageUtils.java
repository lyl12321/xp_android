package com.xuexiang.templateproject.utils;


import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;

import ezy.ui.layout.LoadingLayout;

public class PageUtils {

    public static void finishRefreshData(RefreshLayout refreshLayout, LoadingLayout loadingLayout, Integer page, Integer currentPage, boolean hasData) {



        if (refreshLayout.getState() == RefreshState.Loading) {
            //当前是加载更多
            if (page < currentPage) {
                refreshLayout.finishLoadMore(1000);
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        } else if (refreshLayout.getState() == RefreshState.Refreshing) {
            if (page < currentPage) {
                refreshLayout.resetNoMoreData();
            }
//            if (response.getList() != null && response.getList().size() > 0) {
//                mAdapter.refresh(response.getList());
//            }
            if (hasData) {
                loadingLayout.showContent();
                refreshLayout.finishRefresh();
            } else {
                refreshLayout.finishRefreshWithNoMoreData();
                loadingLayout.showEmpty();
            }
        }  //当前没有任何状态的话 应该直接returun


//        if (isLoadMore) {
////            refreshLayout.setEnableLoadMore(page > currentPage);
////            if (response.getList() != null && response.getList().size() > 0) {
////                mAdapter.loadMore(response.getList());
////            }
//            if (page < currentPage) {
//                refreshLayout.finishLoadMore(1500);
//            } else {
//                refreshLayout.finishLoadMoreWithNoMoreData();
//            }
//        } else {
////            refreshLayout.setEnableLoadMore(page > currentPage);
//            if (page < currentPage) {
//                refreshLayout.resetNoMoreData();
//            }
////            if (response.getList() != null && response.getList().size() > 0) {
////                mAdapter.refresh(response.getList());
////            }
//            if (hasData) {
//                refreshLayout.finishRefresh(1500);
//            } else {
//                refreshLayout.finishRefreshWithNoMoreData();
//            }
//
//        }
    }
}

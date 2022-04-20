package com.xuexiang.templateproject.fragment.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.base.broccoli.BroccoliSimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentHomeBinding;
import com.xuexiang.templateproject.http.goods.api.GoodsApi;
import com.xuexiang.templateproject.http.goods.entity.GoodsListDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.templateproject.utils.GoodsClassifyUtils;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.samlss.broccoli.Broccoli;

@Page
public class GridItemFragment extends BaseFragment<FragmentHomeBinding> {

    public static final String KEY_TITLE_NAME = "title_name";

    private SimpleDelegateAdapter<GoodsListDTO.GoodsDTO> mNewsAdapter;

    private int listCurrentFrom = 1;  //当前页数

    /**
     * 自动注入参数，不能是private
     */
    @AutoWired(name = KEY_TITLE_NAME)
    String title;

    @NonNull
    @Override
    protected FragmentHomeBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initArgs() {
        // 自动注入参数必须在initArgs里进行注入
        XRouter.getInstance().inject(this);
    }

    @Override
    protected String getPageTitle() {
        return title;
    }



    @Override
    protected void initViews() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        binding.recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        mNewsAdapter = new BroccoliSimpleDelegateAdapter<GoodsListDTO.GoodsDTO>(R.layout.adapter_news_card_view_list_item, new LinearLayoutHelper(), new ArrayList<>()) {
            @Override
            protected void onBindData(RecyclerViewHolder holder, GoodsListDTO.GoodsDTO model, int position) {
                if (model != null) {
                    holder.text(R.id.tv_user_name, model.getCreator());
                    holder.text(R.id.tv_tag, GoodsClassifyUtils.translationClassify(model.getClassification()));
                    holder.text(R.id.tv_title, model.getGoodsName());
                    holder.text(R.id.tv_summary, model.getGoodsDescribe());
//                    holder.text(R.id.tv_praise, model.getPraise() == 0 ? "点赞" : String.valueOf(model.getPraise()));
//                    holder.text(R.id.tv_comment, model.getComment() == 0 ? "评论" : String.valueOf(model.getComment()));
//                    holder.text(R.id.tv_read, "阅读量 " + model.getRead());
                    holder.image(R.id.iv_image, model.getGoodsPicUrl());

                    holder.click(R.id.card_view, v -> {
                        openNewPage(GoodsDetailsFragment.class, GoodsDetailsFragment.KEY_GOODS_ID, model.getId());
                    });
                }
            }

            @Override
            protected void onBindBroccoli(RecyclerViewHolder holder, Broccoli broccoli) {
                broccoli.addPlaceholders(
                        holder.findView(R.id.tv_user_name),
                        holder.findView(R.id.tv_tag),
                        holder.findView(R.id.tv_title),
                        holder.findView(R.id.tv_summary),
//                        holder.findView(R.id.tv_praise),
//                        holder.findView(R.id.tv_comment),
//                        holder.findView(R.id.tv_read),
                        holder.findView(R.id.iv_image)
                );
            }
        };

        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapter(mNewsAdapter);
        binding.recyclerView.setAdapter(delegateAdapter);
    }


    @Override
    protected void initListeners() {
        //下拉刷新
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            listCurrentFrom = 1;  //刷新只加载第一页
            Map<String,Object> params = new HashMap<>();
            params.put("from",listCurrentFrom);
            params.put("pageSize", Constant.pageSize);
            params.put("classification",GoodsClassifyUtils.translationClassify(title));
            XHttp.post(GoodsApi.queryGoodsPages())
                    .upJson(JsonUtil.toJson(params))
                    .execute(new TipCallBack<GoodsListDTO>() {
                        @Override
                        public void onSuccess(GoodsListDTO response) throws Throwable {
                            binding.refreshLayout.setEnableLoadMore(response.getPages() > listCurrentFrom);
                            if (response.getPages() < listCurrentFrom) {
                                refreshLayout.resetNoMoreData();
                            }
                            if (response.getList() != null && response.getList().size() > 0) {
                                mNewsAdapter.refresh(response.getList());
                            }
                            refreshLayout.finishRefresh();
                        }
                    });
        });
        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            listCurrentFrom++;

            Map<String,Object> params = new HashMap<>();
            params.put("from",listCurrentFrom);
            params.put("pageSize", Constant.pageSize);
            params.put("classification",GoodsClassifyUtils.translationClassify(title));
            XHttp.post(GoodsApi.queryGoodsPages())
                    .upJson(JsonUtil.toJson(params))
                    .execute(new TipCallBack<GoodsListDTO>() {
                        @Override
                        public void onSuccess(GoodsListDTO response) throws Throwable {
                            binding.refreshLayout.setEnableLoadMore(response.getPages() > listCurrentFrom);
                            if (response.getList() != null && response.getList().size() > 0) {
                                mNewsAdapter.loadMore(response.getList());
                            }
                            if (response.getPages() < listCurrentFrom) {
                                refreshLayout.finishLoadMore();
                            } else {
                                refreshLayout.finishLoadMoreWithNoMoreData();
                            }
                        }
                    });
        });
        binding.refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果
    }
}

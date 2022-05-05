
package com.xuexiang.templateproject.fragment.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.base.broccoli.BroccoliSimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.base.delegate.SingleDelegateAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentHomeBinding;
import com.xuexiang.templateproject.http.goods.api.GoodsApi;
import com.xuexiang.templateproject.http.goods.entity.GoodsListDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.templateproject.utils.DemoDataProvider;
import com.xuexiang.templateproject.utils.GoodsClassifyUtils;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.samlss.broccoli.Broccoli;

@Page(anim = CoreAnim.none)
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    private SimpleDelegateAdapter<GoodsListDTO.GoodsDTO> mNewsAdapter;

    private int listCurrentFrom = 1;  //当前页数

    private String searchKey = "";

    @NonNull
    @Override
    protected FragmentHomeBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater, container, false);
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
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        binding.recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);

        //轮播条
        SingleDelegateAdapter bannerAdapter = new SingleDelegateAdapter(R.layout.include_head_view_banner) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                SimpleImageBanner banner = holder.findViewById(R.id.sib_simple_usage);
                banner.setSource(DemoDataProvider.getBannerList()).startScroll();
            }
        };

        //九宫格菜单
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setPadding(0, 16, 0, 0);
        gridLayoutHelper.setVGap(10);
        gridLayoutHelper.setHGap(0);
        SimpleDelegateAdapter<AdapterItem> commonAdapter = new SimpleDelegateAdapter<AdapterItem>(R.layout.adapter_common_grid_item, gridLayoutHelper, DemoDataProvider.getGridItems(getContext())) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, AdapterItem item) {
                if (item != null) {
                    RadiusImageView imageView = holder.findViewById(R.id.riv_item);
                    imageView.setCircle(true);
                    ImageLoader.get().loadImage(imageView, item.getIcon());
                    holder.text(R.id.tv_title, item.getTitle().toString().substring(0, 1));
                    holder.text(R.id.tv_sub_title, item.getTitle());

                    holder.click(R.id.ll_container, v -> {
//                        XToastUtils.toast("点击了：" + item.getTitle());
                        // 注意: 这里由于NewsFragment是使用Viewpager加载的，并非使用XPage加载的，因此没有承载Activity， 需要使用openNewPage。
                        openNewPage(GridItemFragment.class, GridItemFragment.KEY_TITLE_NAME, item.getTitle());
                    });
                }
            }
        };

        //资讯的标题
        SingleDelegateAdapter searchAdapter = new SingleDelegateAdapter(R.layout.adapter_title_item) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
//
//                mSearchView.setVoiceSearch(false);
//                mSearchView.setEllipsize(true);
//                mSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
//                mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//                        SnackbarUtils.Long(mSearchView, "Query: " + query).show();
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//                        //Do some magic
//                        return false;
//                    }
//                });
//                mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//                    @Override
//                    public void onSearchViewShown() {
//                        //Do some magic
//                    }
//
//                    @Override
//                    public void onSearchViewClosed() {
//                        //Do some magic
//                    }
//                });
//                mSearchView.setSubmitOnClick(true);

//                holder.text(R.id.tv_title, "课程");
//                holder.text(R.id.tv_action, "更多");
//                holder.click(R.id.tv_action, v -> XToastUtils.toast("更多"));
            }
        };

        mNewsAdapter = new BroccoliSimpleDelegateAdapter<GoodsListDTO.GoodsDTO>(R.layout.adapter_news_card_view_list_item, new LinearLayoutHelper(), new ArrayList<>()) {
            @Override
            protected void onBindData(RecyclerViewHolder holder, GoodsListDTO.GoodsDTO model, int position) {
                if (model != null) {
                    holder.text(R.id.tv_user_name, model.getCreator());
                    holder.text(R.id.tv_tag, GoodsClassifyUtils.translationClassify(model.getClassification()));
                    holder.text(R.id.tv_title, model.getGoodsName());
                    holder.text(R.id.tv_summary, model.getGoodsDescribe());
                    holder.image(R.id.iv_image, model.getGoodsPicUrl());

                    //点击后应该跳向详情
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
                        holder.findView(R.id.iv_image)
                );
            }
        };

        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapter(bannerAdapter);
        delegateAdapter.addAdapter(commonAdapter);
        delegateAdapter.addAdapter(searchAdapter);
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
            params.put("searchKey", searchKey);
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
            params.put("searchKey", searchKey);
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
        binding.refreshLayout.autoRefresh();//第一次进入触发自动刷新
    }
}

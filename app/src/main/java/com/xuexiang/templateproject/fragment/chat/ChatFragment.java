package com.xuexiang.templateproject.fragment.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentRefreshBasicBinding;
import com.xuexiang.templateproject.http.chat.api.ChatApi;
import com.xuexiang.templateproject.http.chat.entity.ChatRoomListDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.templateproject.utils.MMKVUtils;
import com.xuexiang.templateproject.utils.PageUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.CollectionUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;


@Page(anim = CoreAnim.none)
public class ChatFragment extends BaseFragment<FragmentRefreshBasicBinding> {

    private SimpleDelegateAdapter<ChatRoomListDTO.ChatRoomItem> mAdapter;

    private int listCurrentFrom = 1;  //当前页数

    private boolean isFirst = true;

    @NonNull
    @Override
    protected FragmentRefreshBasicBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentRefreshBasicBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initViews() {
        WidgetUtils.initRecyclerView(binding.recyclerView);

        mAdapter = new SimpleDelegateAdapter<ChatRoomListDTO.ChatRoomItem>(R.layout.item_chat_room,new LinearLayoutHelper()) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, ChatRoomListDTO.ChatRoomItem item) {
                holder.text(R.id.name, item.getReceiveUserInfo().getUsername());
                holder.text(R.id.content, CollectionUtils.isEmpty(item.getChatRecords()) ? "" : item.getChatRecords().get(item.getChatRecords().size() - 1).getMsgContent());
                holder.text(R.id.time, item.getLastRecordTimeView());
                holder.click(R.id.ll_chat_room, new View.OnClickListener() {
                    @SingleClick
                    @Override
                    public void onClick(View view) {
                        openNewPage(ChatContentFragment.class, ChatContentFragment.KEY_CHAT_INFO, item);
                    }
                });
            }
        };
//        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
//        binding.recyclerView.setBackgroundColor(Color.parseColor("#FFF1F1F1"));
        binding.recyclerView.setAdapter(mAdapter);

        // 开启自动加载功能（非必须）
        binding.refreshLayout.setEnableAutoLoadMore(true);
        // 下拉刷新
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            listCurrentFrom = 1;  //刷新只加载第一页
            ChatApi.getChatRoomList(listCurrentFrom, new TipCallBack<ChatRoomListDTO>() {
                @Override
                public void onSuccess(ChatRoomListDTO response) throws Throwable {
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

            ChatApi.getChatRoomList(listCurrentFrom, new TipCallBack<ChatRoomListDTO>() {
                @Override
                public void onSuccess(ChatRoomListDTO response) throws Throwable {
                    PageUtils.finishRefreshData(refreshLayout,binding.loading,response.getPages(),listCurrentFrom,response.getTotal() > 0);

                    if (response.getList() != null && response.getList().size() > 0) {
                        mAdapter.loadMore(response.getList());
                    }
                }
            });
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        //重新显示的时候判断一下是否需要刷新
        if (MMKVUtils.getBoolean(Constant.chatRoomListIsRefresh, false) || isFirst) {
            binding.refreshLayout.autoRefresh();
            isFirst = false;  //走过一次onResume就不是第一次进入了
            //刷新完就不用重复刷新了
            MMKVUtils.put(Constant.chatRoomListIsRefresh, false);
        }
    }

    @Override
    protected TitleBar initTitle() {
        return null;
    }
}

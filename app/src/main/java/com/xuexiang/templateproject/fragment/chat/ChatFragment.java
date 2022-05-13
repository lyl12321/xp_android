package com.xuexiang.templateproject.fragment.chat;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.google.gson.reflect.TypeToken;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentRefreshBasicBinding;
import com.xuexiang.templateproject.http.chat.api.ChatApi;
import com.xuexiang.templateproject.http.chat.entity.ChatRoomListDTO;
import com.xuexiang.templateproject.http.websocket.WebSocketResponseDTO;
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
import com.xuexiang.xutil.net.JsonUtil;
import com.zhangke.websocket.SimpleListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;

import java.util.List;


@Page(anim = CoreAnim.none)
public class ChatFragment extends BaseFragment<FragmentRefreshBasicBinding> {

    private SimpleDelegateAdapter<ChatRoomListDTO.ChatRoomItem> mAdapter;

    private int listCurrentFrom = 1;  //当前页数

    private boolean isFirst = true;


    private WebSocketManager manager = WebSocketHandler.getDefault();

    private SimpleListener listener;

    @NonNull
    @Override
    protected FragmentRefreshBasicBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentRefreshBasicBinding.inflate(inflater, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void initViews() {
        WidgetUtils.initRecyclerView(binding.recyclerView);

        mAdapter = new SimpleDelegateAdapter<ChatRoomListDTO.ChatRoomItem>(R.layout.item_chat_room, new LinearLayoutHelper()) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, ChatRoomListDTO.ChatRoomItem item) {
                holder.text(R.id.name, item.getReceiveUserInfo().getUsername());
                holder.text(R.id.content, CollectionUtils.isEmpty(item.getChatRecords()) ? "" : item.getChatRecords().get(item.getChatRecords().size() - 1).getMsgContent());
                holder.text(R.id.time, item.getLastRecordTimeView());
                if (item.getNoReadMessage() == 0) {
                    holder.findViewById(R.id.tv_no_read_count).setVisibility(View.GONE);
                } else {
                    holder.findViewById(R.id.tv_no_read_count).setVisibility(View.VISIBLE);
                    holder.text(R.id.tv_no_read_count, String.valueOf(item.getNoReadMessage()));
                }
                holder.click(R.id.ll_chat_room, new View.OnClickListener() {
                    @SingleClick
                    @Override
                    public void onClick(View view) {
                        item.setNoReadMessage(0);
                        mAdapter.notifyItemChanged(position);


                        ChatApi.clearNoReadMessage(item.getId(), new TipCallBack<String>() {
                            @Override
                            public void onSuccess(String response) throws Throwable {
                            }
                        });

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
            if (isFirst || CollectionUtils.isEmpty(mAdapter.getData())) {
                ChatApi.getChatRoomList(listCurrentFrom, new TipCallBack<ChatRoomListDTO>() {
                    @Override
                    public void onSuccess(ChatRoomListDTO response) throws Throwable {
                        PageUtils.finishRefreshData(refreshLayout, binding.loading, response.getPages(), listCurrentFrom, response.getTotal() > 0);

                        if (response.getList() != null && response.getList().size() > 0) {
                            mAdapter.refresh(response.getList());
                        }
                    }
                });
            } else {

                mAdapter.notifyDataSetChanged();

                refreshLayout.finishRefreshWithNoMoreData();

            }

        });
        // 上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            listCurrentFrom++;

            ChatApi.getChatRoomList(listCurrentFrom, new TipCallBack<ChatRoomListDTO>() {
                @Override
                public void onSuccess(ChatRoomListDTO response) throws Throwable {
                    PageUtils.finishRefreshData(refreshLayout, binding.loading, response.getPages(), listCurrentFrom, response.getTotal() > 0);

                    if (response.getList() != null && response.getList().size() > 0) {
                        mAdapter.loadMore(response.getList());
                    }
                }
            });
        });

    }

    @Override
    protected void initListeners() {
        listener = new SimpleListener() {
            @Override
            public <T> void onMessage(String message, T data) {

                WebSocketResponseDTO<ChatRoomListDTO.ChatRoomItem.ChatRecordsDTO> response = JsonUtil.fromJson(message, new TypeToken<WebSocketResponseDTO<ChatRoomListDTO.ChatRoomItem.ChatRecordsDTO>>() {
                }.getType());
                if (response.getType() == 5) {

                    List<ChatRoomListDTO.ChatRoomItem> ch = mAdapter.getData();
                    boolean hasRoom = false;
                    for (ChatRoomListDTO.ChatRoomItem chatRoomItem : ch) {
                        if (response.getData().getChatRoomId().equals(chatRoomItem.getId())) {
                            //说明就是这个房间有新消息
                            if (response.getData().getReadStatus().equals("0")) {
                                chatRoomItem.setNoReadMessage(chatRoomItem.getNoReadMessage() + 1);
                            }
                            chatRoomItem.getChatRecords().add(response.getData());
                            if (ChatFragment.this.isVisible()) {
                                mAdapter.notifyItemChanged(ch.indexOf(chatRoomItem));
                            }
                            hasRoom = true;
                        }
                    }
                    if (!hasRoom) {
                        //如果没有这里面的消息 就是需要从服务器拉一次
                        isFirst = true;
                        binding.refreshLayout.autoRefresh();
                    }
                }
            }
        };
        manager.addListener(listener);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (manager != null) {
            manager.removeListener(listener);
        }
    }
}

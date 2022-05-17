package com.xuexiang.templateproject.fragment.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.base.BaseRecyclerAdapter;
import com.xuexiang.templateproject.adapter.base.SmartViewHolder;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentChatContentBinding;
import com.xuexiang.templateproject.http.chat.api.ChatApi;
import com.xuexiang.templateproject.http.chat.entity.ChatRoomListDTO;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;
import com.xuexiang.templateproject.http.websocket.WebSocketResponseDTO;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.templateproject.utils.MMKVUtils;
import com.xuexiang.templateproject.utils.UserUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.net.JsonUtil;
import com.zhangke.websocket.SimpleListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Page(name = "聊天详情")
public class ChatContentFragment extends BaseFragment<FragmentChatContentBinding> {


    private BaseRecyclerAdapter<ChatRoomListDTO.ChatRoomItem.ChatRecordsDTO> mAdapter;

    public static final String KEY_CHAT_INFO = "chat_info";

    @AutoWired(name = KEY_CHAT_INFO)
    ChatRoomListDTO.ChatRoomItem mData;

    @Override
    protected void initArgs() {
        // 自动注入参数必须在initArgs里进行注入
        XRouter.getInstance().inject(this);
    }


    private int listCurrentFrom = 1;  //当前页数

    private UserDTORes mUser = null;


    private WebSocketManager manager = WebSocketHandler.getDefault();

    private SimpleListener listener;

    @NonNull
    @Override
    protected FragmentChatContentBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentChatContentBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initViews() {

        mUser = UserUtils.getCurrentUser();

        mAdapter = new BaseRecyclerAdapter<ChatRoomListDTO.ChatRoomItem.ChatRecordsDTO>(mData.getChatRecords(),R.layout.item_chat_content) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, ChatRoomListDTO.ChatRoomItem.ChatRecordsDTO model, int position) {
                if (model.getSendId().equals(mUser.getId())) {
                    //如果是我自己的消息

                    holder.gone(R.id.chatting_left);
                    holder.visible(R.id.chatting_right);

                    //设置头像

                    Glide.with(binding.getRoot())
                            .load(mUser.getAvatarUrl())
                            .into((ImageView) holder.itemView.findViewById(R.id.chatting_riv_avatar));
//                    holder.image(R.id.chatting_riv_avatar, message.User.avatarId);

                    if (model.getMsgType().equals("0")) {
                        holder.gone(R.id.chatting_riv_img);
                        holder.text(R.id.chatting_rtv_txt, model.getMsgContent()).visible(R.id.chatting_rtv_txt);
                    } else {
                        //图片暂时不显示
//                        holder.gone(R.id.chatting_rtv_txt);
//                        holder.image(R.id.chatting_riv_img, message.Image).visible(R.id.chatting_riv_img);
                    }

                } else {
                    holder.gone(R.id.chatting_right);
                    holder.visible(R.id.chatting_left);

                    Glide.with(binding.getRoot())
                            .load(mData.getReceiveUserInfo().getAvatarUrl())
                            .into((ImageView) holder.itemView.findViewById(R.id.chatting_liv_avatar));
                    //设置头像
//                    holder.image(R.id.chatting_riv_avatar, message.User.avatarId);

                    if (model.getMsgType().equals("0")) {
                        holder.gone(R.id.chatting_liv_img);
                        holder.text(R.id.chatting_ltv_txt, model.getMsgContent()).visible(R.id.chatting_ltv_txt);
                    } else {
                        //图片暂时不显示
//                        holder.gone(R.id.chatting_rtv_txt);
//                        holder.image(R.id.chatting_riv_img, message.Image).visible(R.id.chatting_riv_img);
                    }
                }
            }
        };

//        View arrow =  binding.footer.findViewById(ClassicsFooter.ID_IMAGE_ARROW);
//        arrow.setScaleY(-1);//必须设置


        binding.listView.setAdapter(mAdapter);

        binding.listView.scrollToPosition(mAdapter.getItemCount() - 1);


//        binding.listView.setScaleY(-1);//必须设置
//        binding.listView.scrollToPosition(mAdapter.getItemCount() - 1);


//        binding.refreshLayout.setEnablePureScrollMode(true);
//        binding.refreshLayout.setEnableRefresh(false);//必须关闭
//        binding.refreshLayout.setEnableLoadMore(false);
//        binding.refreshLayout.setEnableAutoLoadMore(false);//必须关闭
//        binding.refreshLayout.setEnableNestedScroll(false);//必须关闭
//        binding.refreshLayout.setEnableScrollContentWhenLoaded(false);//必须关闭
//        binding.refreshLayout.getLayout().setScaleY(-1);//必须设置
////        binding.refreshLayout.setScrollBoundaryDecider(new ScrollBoundaryDeciderAdapter() {
////            @Override
////            public boolean canLoadMore(View content) {
////                return super.canRefresh(content);//必须替换
////            }
////        });
//
//        //监听加载，而不是监听 刷新
//        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
//
//
//
//
//
//                refreshLayout.getLayout().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        refreshLayout.finishLoadMore();
//                    }
//                }, 2000);
//            }
//        });

    }

    @Override
    protected void initListeners() {
        binding.rbSubmit.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View view) {
                if (!StringUtils.isEmpty(binding.input.getEditableText().toString())) {
                    ChatRoomListDTO.ChatRoomItem.ChatRecordsDTO chat = new ChatRoomListDTO.ChatRoomItem.ChatRecordsDTO();
                    chat.setChatRoomId(mData.getId());
                    chat.setSendId(mUser.getId());
                    chat.setReceiveId(mData.getReceiveUserInfo().getId());
                    chat.setMsgContent(binding.input.getEditableText().toString());

                    Map<String,Object> params = new HashMap<>();
                    params.put("type",2);
                    params.put("data", chat);
                    manager.send(JsonUtil.toJson(params));
                    binding.input.setText("");
                }
            }
        });

        listener = new SimpleListener() {
            @Override
            public <T> void onMessage(String message, T data) {
                WebSocketResponseDTO<ChatRoomListDTO.ChatRoomItem.ChatRecordsDTO> response = JsonUtil.fromJson(message, new TypeToken<WebSocketResponseDTO<ChatRoomListDTO.ChatRoomItem.ChatRecordsDTO>>() {
                }.getType());
                if (response.getType() == 5) {

                    if (response.getData().getChatRoomId().equals(mData.getId())) {

                        mAdapter.loadMore(Arrays.asList(response.getData()));
                        mAdapter.notifyListDataSetChanged();
                        binding.listView.scrollToPosition(mAdapter.getItemCount() - 1);


                        ChatApi.clearNoReadMessage(mData.getId(), new TipCallBack<String>() {
                            @Override
                            public void onSuccess(String response) throws Throwable {
                            }
                        });
                        //如果有新消息 回去是要刷新页面的
                        MMKVUtils.put(Constant.chatRoomListIsRefresh, true);
                    }

                }
            }
        };

        manager.addListener(listener);
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setLeftText("和 "+mData.getReceiveUserInfo().getUsername()+" 的对话");
        titleBar.setLeftTextSize(48);
        titleBar.setTitle("");
        return titleBar;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (manager != null) {
            manager.removeListener(listener);
        }
    }
}

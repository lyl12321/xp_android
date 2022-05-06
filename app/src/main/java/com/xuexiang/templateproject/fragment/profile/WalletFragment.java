package com.xuexiang.templateproject.fragment.profile;


import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.base.broccoli.BroccoliSimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.base.delegate.SingleDelegateAdapter;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentWalletBinding;
import com.xuexiang.templateproject.http.wallet.api.WalletApi;
import com.xuexiang.templateproject.http.wallet.entity.WalletListDTO;
import com.xuexiang.templateproject.utils.PageUtils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.ArrayList;
import java.util.regex.Pattern;

import me.samlss.broccoli.Broccoli;

@Page(name = "我的钱包")
public class WalletFragment extends BaseFragment<FragmentWalletBinding> {


    private SimpleDelegateAdapter<WalletListDTO.WalletDTO> balanceLogAdapter;

    private int listCurrentFrom = 1;  //当前页数

    @NonNull
    @Override
    protected FragmentWalletBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentWalletBinding.inflate(inflater,container,false);
    }



    @Override
    protected void initViews() {

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        binding.recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);


        //我的余额hear
        SingleDelegateAdapter myBalanceAdapter = new SingleDelegateAdapter(R.layout.item_my_wallet_header) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                WalletApi.getMyBalance(new TipCallBack<Integer>() {
                    @Override
                    public void onSuccess(Integer response) throws Throwable {
                        holder.text(R.id.tv_my_wallet_all,response+"");
                    }
                });

            }
        };



        balanceLogAdapter = new BroccoliSimpleDelegateAdapter<WalletListDTO.WalletDTO>(R.layout.item_my_balance_log, new LinearLayoutHelper(), new ArrayList<>()) {
            @Override
            protected void onBindData(RecyclerViewHolder holder, WalletListDTO.WalletDTO model, int position) {
                if (model != null) {
                    holder.text(R.id.tv_log_title,model.getTradeType().equals("0") ? "转入" : "转出");
                    holder.text(R.id.tv_log_date, model.getCreateTime());
                    holder.text(R.id.tv_log_change,String.valueOf(model.getChangeAmount()));
                }
            }

            @Override
            protected void onBindBroccoli(RecyclerViewHolder holder, Broccoli broccoli) {
                broccoli.addPlaceholders(
                        holder.findView(R.id.tv_log_title),
                        holder.findView(R.id.tv_log_date),
                        holder.findView(R.id.tv_log_change)
                );
            }
        };


        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapter(myBalanceAdapter);
        delegateAdapter.addAdapter(balanceLogAdapter);

        binding.recyclerView.setAdapter(delegateAdapter);

    }

    @Override
    protected void initListeners() {

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getContext())
                        .title("输入激活卡号")
                        .content("输入4位激活卡号，用来充值")
                        .inputType(
                                InputType.TYPE_CLASS_NUMBER)
                        .input(
                                "请输入卡号",
                                "",
                                false,
                                ((dialog, input) -> {
                                }))
                        .positiveText("确定")
                        .negativeText("取消")
                        .onNegative((dialog, which) -> {
                            dialog.dismiss();
                        })
                        .onPositive((dialog, which) -> {
                            String cardCode = dialog.getInputEditText().getText().toString();
                            String reg = ResUtils.getString(R.string.regex_verify_code);
                            if (cardCode != null && !"".equals(cardCode) && Pattern.matches(reg,cardCode)) {
                                WalletApi.verifyCard(cardCode, new TipCallBack<String>() {
                                    @Override
                                    public void onSuccess(String response) throws Throwable {
                                        XToastUtils.success(response);
                                        binding.refreshLayout.autoRefresh();
                                        dialog.dismiss();
                                    }
                                });
                            } else {
                                XToastUtils.warning("卡号校验失败");
                            }
                        })
                        .cancelable(false)
                        .autoDismiss(false)
                        .show();
            }
        });




        //下拉刷新
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            listCurrentFrom = 1;  //刷新只加载第一页
            WalletApi.queryPage(listCurrentFrom, new TipCallBack<WalletListDTO>() {
                @Override
                public void onSuccess(WalletListDTO response) throws Throwable {
                    PageUtils.finishRefreshData(refreshLayout,binding.loading,response.getPages(),listCurrentFrom,response.getTotal() > 0);
                    binding.loading.showContent();
                    if (response.getList() != null && response.getList().size() > 0) {
                        balanceLogAdapter.refresh(response.getList());
                    }
                }
            });
        });
//        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            listCurrentFrom++;
            WalletApi.queryPage(listCurrentFrom, new TipCallBack<WalletListDTO>() {
                @Override
                public void onSuccess(WalletListDTO response) throws Throwable {
                    PageUtils.finishRefreshData(refreshLayout,binding.loading,response.getPages(),listCurrentFrom,response.getTotal() > 0);

                    if (response.getList() != null && response.getList().size() > 0) {
                        balanceLogAdapter.loadMore(response.getList());
                    }
                }
            });
        });
        binding.refreshLayout.autoRefresh();//第一次进入触发自动刷新



    }
}

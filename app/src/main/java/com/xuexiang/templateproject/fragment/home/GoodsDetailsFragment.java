package com.xuexiang.templateproject.fragment.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentGoodsDetailsBinding;
import com.xuexiang.templateproject.http.goods.api.GoodsApi;
import com.xuexiang.templateproject.http.goods.entity.GoodsListDTO;
import com.xuexiang.templateproject.http.order.api.OrderApi;
import com.xuexiang.templateproject.http.wallet.api.WalletApi;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

@Page(name = "商品详情")
public class GoodsDetailsFragment extends BaseFragment<FragmentGoodsDetailsBinding> {

    public static final String KEY_GOODS_ID = "goods_id";

    @AutoWired(name = KEY_GOODS_ID)
    String id;

    private boolean hasThisGoods = false;

    @Override
    protected void initArgs() {
        // 自动注入参数必须在initArgs里进行注入
        XRouter.getInstance().inject(this);
    }

    @NonNull
    @Override
    protected FragmentGoodsDetailsBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentGoodsDetailsBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initViews() {


        GoodsApi.getGoodsInfo(id, new TipCallBack<GoodsListDTO.GoodsDTO>() {
            @Override
            public void onSuccess(GoodsListDTO.GoodsDTO response) throws Throwable {
//                binding.rivHeadPic.setImageURI(response.getGoodsPicUrl());
                if (getActivity() != null && getContext() != null) {
                    Glide.with(getContext())
                            .load(response.getGoodsPicUrl())
                            .into(binding.rivHeadPic);
                    binding.stUsername.setLeftString(response.getCreator());
                    binding.stUsername.setRightString("课程价格: ￥"+response.getGoodsPrice());
                    binding.tvTitle.setText(response.getGoodsName());
                    binding.tvContent.setText(response.getGoodsDescribe());
                }
            }
        });

        OrderApi.hasThisGoods(id, new TipCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean response) throws Throwable {
                hasThisGoods = response;
                if (response) {
                    binding.fab.setImageResource(R.drawable.ic_baseline_check_24);
                } else {
                    binding.fab.setImageResource(R.drawable.ic_baseline_account_balance_wallet_24);
                }
            }
        });


    }

    @Override
    protected void initListeners() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View view) {
                if (hasThisGoods) {
                    XToastUtils.warning("你已经购买过这个课程了");
                    return;
                }
                new MaterialDialog.Builder(getContext())
                        .content("确定要购买这个课程吗")
                        .positiveText("是")
                        .negativeText("否")
                        .onPositive((dialog, which) -> WalletApi.buyGoods(id, new TipCallBack<String>() {
                            @Override
                            public void onSuccess(String response) throws Throwable {
                                XToastUtils.success(response);
                                hasThisGoods = true;
                                binding.fab.setImageResource(R.drawable.ic_baseline_check_24);
                            }
                        }))
                        .show();
            }
        });
    }
}

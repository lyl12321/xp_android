package com.xuexiang.templateproject.fragment.moments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.core.http.loader.MiniLoadingDialogLoader;
import com.xuexiang.templateproject.databinding.FragmentPublishMomentsBinding;
import com.xuexiang.templateproject.http.moments.api.MomentsApi;
import com.xuexiang.templateproject.utils.Constant;
import com.xuexiang.templateproject.utils.MMKVUtils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;


@Page(name = "发布朋友圈",anim = CoreAnim.present)
public class PublishMomentsFragment extends BaseFragment<FragmentPublishMomentsBinding> {

    private MiniLoadingDialogLoader miniLoading;

    @NonNull
    @Override
    protected FragmentPublishMomentsBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentPublishMomentsBinding.inflate(inflater,container,false);
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();

        titleBar.setLeftImageDrawable(ResUtils.getVectorDrawable(getContext(), R.drawable.ic_web_close));

        return titleBar;
    }

    @Override
    protected void initViews() {
        miniLoading = new MiniLoadingDialogLoader(getActivity());
        miniLoading.setCancelable(true);
        miniLoading.setOnProgressCancelListener(this::popToBack);

        binding.rbSubmit.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View view) {
                String content = binding.mletContent.getEditText().getText().toString();
                if (content != null && !"".equals(content)) {
                    publishContent(content);
                }
            }
        });
    }

    private void publishContent(String content) {
        miniLoading.showLoading();
        MomentsApi.publishMoment(content, new TipCallBack<String>() {
            @Override
            public void onSuccess(String response) throws Throwable {
                XToastUtils.success(response);
                miniLoading.dismissLoading();
                //放置一个永久的flag用来判断是否刷新
                MMKVUtils.put(Constant.momentsListIsRefresh, true);
                popToBack();
            }

            @Override
            public void onError(ApiException e) {
                super.onError(e);
                miniLoading.dismissLoading();
            }
        });
    }
}

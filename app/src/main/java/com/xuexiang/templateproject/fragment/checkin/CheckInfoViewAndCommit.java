package com.xuexiang.templateproject.fragment.checkin;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.core.http.loader.MiniLoadingDialogLoader;
import com.xuexiang.templateproject.databinding.FragmentCheckInDetailsBinding;
import com.xuexiang.templateproject.http.check.api.CheckSubApi;
import com.xuexiang.templateproject.http.check.entity.CheckDetailsDTO;
import com.xuexiang.templateproject.http.check.entity.CheckListDTO;
import com.xuexiang.templateproject.utils.UserUtils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xutil.net.JsonUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Page(name = "签到详情")
public class CheckInfoViewAndCommit extends BaseFragment<FragmentCheckInDetailsBinding> {

    public static final String CHECK_OBJECT = "check_object";


    @AutoWired(name = CHECK_OBJECT)
    CheckListDTO.CheckListItem mainCheckInfo;

    private MiniLoadingDialogLoader miniLoading;

    @Override
    protected void initArgs() {
        // 自动注入参数必须在initArgs里进行注入
        XRouter.getInstance().inject(this);
    }


    @NonNull
    @Override
    protected FragmentCheckInDetailsBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentCheckInDetailsBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initViews() {
        miniLoading = new MiniLoadingDialogLoader(getActivity());
        miniLoading.setCancelable(true);
        miniLoading.setOnProgressCancelListener(this::popToBack);

        //设置一些参数
        binding.tvCheckTitle.setText(mainCheckInfo.getCheckTitle());
        binding.stCheckDesc.setLeftString(mainCheckInfo.getCheckDesc());

        if (mainCheckInfo.getUserCheckStatus().equals("未签到") || mainCheckInfo.getUserCheckStatus().equals("已过期")) {
            binding.rbSubmit.setVisibility(View.VISIBLE);
        } else {
            openReadOnly();
            miniLoading.showLoading();
            //请求一下提交的信息
            CheckSubApi.getCheckDetails(mainCheckInfo.getId(), UserUtils.getCurrentUser().getId(), new TipCallBack<CheckDetailsDTO>() {
                @Override
                public void onSuccess(CheckDetailsDTO response) throws Throwable {
                    Type type = new TypeToken<Map<String,Integer>>() {}.getType();
                    Map<String,Integer> map = JsonUtil.fromJson(response.getCheckContent(), type);

                    checkButtonByPos(binding.rgReq1, map.get("rgReq1"));
                    checkButtonByPos(binding.rgReq2, map.get("rgReq2"));
                    checkButtonByPos(binding.rgReq3, map.get("rgReq3"));

                    miniLoading.dismissLoading();
                }

                @Override
                public void onError(ApiException e) {
                    super.onError(e);
                    miniLoading.dismissLoading();
                }
            });
        }

    }




    @Override
    protected void initListeners() {
        binding.rbSubmit.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View view) {
                if (binding.rgReq1.getCheckedRadioButtonId() == -1
                    || binding.rgReq2.getCheckedRadioButtonId() == -1
                        || binding.rgReq3.getCheckedRadioButtonId() == -1
                ) {
                    XToastUtils.error("请选择选项！");
                    return;
                }
                miniLoading.showLoading();
                Map<String,Object> params = new HashMap<>();
                params.put("rgReq1",getCurrentSelectButton(binding.rgReq1));
                params.put("rgReq2",getCurrentSelectButton(binding.rgReq2));
                params.put("rgReq3",getCurrentSelectButton(binding.rgReq3));

                params.put("checkContent", JsonUtil.toJson(params));

                if (mainCheckInfo.getUserCheckStatus().equals("已过期")) {
                    params.put("status","3");
                } else if (mainCheckInfo.getUserCheckStatus().equals("未签到")) {
                    params.put("status","1");
                }


                CheckSubApi.submitCheckInfo(mainCheckInfo.getId(), params, new TipCallBack<String>() {
                    @Override
                    public void onSuccess(String response) throws Throwable {
                        XToastUtils.success(response);
                        openReadOnly();
                        Intent intent = new Intent();
                        intent.putExtra("isNeedRefreshList", true);
                        setFragmentResult(1000,intent);
                        miniLoading.dismissLoading();
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        miniLoading.dismissLoading();
                    }
                });

            }
        });
    }



    public void openReadOnly() {
        binding.rbSubmit.setVisibility(View.GONE);
        disableRadioGroup(binding.rgReq1);
        disableRadioGroup(binding.rgReq2);
        disableRadioGroup(binding.rgReq3);
    }

    public void disableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(false);
        }
    }

    public int getCurrentSelectButton(RadioGroup testRadioGroup) {
        int s = 0;
        for (int i = s; i < testRadioGroup.getChildCount(); i++) {
            if (testRadioGroup.getChildAt(i).getId() == testRadioGroup.getCheckedRadioButtonId()) {
                s = i;
                break;
            }
        }
        return s;
    }

    public void checkButtonByPos(RadioGroup testRadioGroup,int pos) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            if (i == pos) {
               testRadioGroup.check(testRadioGroup.getChildAt(i).getId());
            }
        }
    }


    public void enableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(true);
        }
    }
}

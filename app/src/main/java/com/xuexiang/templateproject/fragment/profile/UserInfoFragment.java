package com.xuexiang.templateproject.fragment.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentUserinfoBinding;
import com.xuexiang.templateproject.http.user.api.UserApi;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;
import com.xuexiang.templateproject.utils.UserUtils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.HashMap;
import java.util.regex.Pattern;

@Page(name = "用户信息")
public class UserInfoFragment extends BaseFragment<FragmentUserinfoBinding> implements SuperTextView.OnSuperTextViewClickListener {


    @NonNull
    @Override
    protected FragmentUserinfoBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentUserinfoBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initViews() {
        UserDTORes userDTO = UserUtils.getCurrentUser();
        if (userDTO.getId() != null && !userDTO.getId().equals("")) {
            binding.stUsername.setRightString(userDTO.getUsername());
            binding.stPhone.setRightString(userDTO.getPhone());
            binding.stClassCode.setRightString(userDTO.getClassCode());
        }
    }


    @Override
    protected void initListeners() {
        binding.stUsername.setOnSuperTextViewClickListener(this);
        binding.stPhone.setOnSuperTextViewClickListener(this);
        binding.stClassCode.setOnSuperTextViewClickListener(this);
        binding.stPassword.setOnSuperTextViewClickListener(this);
    }

    @SingleClick
    @Override
    public void onClick(SuperTextView view) {
        int id = view.getId();
        if (id == R.id.st_username) {
            XToastUtils.toast("暂时不支持修改");
        } else if (id == R.id.st_phone) {
            XToastUtils.toast("暂时不支持修改");
        } else if (id == R.id.st_password) {
            new MaterialDialog.Builder(getContext())
                    .title("修改密码")
                    .content("请在下方修改你的密码")
                    .input(
                            "请输入密码",
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
                        String newPassword = dialog.getInputEditText().getText().toString();
                        String reg = ResUtils.getString(R.string.regex_password);
                        if (newPassword != null && !"".equals(newPassword) && Pattern.matches(reg,newPassword)) {
                            HashMap<String,Object> params = new HashMap<>();
                            params.put("password",newPassword);

                            XHttp.post(UserApi.updatePass())
                                    .upJson(JsonUtil.toJson(params))
                                    .execute(new TipCallBack<String>() {
                                        @Override
                                        public void onSuccess(String response) throws Throwable {
                                            XToastUtils.success(response);
                                            dialog.dismiss();
                                        }
                                    });
                        } else {
                            XToastUtils.warning("密码校验失败");
                        }
                    })
                    .cancelable(false)
                    .autoDismiss(false)
                    .show();
        } else if (id == R.id.st_class_code) {
            XToastUtils.toast("暂时不支持修改");
        }

    }
}

package com.xuexiang.templateproject.fragment.other;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentRegisterBinding;
import com.xuexiang.templateproject.http.user.api.UserService;
import com.xuexiang.templateproject.http.user.entity.RegisterUserDTO;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.cache.model.CacheMode;
import com.xuexiang.xhttp2.request.CustomRequest;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

@Page(anim = CoreAnim.none)
public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> implements View.OnClickListener{


    @NonNull
    @Override
    protected FragmentRegisterBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentRegisterBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initViews() {








    }

    @Override
    protected void initListeners() {
        binding.btnRegister.setOnClickListener(this);
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle()
                .setImmersive(true);
        titleBar.setBackgroundColor(Color.TRANSPARENT);
        titleBar.setTitle("");
        titleBar.setLeftImageDrawable(ResUtils.getVectorDrawable(getContext(), R.drawable.ic_login_close));
        return titleBar;
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_register) {
            if (!binding.etPhoneNumber.validate()) {
                return;
            }
            if (!binding.etUsername.validate()) {
                return;
            }
            if (!binding.etPassword.validate()){
                return;
            }
            if (!binding.etPasswordToo.validate()) {
                return;
            }
            if (!binding.etPassword.getEditValue().equals(binding.etPasswordToo.getEditValue())) {
                return;
            }
            if (!binding.etClassCode.validate()) {
                return;
            }
            if (binding.rgUserType.getCheckedRadioButtonId() == -1) {
                XToastUtils.error("请选择您的身份");
                return;
            }


            //校验全部通过
            //设置User的值
            RegisterUserDTO registerUserDTO = new RegisterUserDTO();
            registerUserDTO.setPhone(binding.etPhoneNumber.getEditValue());
            registerUserDTO.setUsername(binding.etUsername.getEditValue());
            registerUserDTO.setPassword(binding.etPassword.getEditValue());
            registerUserDTO.setClassCode(binding.etClassCode.getEditValue());
            registerUserDTO.setUserType(binding.rgUserType.getCheckedRadioButtonId() == R.id.option1 ? "1" : "2");  //1是老师  2是学生


            CustomRequest request = XHttp.custom().cacheMode(CacheMode.NO_CACHE);
            request.apiCall(request.create(UserService.class).register(registerUserDTO), new TipCallBack<String>() {
                @Override
                public void onSuccess(String response) throws Throwable {
                    XToastUtils.success(response);
                    new MaterialDialog.Builder(getContext())
                            .content("是否直接进行登陆？")
                            .positiveText(R.string.lab_yes)
                            .negativeText(R.string.lab_no)
                            .onPositive((dialog, which) -> {

                            })
                            .show();
                }
            });
        }
    }
}

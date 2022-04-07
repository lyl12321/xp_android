package com.xuexiang.templateproject.fragment.other;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.NoTipCallBack;
import com.xuexiang.templateproject.databinding.FragmentRegisterBinding;
import com.xuexiang.templateproject.http.base.entity.BaseDTO;
import com.xuexiang.templateproject.http.user.api.UserService;
import com.xuexiang.templateproject.http.user.entity.User;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.cache.model.CacheMode;
import com.xuexiang.xhttp2.request.CustomRequest;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;

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
            if (binding.etPhoneNumber.validate()) {
                XToastUtils.error("手机号码校验不通过");
                return;
            }
            if (binding.etUsername.validate()) {
                XToastUtils.error("用户名不能为空");
                return;
            }
            if (binding.etPassword.validate()) {
                XToastUtils.error("密码不能为空");
                return;
            }
            if (binding.etPasswordToo.validate()) {
                XToastUtils.error("再次输入密码不能为空");
                return;
            }
            if (binding.etPassword.getEditValue().equals(binding.etPasswordToo.getEditValue())) {
                XToastUtils.error("两次输入密码不一样");
                return;
            }
            if (binding.etClassCode.validate()) {
                XToastUtils.error("班级代码不能为空");
                return;
            }
            CustomRequest request = XHttp.custom().cacheMode(CacheMode.NO_CACHE);
            request.apiCall(request.create(UserService.class).register(new User()), new NoTipCallBack<BaseDTO<String>>() {
                @Override
                public void onSuccess(BaseDTO<String> response) throws Throwable {

                }
            });
        }
    }
}

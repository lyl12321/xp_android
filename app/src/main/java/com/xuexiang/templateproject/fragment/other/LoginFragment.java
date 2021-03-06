

package com.xuexiang.templateproject.fragment.other;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.activity.MainActivity;
import com.xuexiang.templateproject.activity.RegisterActivity;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.FragmentLoginBinding;
import com.xuexiang.templateproject.http.login.api.LoginApi;
import com.xuexiang.templateproject.http.login.entity.LoginResDTO;
import com.xuexiang.templateproject.utils.MMKVUtils;
import com.xuexiang.templateproject.utils.SettingUtils;
import com.xuexiang.templateproject.utils.TokenUtils;
import com.xuexiang.templateproject.utils.sdkinit.UMengInit;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xaop.logger.XLogger;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.GsonUtils;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.app.ActivityUtils;


/**
 * 登录页面
 *
 */
@Page(anim = CoreAnim.none)
public class LoginFragment extends BaseFragment<FragmentLoginBinding> implements View.OnClickListener {

    private View mJumpView;


    @NonNull
    @Override
    protected FragmentLoginBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoginBinding.inflate(inflater, container, false);
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle()
                .setImmersive(true);
        titleBar.setBackgroundColor(Color.TRANSPARENT);
        titleBar.setTitle("");
        titleBar.setLeftImageDrawable(ResUtils.getVectorDrawable(getContext(), R.drawable.ic_login_close));
//        titleBar.setActionTextColor(ThemeUtils.resolveColor(getContext(), R.attr.colorAccent));
//        mJumpView = titleBar.addAction(new TitleBar.TextAction(R.string.title_jump_login) {
//            @Override
//            public void performAction(View view) {
//                onLoginSuccess();
//            }
//        });
        return titleBar;
    }

    @Override
    protected void initViews() {
        handleSubmitPrivacy();
    }

    @Override
    protected void initListeners() {
        binding.btnLogin.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
        binding.tvForgetPassword.setOnClickListener(this);
    }

    private void refreshButton(boolean isChecked) {
        ViewUtils.setEnabled(binding.btnLogin, isChecked);
        ViewUtils.setEnabled(mJumpView, isChecked);
    }

    private void handleSubmitPrivacy() {
        SettingUtils.setIsAgreePrivacy(true);
        UMengInit.init();
        // 应用市场不让默认勾选
//        ViewUtils.setChecked(cbProtocol, true);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_login) {
            if (binding.etPhoneNumberOrUser.validate()) {
                if (binding.etPassword.validate()) {
                    loginByPhoneOrUser(binding.etPhoneNumberOrUser.getEditValue(),binding.etPassword.getEditValue());
                }
            }
        } else if (id == R.id.btn_register) {
            ActivityUtils.startActivity(RegisterActivity.class);
        }

    }


    private void loginByPhoneOrUser(String phoneOrUser, String password) {
        LoginApi.login(phoneOrUser,password,new TipCallBack<LoginResDTO>() {
            @Override
            public void onSuccess(LoginResDTO response) throws Throwable {
                XLogger.debug("Token:"+response);
//                XToastUtils.success(response);
                MMKVUtils.put(response.getToken(), GsonUtils.toJson(response.getUser()));
                onLoginSuccess(response.getToken());
            }
        });
//        CustomRequest request = XHttp.custom().cacheMode(CacheMode.NO_CACHE);
//        request.apiCall(request.create(LoginService.class).login(), new TipCallBack<String>() {
//            @Override
//            public void onSuccess(String response) throws Throwable {
//                XLogger.debug("Token:"+response);
////                XToastUtils.success(response);
//                onLoginSuccess(response);
//            }
//        });
    }

    /**
     * 登录成功的处理
     */
    private void onLoginSuccess(String res) {
        if (TokenUtils.handleLoginSuccess(res)) {
            popToBack();
            ActivityUtils.startActivity(MainActivity.class);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



}


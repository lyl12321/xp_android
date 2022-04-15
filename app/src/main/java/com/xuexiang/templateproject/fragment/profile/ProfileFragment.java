
package com.xuexiang.templateproject.fragment.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentProfileBinding;
import com.xuexiang.templateproject.fragment.other.AboutFragment;
import com.xuexiang.templateproject.fragment.other.SettingsFragment;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;
import com.xuexiang.templateproject.utils.UserUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

@Page(anim = CoreAnim.none)
public class ProfileFragment extends BaseFragment<FragmentProfileBinding> implements SuperTextView.OnSuperTextViewClickListener {

    @NonNull
    @Override
    protected FragmentProfileBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileBinding.inflate(inflater, container, false);
    }

    /**
     * @return 返回为 null意为不需要导航栏
     */
    @Override
    protected TitleBar initTitle() {
        return null;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        UserDTORes userDTO = UserUtils.getCurrentUser();
        if (userDTO.getId() != null && !userDTO.getId().equals("")) {
            binding.stUsername.setLeftString(userDTO.getUsername());
        }
    }

    @Override
    protected void initListeners() {
        binding.stUsername.setOnSuperTextViewClickListener(this);
        binding.menuMyClass.setOnSuperTextViewClickListener(this);
        binding.menuMyBuy.setOnSuperTextViewClickListener(this);
        binding.menuWallet.setOnSuperTextViewClickListener(this);
        binding.menuSettings.setOnSuperTextViewClickListener(this);
        binding.menuAbout.setOnSuperTextViewClickListener(this);
    }

    @SingleClick
    @Override
    public void onClick(SuperTextView view) {
        int id = view.getId();
        if (id == R.id.st_username) {
            openNewPage(UserInfoFragment.class);
        } else if (id == R.id.menu_settings) {
            openNewPage(SettingsFragment.class);
        } else if (id == R.id.menu_about) {
            openNewPage(AboutFragment.class);
        } else if (id == R.id.menu_my_class) {
            openNewPage(MyClassFragment.class);
        } else if (id == R.id.menu_my_buy) {

        } else if (id == R.id.menu_wallet) {
            openNewPage(WalletFragment.class);
        }
    }
}

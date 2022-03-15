
package com.xuexiang.templateproject.fragment.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentProfileBinding;
import com.xuexiang.templateproject.fragment.other.AboutFragment;
import com.xuexiang.templateproject.fragment.other.SettingsFragment;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

/**
 * @author xuexiang
 * @since 2019-10-30 00:18
 */
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

    }

    @Override
    protected void initListeners() {
        binding.menuSettings.setOnSuperTextViewClickListener(this);
        binding.menuAbout.setOnSuperTextViewClickListener(this);

    }

    @SingleClick
    @Override
    public void onClick(SuperTextView view) {
        int id = view.getId();
        if (id == R.id.menu_settings) {
            openNewPage(SettingsFragment.class);
        } else if (id == R.id.menu_about) {
            openNewPage(AboutFragment.class);
        }
    }
}

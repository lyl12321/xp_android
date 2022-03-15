package com.xuexiang.templateproject.fragment.checkin;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentCheckInBinding;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;


@Page(anim = CoreAnim.none)
public class CheckInFragment extends BaseFragment<FragmentCheckInBinding> {

    @NonNull
    @Override
    protected FragmentCheckInBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentCheckInBinding.inflate(inflater,container,false);
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
}

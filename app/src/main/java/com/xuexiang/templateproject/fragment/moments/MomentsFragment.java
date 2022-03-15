package com.xuexiang.templateproject.fragment.moments;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentMomentsBinding;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;

@Page(anim = CoreAnim.none)
public class MomentsFragment extends BaseFragment<FragmentMomentsBinding> {


    @NonNull
    @Override
    protected FragmentMomentsBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentMomentsBinding.inflate(inflater,container,false);
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

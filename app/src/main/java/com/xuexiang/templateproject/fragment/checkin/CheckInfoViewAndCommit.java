package com.xuexiang.templateproject.fragment.checkin;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentCheckInDetailsBinding;
import com.xuexiang.xpage.annotation.Page;

@Page(name = "签到详情")
public class CheckInfoViewAndCommit extends BaseFragment<FragmentCheckInDetailsBinding> {

    public static final String CHECK_ID = "check_id";

    @NonNull
    @Override
    protected FragmentCheckInDetailsBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentCheckInDetailsBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initViews() {

    }
}



package com.xuexiang.templateproject.fragment.other;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.webview.AgentWebActivity;
import com.xuexiang.templateproject.databinding.FragmentAboutBinding;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.grouplist.XUIGroupListView;
import com.xuexiang.xutil.app.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author xuexiang
 * @since 2019-10-30 00:02
 */
@Page(name = "关于")
public class AboutFragment extends BaseFragment<FragmentAboutBinding> {

    @Override
    protected void initViews() {
        binding.tvVersion.setText(String.format("版本号：%s", AppUtils.getAppVersionName()));

        XUIGroupListView.newSection(getContext())
                .addItemView(binding.aboutList.createItemView(getResources().getString(R.string.about_item_homepage)), v -> AgentWebActivity.goWeb(getContext(), getString(R.string.url_project_github)))
                .addItemView(binding.aboutList.createItemView(getResources().getString(R.string.about_item_author_github)), v -> AgentWebActivity.goWeb(getContext(), getString(R.string.url_author_github)))
                .addItemView(binding.aboutList.createItemView(getResources().getString(R.string.about_item_donation_link)), v -> AgentWebActivity.goWeb(getContext(), getString(R.string.url_donation_link)))
                .addItemView(binding.aboutList.createItemView(getResources().getString(R.string.about_item_add_qq_group)), v -> AgentWebActivity.goWeb(getContext(), getString(R.string.url_add_qq_group)))
                .addItemView(binding.aboutList.createItemView(getResources().getString(R.string.title_user_protocol)), v -> Utils.gotoProtocol(this, false, false))
                .addItemView(binding.aboutList.createItemView(getResources().getString(R.string.title_privacy_protocol)), v -> Utils.gotoProtocol(this, true, false))
                .addTo(binding.aboutList);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.CHINA);
        String currentYear = dateFormat.format(new Date());
        binding.tvCopyright.setText(String.format(getResources().getString(R.string.about_copyright), currentYear));
    }

    @NonNull
    @Override
    protected FragmentAboutBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentAboutBinding.inflate(inflater, container, false);
    }
}

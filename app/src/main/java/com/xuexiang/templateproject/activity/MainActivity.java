
package com.xuexiang.templateproject.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseActivity;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.ActivityMainBinding;
import com.xuexiang.templateproject.fragment.checkin.CheckInFragment;
import com.xuexiang.templateproject.fragment.home.HomeFragment;
import com.xuexiang.templateproject.fragment.moments.MomentsFragment;
import com.xuexiang.templateproject.fragment.other.AboutFragment;
import com.xuexiang.templateproject.fragment.profile.ProfileFragment;
import com.xuexiang.templateproject.http.user.api.UserService;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;
import com.xuexiang.templateproject.utils.MMKVUtils;
import com.xuexiang.templateproject.utils.TokenUtils;
import com.xuexiang.templateproject.utils.UserUtils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.templateproject.utils.sdkinit.XUpdateInit;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.cache.model.CacheMode;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.request.CustomRequest;
import com.xuexiang.xpage.utils.GsonUtils;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.ClickUtils;
import com.xuexiang.xutil.common.CollectionUtils;


public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener, ClickUtils.OnClick2ExitListener, Toolbar.OnMenuItemClickListener {

    private String[] mTitles;

    @Override
    protected ActivityMainBinding viewBindingInflate(LayoutInflater inflater) {
        return ActivityMainBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();

        initData();

        initListeners();
    }


    @Override
    protected boolean isSupportSlideBack() {
        return false;
    }

    private void initViews() {
        WidgetUtils.clearActivityBackground(this);

        mTitles = ResUtils.getStringArray(R.array.home_titles);
        binding.includeMain.toolbar.setTitle(mTitles[0]);
        binding.includeMain.toolbar.inflateMenu(R.menu.menu_main);
        binding.includeMain.toolbar.setOnMenuItemClickListener(this);


        UserDTORes userDTO = UserUtils.getCurrentUser();
        BaseFragment[] fragments;
        if (userDTO.getUserType() != null && (userDTO.getUserType().equals("0") || userDTO.getUserType().equals("1"))){
            //动态加载nav
            binding.includeMain.bottomNavigation.inflateMenu(R.menu.menu_navigation_bottom);
            //主页内容填充
            fragments = new BaseFragment[]{
                    new HomeFragment(),
                    new CheckInFragment(),
                    new MomentsFragment(),
                    new ProfileFragment()
            };

        } else {
            //动态加载nav
            binding.includeMain.bottomNavigation.inflateMenu(R.menu.menu_navigation_bottom_teacher);
            //主页内容填充
            fragments = new BaseFragment[]{
                    new HomeFragment(),
                    new MomentsFragment(),
                    new ProfileFragment()
            };
        }


//        initHeader();


        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getSupportFragmentManager(), fragments);
        binding.includeMain.viewPager.setOffscreenPageLimit(mTitles.length - 1);
        binding.includeMain.viewPager.setAdapter(adapter);
    }

    private void initData() {
//        GuideTipsDialog.showTips(this);
        XUpdateInit.checkUpdate(this, false);

        CustomRequest request = XHttp.custom().cacheMode(CacheMode.NO_CACHE);
        request.apiCall(request.create(UserService.class).getUserInfo(), new TipCallBack<UserDTORes>() {
            @Override
            public void onSuccess(UserDTORes response) throws Throwable {
                if (response.getId() == null) {
                    XToastUtils.error("未获取到用户信息，登陆失败，请重新登陆");
                    TokenUtils.forceLogoutSuccess();
                } else {
                    MMKVUtils.put(TokenUtils.getToken(), GsonUtils.toJson(response));

                }
            }

            @Override
            public void onError(ApiException e) {
                if (e.getDisplayMessage().equals("sessionExpired")) {
                    XToastUtils.error("登陆过期，请重新登陆");
                    TokenUtils.forceLogoutSuccess();
                }
            }
        });
    }

//    private void initHeader() {
//        binding.navView.setItemIconTintList(null);
//        View headerView = binding.navView.getHeaderView(0);
//        LinearLayout navHeader = headerView.findViewById(R.id.nav_header);
//        RadiusImageView ivAvatar = headerView.findViewById(R.id.iv_avatar);
//        TextView tvAvatar = headerView.findViewById(R.id.tv_avatar);
//        TextView tvSign = headerView.findViewById(R.id.tv_sign);
//
//        if (Utils.isColorDark(ThemeUtils.resolveColor(this, R.attr.colorAccent))) {
//            tvAvatar.setTextColor(Colors.WHITE);
//            tvSign.setTextColor(Colors.WHITE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                ivAvatar.setImageTintList(ResUtils.getColors(R.color.xui_config_color_white));
//            }
//        } else {
//            tvAvatar.setTextColor(ThemeUtils.resolveColor(this, R.attr.xui_config_color_title_text));
//            tvSign.setTextColor(ThemeUtils.resolveColor(this, R.attr.xui_config_color_explain_text));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                ivAvatar.setImageTintList(ResUtils.getColors(R.color.xui_config_color_gray_3));
//            }
//        }
//
//        ivAvatar.setImageResource(R.drawable.ic_default_head);
//        tvAvatar.setText(R.string.app_name);
//        tvSign.setText("这个家伙很懒，什么也没有留下～～");
//        navHeader.setOnClickListener(this);
//    }

    protected void initListeners() {
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.includeMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        binding.drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

        //侧边栏点击事件
//        binding.navView.setNavigationItemSelectedListener(menuItem -> {
//            if (menuItem.isCheckable()) {
//                binding.drawerLayout.closeDrawers();
//                return handleNavigationItemSelected(menuItem);
//            } else {
//                int id = menuItem.getItemId();
//                if (id == R.id.nav_settings) {
//                    openNewPage(SettingsFragment.class);
//                } else if (id == R.id.nav_about) {
//                    openNewPage(AboutFragment.class);
//                } else {
//                    XToastUtils.toast("点击了:" + menuItem.getTitle());
//                }
//            }
//            return true;
//        });
        //主页事件监听
        binding.includeMain.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MenuItem item = binding.includeMain.bottomNavigation.getMenu().getItem(position);
                binding.includeMain.toolbar.setTitle(item.getTitle());
                item.setChecked(true);
//                updateSideNavStatus(item);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.includeMain.bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    /**
     * 处理侧边栏点击事件
     *
     * @param menuItem
     * @return
     */
    private boolean handleNavigationItemSelected(@NonNull MenuItem menuItem) {
        int index = CollectionUtils.arrayIndexOf(mTitles, menuItem.getTitle());
        if (index != -1) {
            binding.includeMain.toolbar.setTitle(menuItem.getTitle());
            binding.includeMain.viewPager.setCurrentItem(index, false);
            return true;
        }
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            openNewPage(AboutFragment.class);
        }
        return false;
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.nav_header) {
            XToastUtils.toast("点击头部！");
        }
    }

    //================Navigation================//

    /**
     * 底部导航栏点击事件
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int index = CollectionUtils.arrayIndexOf(mTitles, menuItem.getTitle());
        if (index != -1) {
            binding.includeMain.toolbar.setTitle(menuItem.getTitle());
            binding.includeMain.viewPager.setCurrentItem(index, false);

//            updateSideNavStatus(menuItem);
            return true;
        }
        return false;
    }

    /**
     * 更新侧边栏菜单选中状态
     *
     * @param menuItem
     */
//    private void updateSideNavStatus(MenuItem menuItem) {
//        MenuItem side = binding.navView.getMenu().findItem(menuItem.getItemId());
//        if (side != null) {
//            side.setChecked(true);
//        }
//    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ClickUtils.exitBy2Click(2000, this);
        }
        return true;
    }

    @Override
    public void onRetry() {
        XToastUtils.toast("再按一次退出程序");
    }

    @Override
    public void onExit() {
        XUtil.exitApp();
    }


}

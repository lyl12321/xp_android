
package com.xuexiang.templateproject.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.reflect.TypeToken;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseActivity;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.http.callback.TipCallBack;
import com.xuexiang.templateproject.databinding.ActivityMainBinding;
import com.xuexiang.templateproject.fragment.chat.ChatFragment;
import com.xuexiang.templateproject.fragment.checkin.CheckInFragment;
import com.xuexiang.templateproject.fragment.home.HomeFragment;
import com.xuexiang.templateproject.fragment.moments.MomentsFragment;
import com.xuexiang.templateproject.fragment.other.AboutFragment;
import com.xuexiang.templateproject.fragment.profile.ProfileFragment;
import com.xuexiang.templateproject.http.chat.api.ChatApi;
import com.xuexiang.templateproject.http.chat.entity.ChatRoomListDTO;
import com.xuexiang.templateproject.http.user.api.UserApi;
import com.xuexiang.templateproject.http.user.entity.UserDTORes;
import com.xuexiang.templateproject.http.websocket.WebSocketResponseDTO;
import com.xuexiang.templateproject.utils.MMKVUtils;
import com.xuexiang.templateproject.utils.TokenUtils;
import com.xuexiang.templateproject.utils.UserUtils;
import com.xuexiang.templateproject.utils.XToastUtils;
import com.xuexiang.templateproject.utils.sdkinit.XUpdateInit;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xpage.utils.GsonUtils;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.ClickUtils;
import com.xuexiang.xutil.common.CollectionUtils;
import com.xuexiang.xutil.net.JsonUtil;
import com.zhangke.websocket.SimpleListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;


public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener, ClickUtils.OnClick2ExitListener, Toolbar.OnMenuItemClickListener {

    private String[] mTitles;

    private View badge;

    private int noReadMessageCount = 0;

    private boolean isFront = false;

    private SimpleListener listener;

    @Override
    protected ActivityMainBinding viewBindingInflate(LayoutInflater inflater) {
        return ActivityMainBinding.inflate(inflater);
    }

    private WebSocketManager manager = WebSocketHandler.getDefault();

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


        //???????????????NavigationView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) binding.includeMain.bottomNavigation.getChildAt(0);

        UserDTORes userDTO = UserUtils.getCurrentUser();
        BaseFragment[] fragments;
        if (userDTO.getUserType() != null && userDTO.getUserType().equals("2")) {
            mTitles = ResUtils.getStringArray(R.array.home_titles_student);
            //????????????nav
            binding.includeMain.bottomNavigation.inflateMenu(R.menu.menu_navigation_bottom);
            //??????????????????
            fragments = new BaseFragment[]{
                    new HomeFragment(),
                    new CheckInFragment(),
                    new ChatFragment(),
                    new MomentsFragment(),
                    new ProfileFragment()
            };


            View tab = menuView.getChildAt(2);
            badge = LayoutInflater.from(this).inflate(R.layout.menu_badge, menuView, false);
            BottomNavigationItemView itemView = (BottomNavigationItemView) tab;
            itemView.addView(badge);
        } else {
            mTitles = ResUtils.getStringArray(R.array.home_titles);
            //????????????nav
            binding.includeMain.bottomNavigation.inflateMenu(R.menu.menu_navigation_bottom_teacher);
            //??????????????????
            fragments = new BaseFragment[]{
                    new HomeFragment(),
                    new ChatFragment(),
                    new MomentsFragment(),
                    new ProfileFragment()
            };
            View tab = menuView.getChildAt(1);
            badge = LayoutInflater.from(this).inflate(R.layout.menu_badge, menuView, false);
            BottomNavigationItemView itemView = (BottomNavigationItemView) tab;
            itemView.addView(badge);
        }


        binding.includeMain.toolbar.setTitle(mTitles[0]);
        binding.includeMain.toolbar.inflateMenu(R.menu.menu_main);
        binding.includeMain.toolbar.setOnMenuItemClickListener(this);


//        initHeader();


        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getSupportFragmentManager(), fragments);
        binding.includeMain.viewPager.setOffscreenPageLimit(mTitles.length - 1);
        binding.includeMain.viewPager.setAdapter(adapter);


        ChatApi.getAllNoReadMessageCount(new TipCallBack<Integer>() {
            @Override
            public void onSuccess(Integer response) throws Throwable {
                TextView count = (TextView) badge.findViewById(R.id.tv_msg_count);
                if (response != null) {
                    noReadMessageCount = response;
                }
                if (response != null && response > 0) {
                    count.setText(String.valueOf(noReadMessageCount));
                    count.setVisibility(View.VISIBLE);
                } else {
                    count.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initData() {
//        GuideTipsDialog.showTips(this);
        XUpdateInit.checkUpdate(this, false);

        UserApi.getUserInfo(new TipCallBack<UserDTORes>() {
            @Override
            public void onSuccess(UserDTORes response) throws Throwable {
                if (response.getId() == null) {
                    XToastUtils.error("?????????????????????????????????????????????????????????");
                    TokenUtils.forceLogoutSuccess();
                } else {
                    MMKVUtils.put(TokenUtils.getToken(), GsonUtils.toJson(response));

                }
            }

            @Override
            public void onError(ApiException e) {
                if (e.getDisplayMessage().equals("sessionExpired")) {
                    XToastUtils.error("??????????????????????????????");
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
//        tvSign.setText("????????????????????????????????????????????????");
//        navHeader.setOnClickListener(this);
//    }

    protected void initListeners() {
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.includeMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        binding.drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

        //?????????????????????
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
//                    XToastUtils.toast("?????????:" + menuItem.getTitle());
//                }
//            }
//            return true;
//        });
        //??????????????????
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
        listener = new SimpleListener() {
            @Override
            public <T> void onMessage(String message, T data) {
                WebSocketResponseDTO<ChatRoomListDTO.ChatRoomItem.ChatRecordsDTO> response = JsonUtil.fromJson(message, new TypeToken<WebSocketResponseDTO<ChatRoomListDTO.ChatRoomItem.ChatRecordsDTO>>() {
                }.getType());
                if (response.getType() == 5) {
                    if (response.getData().getReadStatus().equals("0") && response.getData().getReceiveId().equals(UserUtils.getCurrentUser().getId())) {
                        noReadMessageCount++;
                        if (isFront) {
                            TextView count = (TextView) badge.findViewById(R.id.tv_msg_count);
                            count.setText(String.valueOf(noReadMessageCount));
                            count.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        };
        manager.addListener(listener);
    }

    /**
     * ???????????????????????????
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
            XToastUtils.toast("???????????????");
        }
    }

    //================Navigation================//

    /**
     * ???????????????????????????
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int index = CollectionUtils.arrayIndexOf(mTitles, menuItem.getTitle());
        if (index != -1) {
            binding.includeMain.toolbar.setTitle(menuItem.getTitle());
            binding.includeMain.viewPager.setCurrentItem(index, true);

//            updateSideNavStatus(menuItem);
            return true;
        }
        return false;
    }

    /**
     * ?????????????????????????????????
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
     * ????????????????????????
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
        XToastUtils.toast("????????????????????????");
    }

    @Override
    public void onExit() {
        XUtil.exitApp();
    }


//    public void reduceNoRead(Integer countNum) {
//        if (this.noReadMessageCount != 0) {
//            this.noReadMessageCount = this.noReadMessageCount - countNum;
//            if (this.isFront) {
//                TextView count = (TextView) badge.findViewById(R.id.tv_msg_count);
//                if (this.noReadMessageCount == 0) {
//                    count.setVisibility(View.GONE);
//                } else {
//
//                    count.setText(String.valueOf(noReadMessageCount));
//                }
//
//
//            }
//        }
//
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        isFront = true;
        ChatApi.getAllNoReadMessageCount(new TipCallBack<Integer>() {
            @Override
            public void onSuccess(Integer response) throws Throwable {
                TextView count = (TextView) badge.findViewById(R.id.tv_msg_count);
                if (response != null) {
                    noReadMessageCount = response;
                }
                if (response != null && response > 0) {
                    count.setText(String.valueOf(noReadMessageCount));
                    count.setVisibility(View.VISIBLE);
                } else {
                    count.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        isFront = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null) {
            manager.removeListener(listener);
        }
    }
}

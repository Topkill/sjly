package com.zpj.shouji.market.ui.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lxj.xpopup.core.BasePopupView;
import com.zpj.fragmentation.BaseFragment;
import com.zpj.matisse.CaptureMode;
import com.zpj.matisse.Matisse;
import com.zpj.matisse.MimeType;
import com.zpj.matisse.engine.impl.GlideEngine;
import com.zpj.matisse.entity.Item;
import com.zpj.matisse.listener.OnSelectedListener;
import com.zpj.shouji.market.R;
import com.zpj.shouji.market.ui.adapter.FragmentsPagerAdapter;
import com.zpj.shouji.market.ui.fragment.chat.ChatFragment;
import com.zpj.shouji.market.ui.fragment.homepage.HomeFragment;
import com.zpj.shouji.market.ui.fragment.profile.MeFragment;
import com.zpj.shouji.market.ui.fragment.recommond.GameRecommendFragment;
import com.zpj.shouji.market.ui.fragment.recommond.SoftRecommendFragment;
import com.zpj.shouji.market.ui.widget.navigation.BottomNavigationViewEx;
import com.zpj.shouji.market.ui.widget.popup.MorePopup;
import com.zpj.widget.ZViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentTest1 extends BaseFragment
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        MorePopup.OnItemClickListener {

    private final List<BaseFragment> fragments = new ArrayList<>();
    private ZViewPager viewPager;
    private BasePopupView popupView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected boolean supportSwipeBack() {
        return false;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        HomeFragment homeFragment = findChildFragment(HomeFragment.class);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }

        SoftRecommendFragment softFragment = findChildFragment(SoftRecommendFragment.class);
        if (softFragment == null) {
            softFragment = new SoftRecommendFragment();
        }

        GameRecommendFragment game = findChildFragment(GameRecommendFragment.class);
        if (game == null) {
            game = new GameRecommendFragment();
        }

        MeFragment profileFragment = findChildFragment(MeFragment.class);
        if (profileFragment == null) {
            profileFragment = new MeFragment();
        }
        fragments.clear();
        fragments.add(homeFragment);
        fragments.add(softFragment);
        fragments.add(game);
        fragments.add(profileFragment);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);

        BottomNavigationViewEx navigationView = view.findViewById(R.id.navigation_view);
        navigationView.enableItemShiftingMode(false);
        navigationView.enableShiftingMode(false);
        navigationView.enableAnimation(false);
        viewPager = view.findViewById(R.id.vp);
        viewPager.setCanScroll(false);
        viewPager.setOffscreenPageLimit(fragments.size());
        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(getChildFragmentManager(), fragments, null);
        viewPager.setAdapter(adapter);
        navigationView.setOnNavigationItemSelectedListener(this);

        floatingActionButton.setOnClickListener(v -> {
            postDelay(this::darkStatusBar, 300);
            popupView = MorePopup.with((ViewGroup) view)
                    .setListener(this)
                    .show();
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        if (popupView != null) {
            popupView.dismiss();
            popupView = null;
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onSupportVisible() {
        if (viewPager != null && !fragments.isEmpty()) {
            fragments.get(viewPager.getCurrentItem()).onSupportVisible();
        } else {
            darkStatusBar();
        }
    }

    @Override
    public void onSupportInvisible() {
        if (viewPager != null && !fragments.isEmpty()) {
            fragments.get(viewPager.getCurrentItem()).onSupportInvisible();
        } else {
            darkStatusBar();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int position = 0;
        switch (menuItem.getItemId()) {
            case R.id.i_homepage:
                position = 0;
                break;
            case R.id.i_app:
                position = 1;
                break;
            case R.id.i_game:
                position = 2;
                break;
            case R.id.i_me:
                position = 3;
                break;
            case R.id.i_empty: {
                return false;
            }
        }
        if(viewPager.getCurrentItem() != position) {
            viewPager.setCurrentItem(position, false);
        }

        return true;
    }

    @Override
    public void onDiscoverItemClick() {
        _mActivity.start(new DiscoverEditorFragment2());
    }

    @Override
    public void onCollectionItemClick() {

    }

    @Override
    public void onWallpaperItemClick() {
        Matisse.from(_mActivity)
                .choose(MimeType.ofImage())//照片视频全部显示MimeType.allOf()
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(3)//最大选择数量为9
                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(this.getResources().getDimensionPixelSize(R.dimen.photo))//图片显示表格的大小
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//图像选择和预览活动所需的方向
                .thumbnailScale(0.85f)//缩放比例
                .imageEngine(new GlideEngine())//图片加载方式，Glide4需要自定义实现
                .capture(true) //是否提供拍照功能，兼容7.0系统需要下面的配置
                //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .capture(true, CaptureMode.All)//存储到哪里
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(@NonNull List<Item> itemList) {

                    }
                })
                .start();
    }

    @Override
    public void onChatWithFriendItemClick() {
        _mActivity.start(new ChatFragment());
    }
}
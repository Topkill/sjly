package com.zpj.shouji.market.ui.fragment.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zpj.fragmentation.BaseFragment;
import com.zpj.shouji.market.R;
import com.zpj.shouji.market.manager.UserManager;
import com.zpj.shouji.market.ui.adapter.FragmentsPagerAdapter;
import com.zpj.shouji.market.ui.fragment.theme.ThemeListFragment;
import com.zpj.utils.ScreenUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class MyBookingFragment extends BaseFragment {

    private static final String[] TAB_TITLES = {"预约中", "已上线"};

    protected ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_discover;
    }

    @Override
    protected boolean supportSwipeBack() {
        return true;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        setToolbarTitle("我的预约");
        viewPager = view.findViewById(R.id.view_pager);
        List<Fragment> fragments = new ArrayList<>();
        MyRelatedCommentFragment myRelatedCommentFragment = findChildFragment(MyRelatedCommentFragment.class);
        if (myRelatedCommentFragment == null) {
            myRelatedCommentFragment = MyRelatedCommentFragment.newInstance();
        }
        MyPublishCommentFragment myPublishCommentFragment = findChildFragment(MyPublishCommentFragment.class);
        if (myPublishCommentFragment == null) {
            myPublishCommentFragment = MyPublishCommentFragment.newInstance();
        }
        fragments.add(myRelatedCommentFragment);
        fragments.add(myPublishCommentFragment);
        viewPager.setAdapter(new FragmentsPagerAdapter(getChildFragmentManager(), fragments, TAB_TITLES));
        viewPager.setOffscreenPageLimit(2);

        MagicIndicator magicIndicator = view.findViewById(R.id.magic_indicator);
        CommonNavigator navigator = new CommonNavigator(getContext());
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return TAB_TITLES.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                ColorTransitionPagerTitleView titleView = new ColorTransitionPagerTitleView(context);
                titleView.setNormalColor(getResources().getColor(R.color.color_text_major));
                titleView.setSelectedColor(getResources().getColor(R.color.colorPrimary));
                titleView.setTextSize(14);
                titleView.setText(TAB_TITLES[index]);
                titleView.setOnClickListener(view1 -> viewPager.setCurrentItem(index, true));
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(ScreenUtils.dp2px(context, 4f));
                indicator.setLineWidth(ScreenUtils.dp2px(context, 12f));
                indicator.setRoundRadius(ScreenUtils.dp2px(context, 4f));
                indicator.setColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary));
                return indicator;
            }
        });
        magicIndicator.setNavigator(navigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);

    }

    public static class MyRelatedCommentFragment extends ThemeListFragment {

        public static MyRelatedCommentFragment newInstance() {
            String url = "http://tt.shouji.com.cn/app/user_content_list_xml_v2.jsp?versioncode=198&t=review&jsessionid="
                    + UserManager.getInstance().getSessionId() + "&thread=thread&sn="
                    + UserManager.getInstance().getMemberInfo().getSn();
            Bundle args = new Bundle();
            args.putString(KEY_DEFAULT_URL, url);
            MyRelatedCommentFragment fragment = new MyRelatedCommentFragment();
            fragment.setArguments(args);
            return fragment;
        }

    }

    public static class MyPublishCommentFragment extends ThemeListFragment {

        public static MyPublishCommentFragment newInstance() {
            String url = "http://tt.shouji.com.cn/app/user_content_list_xml_v2.jsp?versioncode=198&t=review&jsessionid="
                    + UserManager.getInstance().getSessionId() + "&sn="
                    + UserManager.getInstance().getMemberInfo().getSn();
            Bundle args = new Bundle();
            args.putString(KEY_DEFAULT_URL, url);
            MyPublishCommentFragment fragment = new MyPublishCommentFragment();
            fragment.setArguments(args);
            return fragment;
        }

    }

}

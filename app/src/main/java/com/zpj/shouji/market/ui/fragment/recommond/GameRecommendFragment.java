//package com.zpj.shouji.market.ui.fragment.recommond;
//
//import android.graphics.Color;
//import android.support.v7.widget.RecyclerView;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import com.zpj.recyclerview.EasyRecyclerLayout;
//import com.zpj.recyclerview.EasyRecyclerView;
//import com.zpj.recyclerview.EasyViewHolder;
//import com.zpj.recyclerview.IEasy;
//import com.zpj.shouji.market.R;
//import com.zpj.shouji.market.model.GroupItem;
//import com.zpj.utils.ScreenUtils;
//
//import java.util.List;
//
//import www.linwg.org.lib.LCardView;
//
//public class GameRecommendFragment extends BaseRecommendFragment {
//
//    private static final String TAG = "GameRecommendFragment";
//
//    @Override
//    protected void buildRecyclerLayout(EasyRecyclerLayout<GroupItem> recyclerLayout) {
//        recyclerLayout.getEasyRecyclerView().getRecyclerView().setBackgroundColor(getResources().getColor(R.color.background_color));
//        recyclerLayout.setHeaderView(R.layout.layout_app_header, new IEasy.OnBindHeaderListener() {
//            @Override
//            public void onBindHeader(EasyViewHolder holder) {
//
//            }
//
//        })
//                .onGetChildViewType(position -> position + 1);
//    }
//
//    @Override
//    public void onRefresh() {
//        data.clear();
//        data.add(new GroupItem("最近更新"));
//        data.add(new GroupItem("游戏推荐"));
//        data.add(new GroupItem("热门网游"));
//        // TODO 排行
//        data.add(new GroupItem("游戏快递"));
//        data.add(new GroupItem("游戏评测"));
//        data.add(new GroupItem("游戏攻略"));
//        data.add(new GroupItem("游戏新闻"));
//        data.add(new GroupItem("游戏周刊"));
//        data.add(new GroupItem("游戏公告"));
//        recyclerLayout.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onBindViewHolder(EasyViewHolder holder, List<GroupItem> list, int position, List<Object> payloads) {
//        holder.getTextView(R.id.tv_title).setText(list.get(position).getTitle());
//        if (holder.getItemView().getTag() instanceof EasyRecyclerView) {
//            ((EasyRecyclerView) holder.getItemView().getTag()).notifyDataSetChanged();
//            return;
//        }
//
//        RelativeLayout rlHeader = holder.getView(R.id.rl_header);
//        RecyclerView view = holder.getView(R.id.recycler_view);
//        LCardView cardView = holder.getView(R.id.card_view);
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) cardView.getLayoutParams();
//        LinearLayout.LayoutParams rlParams = (LinearLayout.LayoutParams) rlHeader.getLayoutParams();
//        rlParams.setMarginStart(0);
//        rlParams.setMarginEnd(0);
//        int margin = ScreenUtils.dp2pxInt(context, 12);
//        int padding = ScreenUtils.dp2pxInt(context, 8);
//        view.setPadding(padding, padding, padding, padding);
//        switch (holder.getViewType()) {
//            case 1:
//                params.setMargins(margin, margin, margin, margin / 2);
//                cardView.setCardBackgroundColor(Color.WHITE);
//                getAppInfo(holder, "http://tt.shouji.com.cn/androidv3/game_index_xml.jsp?sort=time&versioncode=198");
//                break;
//            case 2:
//                params.setMargins(margin, margin, margin, margin / 2);
//                cardView.setCardBackgroundColor(Color.WHITE);
//                getAppInfo(holder, "http://tt.shouji.com.cn/androidv3/game_index_xml.jsp?sdk=100&sort=day");
//                break;
//            case 3:
//                params.setMargins(margin, margin / 2, margin, margin / 2);
//                cardView.setCardBackgroundColor(Color.WHITE);
//                getAppInfo(holder, "http://tt.shouji.com.cn/androidv3/netgame.jsp");
//                break;
//            case 4:
//            case 5:
//            case 6:
//            case 7:
//            case 8:
//            case 9:
//                params.setMargins(0, margin / 2, 0, 0);
//                rlParams.setMarginStart(margin);
//                rlParams.setMarginEnd(margin);
//                cardView.setCardBackgroundColor(Color.TRANSPARENT);
//                view.setPadding(0, 0, 0, 0);
//                getTutorial(holder, "game", holder.getViewType() - 3);
//                break;
//        }
//        rlHeader.setLayoutParams(rlParams);
//        cardView.setLayoutParams(params);
//    }
//
//}

package com.zpj.shouji.market.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.felix.atoast.library.AToast;
import com.zpj.shouji.market.R;
import com.zpj.shouji.market.glide.CustomShapeTransformation;
import com.zpj.shouji.market.model.ChatMessageBean;
import com.zpj.shouji.market.ui.fragment.chat.ChatConst;
import com.zpj.shouji.market.ui.widget.BubbleImageView;
import com.zpj.shouji.market.ui.widget.GifTextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mao Jiqing on 2016/9/29.
 */
public class ChatRecyclerAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ChatMessageBean> userList = new ArrayList<ChatMessageBean>();
    private ArrayList<String> imageList = new ArrayList<String>();
    private HashMap<Integer, Integer> imagePosition = new HashMap<Integer, Integer>();
    public static final int FROM_USER_MSG = 0;//接收消息类型
    public static final int TO_USER_MSG = 1;//发送消息类型
    public static final int FROM_USER_IMG = 2;//接收消息类型
    public static final int TO_USER_IMG = 3;//发送消息类型
    private int mMinItemWith;// 设置对话框的最大宽度和最小宽度
    private int mMaxItemWith;
    public Handler handler;
    private Animation an;
    private SendErrorListener sendErrorListener;
    public List<String> unReadPosition = new ArrayList<String>();
    private LayoutInflater mLayoutInflater;
    private boolean isGif = true;
    public boolean isPicRefresh = true;

    public interface SendErrorListener {
        public void onClick(int position);
    }

    public void setSendErrorListener(SendErrorListener sendErrorListener) {
        this.sendErrorListener = sendErrorListener;
    }

    public ChatRecyclerAdapter(Context context, List<ChatMessageBean> userList) {
        this.context = context;
        this.userList = userList;
        mLayoutInflater = LayoutInflater.from(context);
        // 获取系统宽度
        WindowManager wManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wManager.getDefaultDisplay().getMetrics(outMetrics);
        mMaxItemWith = (int) (outMetrics.widthPixels * 0.5f);
        mMinItemWith = (int) (outMetrics.widthPixels * 0.15f);
        handler = new Handler();
    }

    public void setIsGif(boolean isGif) {
        this.isGif = isGif;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public void setImagePosition(HashMap<Integer, Integer> imagePosition) {
        this.imagePosition = imagePosition;
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case FROM_USER_MSG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_msgfrom_list_item, parent, false);
                holder = new FromUserMsgViewHolder(view);
                break;
            case FROM_USER_IMG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_imagefrom_list_item, parent, false);
                holder = new FromUserImageViewHolder(view);
                break;
            case TO_USER_MSG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_msgto_list_item, parent, false);
                holder = new ToUserMsgViewHolder(view);
                break;
            case TO_USER_IMG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_imageto_list_item, parent, false);
                holder = new ToUserImgViewHolder(view);
                break;
        }
        return holder;
    }

    class FromUserMsgViewHolder extends RecyclerView.ViewHolder {
        private ImageView headicon;
        private TextView chat_time;
        private GifTextView content;

        public FromUserMsgViewHolder(View view) {
            super(view);
            headicon = (ImageView) view
                    .findViewById(R.id.tb_other_user_icon);
            chat_time = (TextView) view.findViewById(R.id.chat_time);
            content = (GifTextView) view.findViewById(R.id.content);
        }
    }

    class FromUserImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView headicon;
        private TextView chat_time;
        private BubbleImageView image_Msg;

        public FromUserImageViewHolder(View view) {
            super(view);
            headicon = (ImageView) view
                    .findViewById(R.id.tb_other_user_icon);
            chat_time = (TextView) view.findViewById(R.id.chat_time);
            image_Msg = (BubbleImageView) view
                    .findViewById(R.id.image_message);
        }
    }

    class ToUserMsgViewHolder extends RecyclerView.ViewHolder {
        private ImageView headicon;
        private TextView chat_time;
        private GifTextView content;
        private ImageView sendFailImg;

        public ToUserMsgViewHolder(View view) {
            super(view);
            headicon = (ImageView) view
                    .findViewById(R.id.tb_my_user_icon);
            chat_time = (TextView) view
                    .findViewById(R.id.mychat_time);
            content = (GifTextView) view
                    .findViewById(R.id.mycontent);
            sendFailImg = (ImageView) view
                    .findViewById(R.id.mysend_fail_img);
        }
    }

    class ToUserImgViewHolder extends RecyclerView.ViewHolder {
        private ImageView headicon;
        private TextView chat_time;
        private LinearLayout image_group;
        private BubbleImageView image_Msg;
        private ImageView sendFailImg;

        public ToUserImgViewHolder(View view) {
            super(view);
            headicon = (ImageView) view
                    .findViewById(R.id.tb_my_user_icon);
            chat_time = (TextView) view
                    .findViewById(R.id.mychat_time);
            sendFailImg = (ImageView) view
                    .findViewById(R.id.mysend_fail_img);
            image_group = (LinearLayout) view
                    .findViewById(R.id.image_group);
            image_Msg = (BubbleImageView) view
                    .findViewById(R.id.image_message);
        }
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessageBean tbub = userList.get(position);
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case FROM_USER_MSG:
                fromMsgUserLayout((FromUserMsgViewHolder) holder, tbub, position);
                break;
            case FROM_USER_IMG:
                fromImgUserLayout((FromUserImageViewHolder) holder, tbub, position);
                break;
            case TO_USER_MSG:
                toMsgUserLayout((ToUserMsgViewHolder) holder, tbub, position);
                break;
            case TO_USER_IMG:
                toImgUserLayout((ToUserImgViewHolder) holder, tbub, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return userList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void fromMsgUserLayout(final FromUserMsgViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.drawable.tongbao_hiv);
        /* time */
        if (position != 0) {
            String showTime = getTime(tbub.getTime(), userList.get(position - 1)
                    .getTime());
            if (showTime != null) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(showTime);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }
        } else {
            String showTime = getTime(tbub.getTime(), null);
            holder.chat_time.setVisibility(View.VISIBLE);
            holder.chat_time.setText(showTime);
        }
        holder.content.setVisibility(View.VISIBLE);
        holder.content.setSpanText(handler, tbub.getUserContent(), isGif);
    }

    private void fromImgUserLayout(final FromUserImageViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.drawable.tongbao_hiv);
        /* time */
        if (position != 0) {
            String showTime = getTime(tbub.getTime(), userList.get(position - 1)
                    .getTime());
            if (showTime != null) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(showTime);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }
        } else {
            String showTime = getTime(tbub.getTime(), null);
            holder.chat_time.setVisibility(View.VISIBLE);
            holder.chat_time.setText(showTime);
        }
//        if (isPicRefresh) {
        final String imageSrc = tbub.getImageLocal() == null ? "" : tbub
                .getImageLocal();
        final String imageUrlSrc = tbub.getImageUrl() == null ? "" : tbub
                .getImageUrl();
        final String imageIconUrl = tbub.getImageIconUrl() == null ? ""
                : tbub.getImageIconUrl();
        File file = new File(imageSrc);
        final boolean hasLocal = !imageSrc.equals("")
                && file.exists();
        int res;
        res = R.drawable.chatfrom_bg_focused;
//        Glide.with(context).load(imageSrc).transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
        Glide.with(context).load(imageSrc).placeholder(R.drawable.cygs_cs).transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);

        holder.image_Msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "image_Msg", Toast.LENGTH_SHORT).show();
            }

        });
//        }

    }

    private void toMsgUserLayout(final ToUserMsgViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.mipmap.ic_launcher);
        holder.headicon.setImageDrawable(context.getResources()
                .getDrawable(R.mipmap.ic_launcher));
        /* time */
        if (position != 0) {
            String showTime = getTime(tbub.getTime(), userList.get(position - 1)
                    .getTime());
            if (showTime != null) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(showTime);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }
        } else {
            String showTime = getTime(tbub.getTime(), null);
            holder.chat_time.setVisibility(View.VISIBLE);
            holder.chat_time.setText(showTime);
        }

        holder.content.setVisibility(View.VISIBLE);
        holder.content.setSpanText(handler, tbub.getUserContent(), isGif);
    }

    private void toImgUserLayout(final ToUserImgViewHolder holder, final ChatMessageBean tbub, final int position) {
        holder.headicon.setBackgroundResource(R.mipmap.ic_launcher);
        switch (tbub.getSendState()) {
            case ChatConst.SENDING:
                an = AnimationUtils.loadAnimation(context,
                        R.anim.update_loading_progressbar_anim);
                LinearInterpolator lin = new LinearInterpolator();
                an.setInterpolator(lin);
                an.setRepeatCount(-1);
                holder.sendFailImg
                        .setBackgroundResource(R.drawable.xsearch_loading);
                holder.sendFailImg.startAnimation(an);
                an.startNow();
                holder.sendFailImg.setVisibility(View.VISIBLE);
                break;

            case ChatConst.COMPLETED:
                holder.sendFailImg.clearAnimation();
                holder.sendFailImg.setVisibility(View.GONE);
                break;

            case ChatConst.SENDERROR:
                holder.sendFailImg.clearAnimation();
                holder.sendFailImg
                        .setBackgroundResource(R.drawable.msg_state_fail_resend_pressed);
                holder.sendFailImg.setVisibility(View.VISIBLE);
                holder.sendFailImg.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Auto-generated method stub
                        if (sendErrorListener != null) {
                            sendErrorListener.onClick(position);
                        }
                    }

                });
                break;
            default:
                break;
        }
//        holder.headicon.setImageDrawable(context.getResources()
//                .getDrawable(R.mipmap.grzx_tx_s));

        /* time */
        if (position != 0) {
            String showTime = getTime(tbub.getTime(), userList.get(position - 1)
                    .getTime());
            if (showTime != null) {
                holder.chat_time.setVisibility(View.VISIBLE);
                holder.chat_time.setText(showTime);
            } else {
                holder.chat_time.setVisibility(View.GONE);
            }
        } else {
            String showTime = getTime(tbub.getTime(), null);
            holder.chat_time.setVisibility(View.VISIBLE);
            holder.chat_time.setText(showTime);
        }

//        if (isPicRefresh) {
        holder.image_group.setVisibility(View.VISIBLE);
        final String imageSrc = tbub.getImageLocal() == null ? "" : tbub
                .getImageLocal();
        final String imageUrlSrc = tbub.getImageUrl() == null ? "" : tbub
                .getImageUrl();
        final String imageIconUrl = tbub.getImageIconUrl() == null ? ""
                : tbub.getImageIconUrl();
        File file = new File(imageSrc);
        final boolean hasLocal = !imageSrc.equals("")
                && file.exists();
        int res;
        res = R.drawable.chatto_bg_focused;
//        Glide.with(context).load(imageSrc).transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);
        Glide.with(context).load(imageSrc).placeholder(R.drawable.cygs_cs).transform(new CustomShapeTransformation(context, res)).into(holder.image_Msg);

        holder.image_Msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AToast.normal("image_Msg");
            }

        });
//        }
    }

    @SuppressLint("SimpleDateFormat")
    public String getTime(String time, String before) {
        String show_time = null;
        if (before != null) {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date now = df.parse(time);
                java.util.Date date = df.parse(before);
                long l = now.getTime() - date.getTime();
                long day = l / (24 * 60 * 60 * 1000);
                long hour = (l / (60 * 60 * 1000) - day * 24);
                long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                if (min >= 1) {
                    show_time = time.substring(11);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            show_time = time.substring(11);
        }
        String getDay = getDay(time);
        if (show_time != null && getDay != null)
            show_time = getDay + " " + show_time;
        return show_time;
    }

    @SuppressLint("SimpleDateFormat")
    public static String returnTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public String getDay(String time) {
        String showDay = null;
        String nowTime = returnTime();
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date now = df.parse(nowTime);
            java.util.Date date = df.parse(time);
            long l = now.getTime() - date.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            if (day >= 365) {
                showDay = time.substring(0, 10);
            } else if (day >= 1 && day < 365) {
                showDay = time.substring(5, 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showDay;
    }

    public static Bitmap getLoacalBitmap(String url) {
        try {
            ByteArrayOutputStream out;
            FileInputStream fis = new FileInputStream(url);
            BufferedInputStream bis = new BufferedInputStream(fis);
            out = new ByteArrayOutputStream();
            @SuppressWarnings("unused")
            int hasRead = 0;
            byte[] buffer = new byte[1024 * 2];
            while ((hasRead = bis.read(buffer)) > 0) {
                // 读出多少数据，向输出流中写入多少
                out.write(buffer);
                out.flush();
            }
            out.close();
            fis.close();
            bis.close();
            byte[] data = out.toByteArray();
            // 长宽减半
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 3;
            return BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}

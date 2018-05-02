package com.thoughtworks.chengdu.gb.moments.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.thoughtworks.chengdu.gb.moments.R;
import com.thoughtworks.chengdu.gb.moments.model.Comment;
import com.thoughtworks.chengdu.gb.moments.model.Sender;
import com.thoughtworks.chengdu.gb.moments.util.HelpUtils;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by GB on 2018/05/01.
 */

public class CommentAdapter extends SimpleRecAdapter<Comment, CommentAdapter.ViewHolder> {
    public static final int TAG_VIEW = 0;

    private Context mContext;
    public CommentAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_comment;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Comment comment = data.get(position);
        Sender sender = comment.getSender();

        StringBuilder contentBuilder = new StringBuilder("");
        if (sender != null && HelpUtils.checkNull(sender.getNick())) {
            contentBuilder.append(sender.getNick());
        }
        if (HelpUtils.checkNull(comment.getContent())) {
            contentBuilder.append("："+comment.getContent());
        }
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(contentBuilder.toString());
        int startIndex=0;
        if (sender != null && HelpUtils.checkNull(sender.getNick())) {
            stringBuilder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.item_tx_color)), 0, sender.getNick().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            startIndex=sender.getNick().length();
        }
        if (HelpUtils.checkNull(comment.getContent())) {
            String content="："+comment.getContent();
            stringBuilder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.item_text_color)), startIndex, startIndex+content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        holder.comment_user.setText(stringBuilder);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (getRecItemClick() != null) {
//                    getRecItemClick().onItemClick(position, orderInfo, TAG_VIEW, holder);
//                }
//            }
//        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.comment_user_name)
//        TextView comment_user_name;
//        @BindView(R.id.comment_user_date)
//        TextView comment_user_date;
        @BindView(R.id.comment_user)
        TextView comment_user;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}

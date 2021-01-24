package com.dreamernguyen.demoyokafo;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<TinNhan> mListTinNhan;
    Context context;

    public MessageAdapter(List<TinNhan> mListTinNhan) {
        this.mListTinNhan = mListTinNhan;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent,false);
        context = parent.getContext();
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        TinNhan tinNhan = mListTinNhan.get(position);
        if (tinNhan == null){
            return;
        }
        if(position %3 == 0){
            LayoutParams params = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.START;
            holder.layoutChat.setLayoutParams(params);
            holder.layoutChat.setBackground(context.getDrawable(R.drawable.bg_chat));
        }else {
            LayoutParams params = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.END;
            holder.layoutChat.setLayoutParams(params);
            holder.layoutChat.setBackground(context.getDrawable(R.drawable.bg_chat2));
        }
        Log.d("fff", "onBindViewHolder: "+ tinNhan.getNoiDung());
        holder.tvChat.setText(tinNhan.getNoiDung());






    }

    @Override
    public int getItemCount() {
        return mListTinNhan.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvChat;
        LinearLayout layoutChat;
        ImageView imageView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChat = (TextView) itemView.findViewById(R.id.tvChat);
            layoutChat = (LinearLayout) itemView.findViewById(R.id.layoutChat);

        }
    }
}

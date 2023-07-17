package com.bibon.furnitureshopping.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.models.ChatMessageModel;
import com.bibon.furnitureshopping.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ChatRVAdapter extends FirestoreRecyclerAdapter<ChatMessageModel, ChatRVAdapter.ChatModelViewHolder> {
    Context context;

    public ChatRVAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder holder, int position, @NonNull ChatMessageModel model) {
            if(model.getSenderId().equals(FirebaseUtil.currentUserId())){
                holder.leftChat.setVisibility(View.GONE);
                holder.rightChat.setVisibility(View.VISIBLE);
                holder.rightChatTv.setText(model.getMessage());
            }else {
                holder.rightChat.setVisibility(View.GONE);
                holder.leftChat.setVisibility(View.VISIBLE);
                holder.leftChatTv.setText(model.getMessage());
            }
    }

    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_chat_message_view, parent, false);
        return new ChatModelViewHolder(view);
    }

    class ChatModelViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftChat, rightChat;
        TextView leftChatTv, rightChatTv;


        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);

            leftChat = itemView.findViewById(R.id.left_chat);
            rightChat = itemView.findViewById(R.id.right_chat);
            leftChatTv = itemView.findViewById(R.id.left_chat_textview);
            rightChatTv = itemView.findViewById(R.id.right_chat_textview);

        }
    }
}

package com.example.devvault.ui.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devvault.R;
import com.example.devvault.utils.Constants;
import com.google.firebase.ai.type.Content;
import com.google.firebase.ai.type.Part;
import com.google.firebase.ai.type.TextPart;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{
    private List<Content> chatHistory;

    public ChatAdapter(List<Content> chatHistory) {
        this.chatHistory = chatHistory;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_bubble, parent, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        Content msg = chatHistory.get(position);

        String msgRole = msg.getRole();
        if (msgRole.equals("user")) {
            holder.senderTextView.setText(Constants.USER_CHATNAME);
            holder.chatBubbleRoot.setCardBackgroundColor(Color.WHITE);
        }
        else if (msgRole.equals("model")) {
            holder.senderTextView.setText(Constants.MODEL_CHATNAME);
        }

        Part msgText = msg.getParts().get(0);
        if (msgText instanceof TextPart) {
            holder.msgTextView.setText(((TextPart) msgText).getText());
        }
    }

    @Override
    public int getItemCount() {
        return chatHistory.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        CardView chatBubbleRoot;
        TextView senderTextView;
        TextView msgTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            chatBubbleRoot = itemView.findViewById(R.id.chatBubbleRoot);
            senderTextView = itemView.findViewById(R.id.sender_textview);
            msgTextView = itemView.findViewById(R.id.msg_textview);
        }
    }
}

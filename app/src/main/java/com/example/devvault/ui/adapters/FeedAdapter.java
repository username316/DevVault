package com.example.devvault.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.devvault.R;
import com.example.devvault.model.HNPost;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
    private List<HNPost> postList;

    public FeedAdapter(List<HNPost> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        HNPost post = postList.get(position);
        holder.titleTextView.setText(post.getTitle());
        holder.authorTextView.setText("By: " + post.getBy());
        holder.scoreTextView.setText("Score: " + post.getScore());

        holder.itemView.setOnClickListener(v -> {
            if (post.getUrl() != null && !post.getUrl().isEmpty()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(post.getUrl()));
                v.getContext().startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorTextView;
        TextView scoreTextView;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewFeedTitle);
            authorTextView = itemView.findViewById(R.id.textViewFeedAuthor);
            scoreTextView = itemView.findViewById(R.id.textViewFeedScore);
        }
    }
}

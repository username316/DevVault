package com.example.devvault.ui.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devvault.R;
import com.example.devvault.db.entity.Resource;
import com.example.devvault.model.HNPost;
import com.example.devvault.repository.ResourceRepository;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private static final int[] CARD_COLORS = {
            Color.parseColor("#C8F135"),
            Color.parseColor("#5DEAFF"),
            Color.parseColor("#C5B8FF"),
            Color.parseColor("#1A1A1A"),
    };

    private static final int[] TITLE_COLORS = {
            Color.parseColor("#1A1A1A"),
            Color.parseColor("#1A1A1A"),
            Color.parseColor("#1A1A1A"),
            Color.parseColor("#FFFFFF"),
    };

    private static final float[] ROTATIONS = { -1.5f, 1f, -2f, 1.5f };

    private final List<HNPost> postList;

    public FeedAdapter(List<HNPost> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        HNPost post = postList.get(position);
        int idx = position % CARD_COLORS.length;

        holder.cardRoot.setCardBackgroundColor(CARD_COLORS[idx]);
        holder.titleTextView.setTextColor(TITLE_COLORS[idx]);
        holder.titleTextView.setText(post.getTitle());
        holder.authorTextView.setText("by " + post.getBy());
        holder.scoreTextView.setText("↑ " + post.getScore());

        int pillText = idx == 3 ? Color.parseColor("#FFFFFF") : Color.parseColor("#1A1A1A");
        int pillBg   = idx == 3 ? Color.parseColor("#2E2E2E") : Color.parseColor("#FFFFFF");
        holder.authorTextView.setTextColor(pillText);
        holder.scoreTextView.setTextColor(pillText);
        holder.authorTextView.setBackgroundTintList(ColorStateList.valueOf(pillBg));
        holder.scoreTextView.setBackgroundTintList(ColorStateList.valueOf(pillBg));

        holder.itemView.setRotation(ROTATIONS[idx]);

        // + button: save post as a Resource
        holder.linkBtn.setOnClickListener(v -> {
            ResourceRepository repo = new ResourceRepository(v.getContext());

            Resource resource = new Resource();
            resource.title = post.getTitle() != null ? post.getTitle() : "Untitled";
            resource.url = post.getUrl() != null ? post.getUrl() : "";
            resource.description = "By " + post.getBy() + " · Score: " + post.getScore();
            resource.mediaType = "Article";
            resource.language = "EN";
            resource.createdAt = System.currentTimeMillis();

            new Thread(() -> {
                repo.insertResource(resource);
                // Toast must run on the main thread
                ((android.app.Activity) v.getContext()).runOnUiThread(() ->
                        Toast.makeText(v.getContext(), "Saved: " + resource.title, Toast.LENGTH_SHORT).show()
                );
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        CardView cardRoot;
        TextView titleTextView;
        TextView authorTextView;
        TextView scoreTextView;
        FrameLayout linkBtn;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            cardRoot = itemView.findViewById(R.id.feedCardRoot);
            titleTextView = itemView.findViewById(R.id.textViewFeedTitle);
            authorTextView = itemView.findViewById(R.id.textViewFeedAuthor);
            scoreTextView = itemView.findViewById(R.id.textViewFeedScore);
            linkBtn = itemView.findViewById(R.id.linkBtn);
        }
    }
}
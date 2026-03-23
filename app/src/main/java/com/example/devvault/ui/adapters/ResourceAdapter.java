package com.example.devvault.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devvault.R;
import com.example.devvault.db.entity.Resource;
import com.example.devvault.ui.DetailActivity;

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder> {

    // The four card background colours from the design
    private static final int[] CARD_COLORS = {
            Color.parseColor("#C8F135"), // lime
            Color.parseColor("#5DEAFF"), // cyan
            Color.parseColor("#C5B8FF"), // lavender
            Color.parseColor("#1A1A1A"), // dark
    };

    // When the dark card is used, title text should be white
    private static final int[] TITLE_COLORS = {
            Color.parseColor("#1A1A1A"),
            Color.parseColor("#1A1A1A"),
            Color.parseColor("#1A1A1A"),
            Color.parseColor("#FFFFFF"),
    };

    // Slight rotation per card for the playful tilt effect
    private static final float[] ROTATIONS = { -2f, 1.5f, -1f, 2.5f };

    private final List<Resource> resourceList;

    public ResourceAdapter(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resource_card_layout, parent, false);
        return new ResourceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder holder, int position) {
        Resource resource = resourceList.get(position);
        int colorIdx = position % CARD_COLORS.length;

        // Card background colour
        holder.cardRoot.setCardBackgroundColor(CARD_COLORS[colorIdx]);

        // Title text colour
        holder.titleTextView.setTextColor(TITLE_COLORS[colorIdx]);
        holder.titleTextView.setText(resource.title);

        // Type pill label (reuse mediaType or language as "duration" stand-in)
        String pill1 = resource.mediaType != null && !resource.mediaType.isEmpty()
                ? resource.mediaType : "Resource";
        String pill2 = resource.language != null && !resource.language.isEmpty()
                ? resource.language : "";
        holder.typeTextView.setText(pill1);
        holder.sessionsTextView.setText(pill2);
        holder.sessionsTextView.setVisibility(pill2.isEmpty() ? View.GONE : View.VISIBLE);

        // Tilt rotation for visual playfulness
        holder.itemView.setRotation(ROTATIONS[colorIdx]);

        // Navigate to detail on click
        holder.itemView.setOnClickListener(v -> {
            Context ctx = v.getContext();
            Intent intent = new Intent(ctx, DetailActivity.class);
            intent.putExtra("resource", resource);
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    public static class ResourceViewHolder extends RecyclerView.ViewHolder {
        CardView cardRoot;
        TextView titleTextView;
        TextView typeTextView;
        TextView sessionsTextView;
        TextView overviewTextView;

        public ResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            cardRoot = itemView.findViewById(R.id.cardRoot);
            titleTextView = itemView.findViewById(R.id.resource_card_title);
            typeTextView = itemView.findViewById(R.id.resource_card_type);
            sessionsTextView = itemView.findViewById(R.id.resource_card_sessions);
            overviewTextView = itemView.findViewById(R.id.resource_card_overview);
        }
    }
}
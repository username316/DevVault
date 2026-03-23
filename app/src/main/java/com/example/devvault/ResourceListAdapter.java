package com.example.devvault;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResourceListAdapter extends RecyclerView.Adapter<ResourceListAdapter.ResourceViewHolder> {
    private ArrayList<ResourceDataSet> resourceList;

    public ResourceListAdapter(ArrayList<ResourceDataSet> resourceList) {
        this.resourceList = resourceList;
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_card_layout, parent, false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder holder, int position) {
        ResourceDataSet resource = resourceList.get(position);
        holder.title.setText(resource.getTitle());
        holder.type.setText(resource.getType());
        holder.overview.setText(resource.getTextContent());
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    public class ResourceViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView type;
        TextView overview;

        public ResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.resource_card_title);
            type = itemView.findViewById(R.id.resource_card_type);
            overview = itemView.findViewById(R.id.resource_card_overview);
        }
    }
}

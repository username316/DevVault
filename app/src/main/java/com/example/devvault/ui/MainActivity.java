package com.example.devvault.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devvault.R;
import com.example.devvault.db.entity.Resource;
import com.example.devvault.repository.ResourceRepository;
import com.example.devvault.ui.adapters.ResourceAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ResourceRepository repository;
    private ResourceAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new ResourceRepository(this);
        recyclerView = findViewById(R.id.resource_list);

        // Stagger cards vertically; slight horizontal offset is handled by rotation in adapter
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

        // Buttons
        MaterialButton buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddResourceActivity.class)));

        LinearLayout buttonHome = findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(v -> loadResources());

        LinearLayout buttonFeed = findViewById(R.id.buttonFeed);
        buttonFeed.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, FeedActivity.class)));

        LinearLayout buttonChat = findViewById(R.id.buttonChat);
        buttonChat.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ChatActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadResources();
    }

    private void loadResources() {
        List<Resource> resources = repository.getAllResources();
        adapter = new ResourceAdapter(resources);
        recyclerView.setAdapter(adapter);
    }
}
package com.example.devvault.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.devvault.R;
import com.example.devvault.db.entity.Resource;
import com.example.devvault.repository.ResourceRepository;
import com.example.devvault.ui.adapters.ResourceAdapter;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonHome = findViewById(R.id.buttonHome);
        Button buttonFeed = findViewById(R.id.buttonFeed);

        buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddResourceActivity.class);
            startActivity(intent);
        });

        buttonHome.setOnClickListener(v -> loadResources());

        buttonFeed.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FeedActivity.class);
            startActivity(intent);
        });
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
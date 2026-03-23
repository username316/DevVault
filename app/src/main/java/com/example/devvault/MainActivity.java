package com.example.devvault;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.devvault.viewmodel.ResourceViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.lifecycle.ViewModelProvider;
import com.example.devvault.R;
import com.example.devvault.db.entity.Resource;
import com.example.devvault.repository.ResourceRepository;
import com.example.devvault.ui.AddResourceActivity;
import com.example.devvault.ui.adapters.ResourceAdapter;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ResourceRepository repository;
    private ResourceAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = new ResourceRepository(this);
        recyclerView = findViewById(R.id.resource_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonHome = findViewById(R.id.buttonHome);

        buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddResourceActivity.class);
            startActivity(intent);
        });

        buttonHome.setOnClickListener(v -> {
            loadResources();
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

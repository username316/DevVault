package com.example.devvault.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.devvault.R;
import com.example.devvault.db.entity.Resource;
import com.example.devvault.repository.ResourceRepository;

public class DetailActivity extends AppCompatActivity {
    private TextView textViewTitle, textViewTypeLang, textViewUrl, textViewDescription;
    private Button buttonDelete;
    private ResourceRepository repository;
    private Resource resource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewTitle = findViewById(R.id.textViewDetailTitle);
        textViewTypeLang = findViewById(R.id.textViewDetailTypeLang);
        textViewUrl = findViewById(R.id.textViewDetailUrl);
        textViewDescription = findViewById(R.id.textViewDetailDescription);
        buttonDelete = findViewById(R.id.buttonDelete);

        repository = new ResourceRepository(this);

        resource = (Resource) getIntent().getSerializableExtra("resource");
        if (resource != null) {
            displayResource();
        }

        buttonDelete.setOnClickListener(v -> deleteResource());
    }

    private void displayResource() {
        textViewTitle.setText(resource.title);
        textViewTypeLang.setText(resource.mediaType + " | " + resource.language);
        textViewUrl.setText(resource.url);
        textViewDescription.setText(resource.description);
    }

    private void deleteResource() {
        if (resource != null) {
            repository.deleteResource(resource);
            Toast.makeText(this, "Resource deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}

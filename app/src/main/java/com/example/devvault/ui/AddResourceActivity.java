package com.example.devvault.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.devvault.R;
import com.example.devvault.db.entity.Resource;
import com.example.devvault.repository.ResourceRepository;

public class AddResourceActivity extends AppCompatActivity {
    private EditText editTextTitle, editTextUrl, editTextMediaType, editTextLanguage, editTextDescription;
    private Button buttonSave;
    private ResourceRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextUrl = findViewById(R.id.editTextUrl);
        editTextMediaType = findViewById(R.id.editTextMediaType);
        editTextLanguage = findViewById(R.id.editTextLanguage);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonSave = findViewById(R.id.buttonSave);

        repository = new ResourceRepository(this);

        buttonSave.setOnClickListener(v -> saveResource());
    }

    private void saveResource() {
        String title = editTextTitle.getText().toString().trim();
        String url = editTextUrl.getText().toString().trim();
        String mediaType = editTextMediaType.getText().toString().trim();
        String language = editTextLanguage.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty() || url.isEmpty()) {
            Toast.makeText(this, "Title and URL are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Resource resource = new Resource();
        resource.title = title;
        resource.url = url;
        resource.mediaType = mediaType;
        resource.language = language;
        resource.description = description;
        resource.createdAt = System.currentTimeMillis();

        new Thread(() -> {
            repository.insertResource(resource);
            runOnUiThread(() -> {
                Toast.makeText(this, "Resource saved", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}

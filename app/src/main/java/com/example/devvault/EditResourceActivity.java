package com.example.devvault;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.devvault.db.entity.Resource;
import com.example.devvault.viewmodel.ResourceViewModel;

public class EditResourceActivity extends AppCompatActivity {

    private ResourceViewModel resourceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_resource);

        resourceViewModel = new ViewModelProvider(this).get(ResourceViewModel.class);

        String[] types = new String[]{"Code Snippet", "Link", "Blog"};

        EditText titleInput = findViewById(R.id.title_input);
        Spinner typeDropdown = findViewById(R.id.type_input);
        EditText contentInput = findViewById(R.id.content_input);

        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        typeDropdown.setAdapter(dropdownAdapter);

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString().trim();
                String type = typeDropdown.getSelectedItem().toString();
                String content = contentInput.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(EditResourceActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Resource resource = new Resource(title, type, content, System.currentTimeMillis());
                resourceViewModel.insert(resource);

                Toast.makeText(EditResourceActivity.this, "Resource saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

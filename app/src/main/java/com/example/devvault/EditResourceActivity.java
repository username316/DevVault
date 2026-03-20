package com.example.devvault;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditResourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_resource);

        String[] types = new String[]{"Code Snippet", "Link", "Blog"};

        EditText titleInput = findViewById(R.id.title_input);

        Spinner typeDropdown = findViewById(R.id.type_input);
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        typeDropdown.setAdapter(dropdownAdapter);

        EditText contentInput = findViewById(R.id.content_input);

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
                String title = titleInput.getText().toString();
                String type = typeDropdown.getSelectedItem().toString();
                String content = contentInput.getText().toString();

                ResourceDataSet resourceDataSet = new ResourceDataSet(title, type, content);
                //TODO: save resourceDataSet

                finish();
            }
        });
    }
}
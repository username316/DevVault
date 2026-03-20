package com.example.devvault;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ResourceDataSet> resourceDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: fill resourceDataList

        RecyclerView resourceList = findViewById(R.id.resource_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        resourceList.setLayoutManager(linearLayoutManager);
        //ResourceListAdapter resourceListAdapter = new ResourceListAdapter(resourceDataList);
        //resourceList.setAdapter(resourceListAdapter);

        /**Button addResButton = findViewById(R.id.add_resource_button);
        addResButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });**/
    }
}
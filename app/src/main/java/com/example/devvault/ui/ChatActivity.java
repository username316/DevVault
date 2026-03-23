package com.example.devvault.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devvault.R;
import com.example.devvault.ui.adapters.ChatAdapter;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.ai.FirebaseAI;
import com.google.firebase.ai.GenerativeModel;
import com.google.firebase.ai.java.ChatFutures;
import com.google.firebase.ai.java.GenerativeModelFutures;
import com.google.firebase.ai.type.Content;
import com.google.firebase.ai.type.GenerateContentResponse;
import com.google.firebase.ai.type.GenerativeBackend;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ChatActivity extends AppCompatActivity {
    Executor executor;
    GenerativeModel ai;
    GenerativeModelFutures model;
    ChatFutures chat;
    List<Content> history;
    RecyclerView chatRecyclerView;
    ChatAdapter chatAdapter;
    EditText msgEditText;
    TextView navHome;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        history = new ArrayList<Content>();

        executor = command -> new Handler(Looper.getMainLooper()).post(command);
        ai = FirebaseAI.getInstance(GenerativeBackend.googleAI())
                .generativeModel("gemini-2.5-flash");
        model = GenerativeModelFutures.from(ai);

        Content.Builder starterContentBuilder = new Content.Builder();
        starterContentBuilder.setRole("model");
        starterContentBuilder.addText("Hello, I'm your AI coding assistant. Ask me anything!");
        Content starterContent = starterContentBuilder.build();
        history.add(starterContent);

        chat = model.startChat(history);

        chatRecyclerView = findViewById(R.id.chat_history_recyclerview);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(history);
        chatRecyclerView.setAdapter(chatAdapter);

        msgEditText = findViewById(R.id.msg_edittext);

        navHome = findViewById(R.id.nav_home_textview);
        navHome.setOnClickListener(v -> {
            startActivity(new Intent(ChatActivity.this, MainActivity.class));
            finish();
        });

        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(v -> {
            String msg = msgEditText.getText().toString().trim();
            msgEditText.setText("");
            if (!msg.isEmpty()) {
                chat(msg);
            }
        });
    }

    public void addMsgToHistory(Content msg) {
        history.add(msg);
        chatAdapter.notifyItemInserted(chatAdapter.getItemCount() - 1);
        chatRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
    }

    private void chat(String msg) {
        Content.Builder messageBuilder = new Content.Builder();
        messageBuilder.setRole("user");
        messageBuilder.addText(msg);
        Content message = messageBuilder.build();

        addMsgToHistory(message);

        ListenableFuture<GenerateContentResponse> response = chat.sendMessage(message);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();

                if (resultText != null) {
                    Content.Builder replyBuilder = new Content.Builder();
                    replyBuilder.setRole("model");
                    replyBuilder.addText(resultText);
                    Content reply = replyBuilder.build();

                    addMsgToHistory(reply);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, executor);
    }
}
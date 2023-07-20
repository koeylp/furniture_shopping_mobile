package com.bibon.furnitureshopping.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.adapters.ChatRVAdapter;
import com.bibon.furnitureshopping.models.ChatMessageModel;
import com.bibon.furnitureshopping.models.ChatRoomModel;
import com.bibon.furnitureshopping.models.UserChat;
import com.bibon.furnitureshopping.utils.AndroidUtils;
import com.bibon.furnitureshopping.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    UserChat adminModel;
    String chatroomId;
    ChatRoomModel chatRoomModel;
    EditText messageInput;
    ImageButton sendBtn, backBtn;
    RecyclerView rvChat;
    TextView headerName;
    ChatRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        adminModel = AndroidUtils.getAdminModelFromIntent(getIntent());
        chatroomId = FirebaseUtil.getChatRoomId(FirebaseUtil.currentUserId(), adminModel.getUserId());

        messageInput = (EditText) findViewById(R.id.message_input);
        sendBtn = (ImageButton) findViewById(R.id.btn_send);
        backBtn = (ImageButton) findViewById(R.id.imageBack);
        rvChat = (RecyclerView) findViewById(R.id.rvChat);
        headerName = (TextView) findViewById(R.id.headerName);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sendBtn.setOnClickListener((v -> {
            String message = messageInput.getText().toString().trim();
            if (message.isEmpty()) {
                return;
            }
            sendMessageToUser(message);
        }));
        if (adminModel.getUsername().equals("admin")) {
            Toast.makeText(ChatActivity.this, "Chào bạn đến với Home Furniture " , Toast.LENGTH_SHORT).show();
        } else {
            headerName.setText("Chat with user: " + adminModel.getUsername());
        }

        getOrCreateChatRoomModel();
        setupChatRecyclerView();
    }

    void setupChatRecyclerView() {
        Query query = FirebaseUtil.getChatRoomMessageReference(chatroomId)
                .orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query, ChatMessageModel.class).build();

        adapter = new ChatRVAdapter(options, getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        rvChat.setLayoutManager(manager);
        rvChat.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                rvChat.smoothScrollToPosition(0);
            }
        });
    }


    void sendMessageToUser(String message) {
        chatRoomModel.setLastMessageTimestamp(Timestamp.now());
        chatRoomModel.setLastMessageSenderId(FirebaseUtil.currentUserId());
        FirebaseUtil.getChatRoomReference(chatroomId).set(chatRoomModel);
        ChatMessageModel chatMessageModel = new ChatMessageModel(message, FirebaseUtil.currentUserId(), Timestamp.now());
        FirebaseUtil.getChatRoomMessageReference(chatroomId).add(chatMessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            messageInput.setText("");
                        }
                    }
                });

    }

    public void getOrCreateChatRoomModel() {
        FirebaseUtil.getChatRoomReference(chatroomId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    chatRoomModel = task.getResult().toObject(ChatRoomModel.class);
                    if (chatroomId != null) {
                        //first time chat
                        chatRoomModel = new ChatRoomModel(
                                chatroomId,
                                Arrays.asList(FirebaseUtil.currentUserId(), adminModel.getUserId()),
                                Timestamp.now(),
                                ""
                        );
                        FirebaseUtil.getChatRoomReference(chatroomId).set(chatRoomModel);
                    }
                }
            }
        });
    }


}
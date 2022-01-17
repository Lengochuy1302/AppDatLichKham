package com.example.datlichkham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datlichkham.adapter.MessageAdapter;
import com.example.datlichkham.model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MessageActivity extends AppCompatActivity {
    private String receiverId, senderId;

    private EditText edTextSend;
    private ImageView btnSend;

    private RecyclerView rcShowMessage;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setUpToolBar();
        //id nguoi nhan tin nhan
        receiverId = getIntent().getStringExtra("RECEIVER");
        //id nguoi gui tin nhan
        senderId = getSharedPreferences("PREFS", MODE_PRIVATE).getString("USERNAME", "");

        edTextSend = findViewById(R.id.ed_text_send);
        rcShowMessage = findViewById(R.id.rcShowMessage);
        rcShowMessage.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rcShowMessage.setLayoutManager(linearLayoutManager);


        btnSend = findViewById(R.id.btnSendMessage);
        btnSend.setOnClickListener(v -> {
            String msg = edTextSend.getText().toString().trim();
            if(!msg.isEmpty()){
                sendMessage(receiverId, senderId, msg);
                edTextSend.setText("");
            }

        });

        getMessage(senderId, receiverId);
    }

    private void getMessage(final String senderId, final String receiverId){
        messageList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Message obj = ds.getValue(Message.class);
                    if(obj.getSenderId().equalsIgnoreCase(senderId) && obj.getReceiverId().equalsIgnoreCase(receiverId)
                            || obj.getSenderId().equalsIgnoreCase(receiverId) && obj.getReceiverId().equalsIgnoreCase(senderId )){
                        messageList.add(obj);
                    }
                }

                messageAdapter = new MessageAdapter(MessageActivity.this, messageList);
                rcShowMessage.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(String receiverId, String senderId, String msg) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chats");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("receiverId", receiverId);
        hashMap.put("senderId", senderId);
        hashMap.put("msg", msg);

        databaseReference.push().setValue(hashMap);
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        TextView tvReceiverName = findViewById(R.id.tvReceiverName);
        tvReceiverName.setText(getIntent().getStringExtra("RECEIVERNAME"));
    }
}
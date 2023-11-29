package vn.giapvantai.chattingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatWindowActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST_CODE = 1;

    private String reciverImg, reciverUid, reciverName, senderUid;
    private CircleImageView profile;
    private TextView reciverNameTextView;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    public static String senderImg;
    public static String reciverImgStatic;
    private CardView sendButton, sendFileButton;
    private EditText textMessage;

    private String senderRoom, reciverRoom;
    private RecyclerView messageAdapter;
    private ArrayList<msgModelclass> messagesArrayList;
    private MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        reciverName = getIntent().getStringExtra("nameeee");
        reciverImg = getIntent().getStringExtra("reciverImg");
        reciverUid = getIntent().getStringExtra("uid");

        messagesArrayList = new ArrayList<>();

        sendButton = findViewById(R.id.sendbtnn);
        sendFileButton = findViewById(R.id.sendFilebtn);
        textMessage = findViewById(R.id.textmsg);
        reciverNameTextView = findViewById(R.id.recivername);
        profile = findViewById(R.id.profileimgg);
        messageAdapter = findViewById(R.id.msgadpter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageAdapter.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(ChatWindowActivity.this, messagesArrayList);
        messageAdapter.setAdapter(messagesAdapter);

        Picasso.get().load(reciverImg).into(profile);
        reciverNameTextView.setText(reciverName);

        senderUid = firebaseAuth.getUid();

        senderRoom = senderUid + reciverUid;
        reciverRoom = reciverUid + senderUid;

        DatabaseReference reference = database.getReference().child("user").child(Objects.requireNonNull(firebaseAuth.getUid()));
        DatabaseReference chatReference = database.getReference().child("chats").child(senderRoom).child("messages");

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    msgModelclass messages = dataSnapshot.getValue(msgModelclass.class);
                    messagesArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderImg = Objects.requireNonNull(snapshot.child("profilepic").getValue()).toString();
                reciverImgStatic = reciverImg;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });

        sendButton.setOnClickListener(view -> {
            String message = textMessage.getText().toString();
            if (message.isEmpty()) {
                Toast.makeText(ChatWindowActivity.this, "Enter The Message First", Toast.LENGTH_SHORT).show();
                return;
            }
            textMessage.setText("");
            Date date = new Date();
            msgModelclass messages = new msgModelclass(message, senderUid, date.getTime(), null);

            database = FirebaseDatabase.getInstance();
            DatabaseReference senderReference = database.getReference().child("chats").child(senderRoom).child("messages").push();
            DatabaseReference reciverReference = database.getReference().child("chats").child(reciverRoom).child("messages").push();

            senderReference.setValue(messages).addOnCompleteListener(task -> reciverReference.setValue(messages).addOnCompleteListener(task1 -> {
                // Handle onComplete
            }));
        });

        sendFileButton.setOnClickListener(view -> {
            // Mở một Intent để chọn file
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Người dùng đã chọn một file, tải file lên Firebase Storage
            Uri fileUri = data.getData();
            uploadFileToFirebase(fileUri);
        }
    }

    private void uploadFileToFirebase(Uri fileUri) {
        // Tạo một tham chiếu đến Firebase Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        // Tạo một tham chiếu đến file sẽ được tải lên
        StorageReference fileReference = storageReference.child("files/" + UUID.randomUUID().toString());

        // Tải file lên Firebase
        fileReference.putFile(fileUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // File đã được tải lên thành công, lấy URL của file
                    fileReference.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                // Lưu URL của file vào cơ sở dữ liệu Firebase
                                String fileUrl = uri.toString();
                                saveFileUrlToDatabase(fileUrl);
                            });
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi khi tải file lên Firebase
                });
    }

    private void saveFileUrlToDatabase(String fileUrl) {
        // Lưu URL của file vào cơ sở dữ liệu Firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("chats").child(senderRoom).child("messages").push().setValue(new msgModelclass(null, senderUid, System.currentTimeMillis(), fileUrl));
    }
}

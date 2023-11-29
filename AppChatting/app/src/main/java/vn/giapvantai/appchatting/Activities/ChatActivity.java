package vn.giapvantai.appchatting.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import vn.giapvantai.appchatting.R;
import vn.giapvantai.appchatting.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
}
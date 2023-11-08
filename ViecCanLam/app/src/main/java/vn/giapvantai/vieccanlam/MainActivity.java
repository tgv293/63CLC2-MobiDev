package vn.giapvantai.vieccanlam;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<TASKS> lstVCL;
    TaskRVadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Lắng nghe và xử lý
        lstVCL = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("TASKS");
        databaseReference.addValueEventListener(ngheFB);

        // Tìm điều khiển
        RecyclerView recyclerView = findViewById(R.id.rcvVCL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration( new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new TaskRVadapter(lstVCL);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            Intent  intent = new Intent(MainActivity.this, ThemTaskActivity.class);
            startActivity(intent);
        });


    }
    //
    ValueEventListener ngheFB= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            lstVCL.clear();
            // Lấy dữ liệu từ biến snapshot, đưa vào một biến danh sách để xử lý
            for (DataSnapshot obj: snapshot.getChildren()  ) {
                TASKS task  =  obj.getValue(TASKS.class);
                lstVCL.add(task);
//                Log.w("VCLapp", "Tên việc cần làm : "+ task.getName()  );
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
}
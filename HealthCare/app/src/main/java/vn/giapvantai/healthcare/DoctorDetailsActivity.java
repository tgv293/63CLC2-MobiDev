package vn.giapvantai.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

    private TextView tv;
    private ArrayList<HashMap<String, String>> list;
    private SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        initializeUI();
        fetchDoctorsFromFirebase(getTitleFromIntent());
    }

    private void initializeUI() {
        tv = findViewById(R.id.tvDDTitle);
        Button btn_back = findViewById(R.id.btn_DDBack);
        btn_back.setOnClickListener(v -> startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class)));
        list = new ArrayList<>();
    }

    private String getTitleFromIntent() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        tv.setText(title);
        return title;
    }

    private void fetchDoctorsFromFirebase(String title) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DOCTORS").child(title);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                populateListWithDataSnapshot(dataSnapshot);
                populateListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DoctorDetailsActivity.this, "Lỗi khi tải dữ liệu từ Firebase: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void populateListWithDataSnapshot(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            HashMap<String, String> item = new HashMap<>();
            item.put("line1", ds.child("doctorName").getValue(String.class));
            item.put("line2", "Địa chỉ: " + ds.child("hospitalAddress").getValue(String.class));
            item.put("line3", "KN: " + ds.child("experience").getValue(String.class));
            item.put("line4", "SĐT: " + ds.child("mobilePhone").getValue(String.class));
            item.put("line5", ds.child("fee").getValue(String.class));
            list.add(item);
        }
    }

    private void populateListView() {
        sa = new SimpleAdapter(this, list, R.layout.multi_lines, new String[]{"line1", "line2", "line3", "line4", "line5"}, new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        ListView lst = findViewById(R.id.listViewDD);
        lst.setAdapter(sa);
    }
}

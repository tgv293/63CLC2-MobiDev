package vn.giapvantai.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorDetailsActivity extends AppCompatActivity {

    private DatabaseReference firebaseRef;
    private TextView tv;
    private ArrayList<HashMap<String, String>> list;
    private SimpleAdapter sa;
    private Map<String, String> titleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        initializeUI();
        String title = getTitleFromIntent();
        firebaseRef = FirebaseDatabase.getInstance().getReference("DOCTORS").child(title);
        fetchDoctorsFromFirebase();
    }

    private void initializeUI() {
        tv = findViewById(R.id.tvDDTitle);
        Button btnBack = findViewById(R.id.btn_DDBack);
        btnBack.setOnClickListener(v -> navigateToFindDoctorActivity());
        list = new ArrayList<>();
        titleMap = createTitleMap();
    }

    private Map<String, String> createTitleMap() {
        Map<String, String> titleMap = new HashMap<>();
        titleMap.put("Family Physicians", "Bác sĩ gia đình");
        titleMap.put("Dietician", "Chuyên gia dinh dưỡng");
        titleMap.put("Dentist", "Nha sĩ");
        titleMap.put("Surgeon", "Bác sĩ phẫu thuật");
        titleMap.put("Cardiologists", "Bác sĩ tim mạch");
        return titleMap;
    }

    private void navigateToFindDoctorActivity() {
        startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
    }

    private String getTitleFromIntent() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String displayTitle = titleMap.get(title);
        tv.setText(displayTitle);
        return title;
    }

    private void fetchDoctorsFromFirebase() {
        firebaseRef.addValueEventListener(new ValueEventListener() {
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
            item.put("doctorName", ds.child("doctorName").getValue(String.class));
            item.put("hospitalAddress", "Địa chỉ: " + ds.child("hospitalAddress").getValue(String.class));
            item.put("experience", "KN: " + ds.child("experience").getValue(String.class));
            item.put("mobilePhone", "SĐT: " + ds.child("mobilePhone").getValue(String.class));
            item.put("fee", ds.child("fee").getValue(String.class) + " VND");
            list.add(item);
        }
    }

    private void populateListView() {
        sa = new SimpleAdapter(this, list, R.layout.multi_lines, new String[]{"doctorName", "hospitalAddress", "experience", "mobilePhone", "fee"}, new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        ListView lst = findViewById(R.id.listViewDD);
        lst.setAdapter(sa);

        lst.setOnItemClickListener((parent, view, position, id) -> handleListViewItemClick(position));
    }

    private void handleListViewItemClick(int position) {
        HashMap<String, String> selectedDoctor = list.get(position);
        Intent intent = createBookAppointmentIntent(selectedDoctor);
        startActivity(intent);
    }

    private Intent createBookAppointmentIntent(HashMap<String, String> selectedDoctor) {
        Intent intent = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
        intent.putExtra("title", tv.getText().toString());
        intent.putExtra("doctorName", selectedDoctor.get("doctorName"));
        intent.putExtra("hospitalAddress", selectedDoctor.get("hospitalAddress"));
        intent.putExtra("mobilePhone", selectedDoctor.get("mobilePhone"));
        intent.putExtra("fee", selectedDoctor.get("fee"));
        return intent;
    }
}

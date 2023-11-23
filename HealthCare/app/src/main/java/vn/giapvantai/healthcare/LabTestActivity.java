package vn.giapvantai.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class LabTestActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> packageList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        Button btnBack = findViewById(R.id.btn_LTGTCart);
        listView = findViewById(R.id.listViewLT);

        btnBack.setOnClickListener(v -> startActivity(new Intent(LabTestActivity.this, HomeActivity.class)));

        packageList = new ArrayList<>();

        loadPackagesFromFirebase();
        setupListViewListener();
    }

    private void loadPackagesFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PACKAGES");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot packageSnapshot: dataSnapshot.getChildren()) {
                    HashMap<String, String> item = new HashMap<>();
                    item.put("title", packageSnapshot.child("title").getValue(String.class));
                    item.put("name", packageSnapshot.child("name").getValue(String.class));
                    item.put("price", packageSnapshot.child("price").getValue(String.class) + " VND");
                    GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                    item.put("details", String.join("\n", Objects.requireNonNull(packageSnapshot.child("details").getValue(t))));
                    packageList.add(item);
                }

                SimpleAdapter sa = new SimpleAdapter(LabTestActivity.this, packageList, R.layout.multi_lines2, new String[]{"title","name","price"}, new int[]{R.id.line_a, R.id.line_b,R.id.line_c});
                listView.setAdapter(sa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LabTestActivity.this, "Tải dữ liệu thất bại. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupListViewListener() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            HashMap<String, String> selectedPackage = packageList.get(position);
            Intent intent = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
            intent.putExtra("name", selectedPackage.get("name"));
            intent.putExtra("details", selectedPackage.get("details"));
            intent.putExtra("price", selectedPackage.get("price"));
            startActivity(intent);
        });
    }
}

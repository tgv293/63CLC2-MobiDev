package vn.giapvantai.vieccanlam;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ThemTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_task);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(v -> {
            // Lấy dữ liệu
            EditText editTextName = findViewById(R.id.editTextTenCV);
            EditText editTextMess = findViewById(R.id.editTextMesage);
            EditText editTextDate = findViewById(R.id.editTextDate);
            EditText editTextPrio = findViewById(R.id.editTextPrio);

            String tenCV = editTextName.getText().toString();
            String mess = editTextMess.getText().toString();
            String dat = editTextDate.getText().toString();
            String pri = editTextPrio.getText().toString();
            // Gói vào đối tượng TASK
            TASKS task = new TASKS(tenCV, dat, mess, pri);
            // Kết nói DB, và thêm
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = database.getReference("TASKS");
            String key = databaseReference.push().getKey();

            HashMap<String, Object> item = new HashMap<>();
            item.put(key, task.toFirebaseObject());

            databaseReference.updateChildren(item, (error, ref) -> {
                if (error == null) finish();
            });


        });
    }
}
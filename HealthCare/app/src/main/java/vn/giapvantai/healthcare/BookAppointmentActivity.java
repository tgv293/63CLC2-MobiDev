package vn.giapvantai.healthcare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookAppointmentActivity extends AppCompatActivity {

    private TextView tvFullName, tvAddress, tvContact, tvFees;
    private TextView tvTitle;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button btnDate, btnTime, btnBook, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        initializeViews();
        initializeData();
        initializeDatePicker();
        initializeTimePicker();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookAppointmentActivity.this, FindDoctorActivity.class));
            }
        });

        //TODO: Chưa clean code và sửa thông tin data
//        btnBook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get a reference to the database
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("APPOINTMENTS");
//
//                // Create a new appointment
//                Map<String, String> appointment = new HashMap<>();
//                appointment.put("title", tvTitle.getText().toString());
//                appointment.put("fullName", tvFullName.getText().toString());
//                appointment.put("address", tvAddress.getText().toString());
//                appointment.put("contact", tvContact.getText().toString());
//                appointment.put("fees", tvFees.getText().toString());
//                appointment.put("date", btnDate.getText().toString());
//                appointment.put("time", btnTime.getText().toString());
//
//                // Generate a new child location using push() and save the appointment there
//                myRef.push().setValue(appointment);
//
//                // Navigate to the next activity (if necessary)
//                // startActivity(new Intent(BookAppointmentActivity.this, NextActivity.class));
//            }
//        });

    }

    private void initializeViews() {
        tvTitle = findViewById(R.id.tvBATitle);
        tvFullName = findViewById(R.id.input_ba_fullname);
        tvAddress = findViewById(R.id.input_ba_address);
        tvContact = findViewById(R.id.input_ba_contactnumber);
        tvFees = findViewById(R.id.input_ba_fees);
        btnDate = findViewById(R.id.btn_ba_date);
        btnTime = findViewById(R.id.btn_ba_time);
        btnBook = findViewById(R.id.btn_ba_book);
        btnBack = findViewById(R.id.btn_ba_back);

        tvFullName.setKeyListener(null);
        tvAddress.setKeyListener(null);
        tvContact.setKeyListener(null);
        tvFees.setKeyListener(null);
    }

    private void initializeData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String fullName = intent.getStringExtra("doctorName");
        String address = intent.getStringExtra("hospitalAddress");
        String contact = intent.getStringExtra("mobilePhone");
        String fees = intent.getStringExtra("fee");

        tvTitle.setText(title);
        tvFullName.setText(fullName);
        tvAddress.setText(address);
        tvContact.setText(contact);
        tvFees.setText(fees);
    }

    private void initializeDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            String date = String.format(new Locale("vi", "VN"), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
            btnDate.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Material_Dialog_Alert, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);

        btnDate.setOnClickListener(v -> datePickerDialog.show());

        // Set current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "VN"));
        String currentDate = dateFormat.format(cal.getTime());
        btnDate.setText(currentDate);
    }

    private void initializeTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            String time = String.format(new Locale("vi", "VN"), "%02d:%02d", hourOfDay, minute);
            btnTime.setText(time);
        };

        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR);
        int mins = cal.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Material_Dialog_Alert, timeSetListener, hrs, mins, true);

        btnTime.setOnClickListener(v -> timePickerDialog.show());

        // Set current time
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", new Locale("vi", "VN"));
        String currentTime = timeFormat.format(cal.getTime());
        btnTime.setText(currentTime);
    }
}

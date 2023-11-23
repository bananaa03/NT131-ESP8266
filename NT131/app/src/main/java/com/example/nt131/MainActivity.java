package com.example.nt131;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView temperatureTextView;
    private DatabaseReference temperatureRef;
    private DatabaseReference humidityRef;
    private Button lightSwitch1;
    private Button lightSwitch2;
    private Button lightSwitch3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightSwitch1 = findViewById(R.id.lightSwitch1);
        lightSwitch2 = findViewById(R.id.lightSwitch2);
        lightSwitch3 = findViewById(R.id.lightSwitch3);

        lightSwitch1.setOnClickListener(this);
        lightSwitch2.setOnClickListener(this);
        lightSwitch3.setOnClickListener(this);

        temperatureTextView = findViewById(R.id.temperatureTextView);

        // Kết nối đến Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        temperatureRef = firebaseDatabase.getReference("temperature");

        // Lắng nghe sự thay đổi của dữ liệu nhiệt độ
        temperatureRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Đọc giá trị nhiệt độ từ DataSnapshot
                String temperature = dataSnapshot.getValue(String.class);

                // Hiển thị nhiệt độ trong TextView
                temperatureTextView.setText(temperature);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });

        // Lắng nghe sự thay đổi dữ liệu độ ẩm
        humidityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Lấy giá trị độ ẩm từ dataSnapshot
                String humidity = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Xử lý khi có lỗi xảy ra
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lightSwitch1) {
            // Xử lý sự kiện cho Light 1
        } else if (v.getId() == R.id.lightSwitch2) {
            // Xử lý sự kiện cho Light 2
        } else if (v.getId() == R.id.lightSwitch3) {
            // Xử lý sự kiện cho Light 3
        }
    }
}
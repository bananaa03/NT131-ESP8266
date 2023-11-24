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

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView temperatureTextView;
    private DatabaseReference temperatureRef;
    private DatabaseReference humidityRef;
    private Button lightSwitch1;
    private Button lightSwitch2;
    private Button lightSwitch3;
    private void checkTemperature(float temperature) {
        if (temperature < 0 || temperature > 10) {
            sendEmailNotification();
        }
    }
    private void checkHumidity(float humidity) {
        if (humidity < 65 || humidity > 75) {
            sendEmailNotification();
        }
    }
    private void sendEmailNotification() {
        final String username = "your_email@gmail.com";
        final String password = "your_password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("recipient_email@example.com"));
            message.setSubject("Notification: Temperature and Humidity Alert");
            message.setText("The temperature and humidity are outside the specified range.");

            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

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
                float temperature = dataSnapshot.getValue(float.class);

                // Hiển thị nhiệt độ trong TextView
                temperatureTextView.setText((int)temperature);

                // Kiểm tra và gửi thông báo qua email
                checkTemperature(temperature);
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
                float humidity = dataSnapshot.getValue(float.class);

                // Hiển thị nhiệt độ trong TextView
                temperatureTextView.setText((int)humidity);

                // Kiểm tra và gửi thông báo qua email
                checkHumidity(humidity);
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
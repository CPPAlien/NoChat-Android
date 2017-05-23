package me.pengtao.nochat_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.pengtao.nochat_example.panel.ChatFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.chat_container, new ChatFragment()).commit();
    }
}

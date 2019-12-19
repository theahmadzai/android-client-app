package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Client client = new Client(this);

    private Button connectButton;
    private EditText ipAddressText;
    private EditText messageText;
    private Button sendButton;
    private TextView messagesView;
    private ListView sessionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton = (Button) findViewById(R.id.connectButton);
        ipAddressText = (EditText) findViewById(R.id.ipAddressText);
        messageText = (EditText) findViewById(R.id.messageText);
        sendButton = (Button) findViewById(R.id.sendButton);
        messagesView = (TextView) findViewById(R.id.messagesView);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.connect(ipAddressText.getText().toString(), 5959);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(messageText.getText().toString());
            }
        });
    }

    private void addUser(String userList) {

    }

    public void showMessage(String message) {
        messagesView.append(message + "\n");
    }

    private void sendMessage(String message) {
        if(!client.isConnected()) {
            Toast.makeText(this, "You are not connected!", Toast.LENGTH_SHORT).show();
            return;
        }

        client.textStreamOut(message);
        messageText.setText("");
    }

}

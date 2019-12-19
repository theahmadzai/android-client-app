package com.example.client;

import android.content.Context;
import android.widget.Toast;

import com.example.client.network.TextChannel;
import com.example.client.network.TextTransfer;

import java.io.*;
import java.net.*;

public class Client implements TextChannel {
    private Socket socket;
    private TextTransfer textTransfer;
    private MainActivity activity;

    public Client(MainActivity activity) {
        this.activity = activity;
    }

    public void connect(final String ip, final int port) {
        if (isConnected()) {
            Toast.makeText(activity, "Already connected!", Toast.LENGTH_SHORT).show();
            return;
        }

        final TextChannel textChannel = this;

        new Thread() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ip, port);
                    textTransfer = new TextTransfer(activity, socket);
                    textTransfer.addChannel(textChannel);

                    makeToast("client started on: " + socket);
                } catch (IOException ex) {
                    makeToast(ex.getMessage());
                }
            }
        }.start();
    }

    private void makeToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    @Override
    public void textStreamIn(String text) {
        if(text.startsWith("<ADD USERNAME>")) {
//            gui.addUser(text.substring("<SET USERNAME>".length()));
            return;
        }
        activity.showMessage(text);
    }

    @Override
    public void textStreamOut(String text) {
        textTransfer.sendText(text);
    }
}

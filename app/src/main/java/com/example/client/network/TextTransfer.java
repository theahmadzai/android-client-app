package com.example.client.network;

import com.example.client.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class TextTransfer {
    private PrintWriter textOutput;
    private BufferedReader textInput;
    private List<TextChannel> channels = new LinkedList<TextChannel>();

    public TextTransfer(final MainActivity activity, Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("Null socket given!");
        }

        textInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        textOutput = new PrintWriter(socket.getOutputStream(), true);

        new Thread() {
            @Override
            public void run() {
                String text;
                try {
                    while((text = textInput.readLine()) != null) {
                        for(final TextChannel channel : channels) {
                            final String tText = text;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    channel.textStreamIn(tText);
                                }
                            });
                        }
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    public void sendText(String text) {
        textOutput.println(text);
    }

    public void addChannel(TextChannel channel) {
        channels.add(channel);
    }

    public void removeChannel(TextChannel channel) {
        channels.remove(channel);
    }
}

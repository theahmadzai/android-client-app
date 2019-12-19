package com.example.client.network;

public interface TextChannel {
    void textStreamIn(String text);
    void textStreamOut(String text);
}
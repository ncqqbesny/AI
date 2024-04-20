package com.app.device.handler.wwj;

import java.io.*;
import java.net.Socket;

public class SocketClient {

    private final Socket socket;
    private final PrintWriter writer;

    public SocketClient(String host, int port) throws IOException {
        socket = new Socket("localhost", port);
        OutputStream os = socket.getOutputStream();
        writer = new PrintWriter(os, true);
    }

    public void sendData(String data) {
        writer.println(data);
    }

    public void close() throws IOException {
        writer.close();
        socket.close();
    }

}
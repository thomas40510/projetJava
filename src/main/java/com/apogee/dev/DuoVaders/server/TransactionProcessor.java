package com.apogee.dev.DuoVaders.server;

import com.apogee.dev.DuoVaders.Log;

import java.io.IOException;
import java.net.Socket;

public class TransactionProcessor extends Thread {
    private static final String TAG = "TransactionProcessor";
    private final Socket clientSocket;
    private final TCPServer server;

    public TransactionProcessor(Socket clientSocket, TCPServer server) {
        super("ServerThread");
        this.clientSocket = clientSocket;
        Log.i(TAG, "Client is " + clientSocket.getInetAddress().getHostName());
        this.server = server;
    }

    public void run() {
        try {
            this.server.getProtocol().execute(server.getContext(), clientSocket.getInputStream(), clientSocket.getOutputStream());
            Log.i(TAG, "Done.");
        } catch (IOException e) {
            Log.e(TAG, "Error while processing transaction: " + e.getMessage());
        }
    }
}

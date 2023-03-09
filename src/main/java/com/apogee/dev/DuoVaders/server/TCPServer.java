package com.apogee.dev.DuoVaders.server;

import com.apogee.dev.DuoVaders.client.Log;

import java.net.ServerSocket;
import java.net.*;
import java.io.*;

/**
 * Serveur TCP.
 * @version 1.0
 */
public class TCPServer extends Thread {
    private static int nbConnections = 0;
    private static final String TAG = "TCPServer";

    private int maxConnections;
    private Socket clientSocket;
    private IContext context;
    private IProtocol protocol;
    private int port;

    public TCPServer(int port) {
        this.port = port;
        maxConnections = 2;
    }
    public TCPServer(IContext context, IProtocol protocol, int port) {
        this(port);
        this.context = context;
        this.protocol = protocol;
    }

    public String toString() {
        return "TCP Server at port " + port + " with context " + context + " and protocol " + protocol + ".\n";
    }

    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            Log.e(TAG, "Could not listen on port: " + port);
            System.exit(1);
        }

        while(nbConnections <= maxConnections){
            try{
                Log.i(TAG, "Awaiting connection...");
                clientSocket = serverSocket.accept();
                nbConnections++;
                Log.i(TAG, "Connection established with " + clientSocket.getInetAddress().getHostName());
            } catch (IOException e) {
                Log.e(TAG, "Accept failed: " + port);
                System.exit(1);
            }
            TransactionProcessor tp = new TransactionProcessor(clientSocket, this);
            tp.start();
        }
        Log.i(TAG, "Reached maximum number of connections.");

        try {
            serverSocket.close();
            nbConnections--;
        } catch (IOException e) {
            Log.e(TAG, "Could not close socket");
            System.exit(1);
        }
    }

    public IProtocol getProtocol() {
        return protocol;
    }

    public IContext getContext() {
        return context;
    }
}

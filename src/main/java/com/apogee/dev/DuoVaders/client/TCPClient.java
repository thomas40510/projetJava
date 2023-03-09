package com.apogee.dev.DuoVaders.client;

import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client TCP.
 * @version 1.0
 */
public class TCPClient {
    static final String DEFAULT_HOST = "localhost";
    static final int DEFAULT_PORT = 5000;
    static final String TAG = "TCPClient";

    private final int portNumber;
    private final String hostName;
    private Socket serverSocket;
    private PrintStream socOut;
    private BufferedReader socIn;

    public TCPClient() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public TCPClient(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public boolean connectToServer(){
        boolean success = false;
        try {
            serverSocket = new Socket(hostName, portNumber);
            socOut = new PrintStream(serverSocket.getOutputStream());
            socIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            success = true;
        } catch (UnknownHostException e) {
            Log.e(TAG, "Don't know about host: " + hostName);
        } catch (ConnectException e) {
            Log.e(TAG, "Connection refused. You need to initiate a server first.");
        } catch (IOException e) {
            Log.e(TAG, "Couldn't get I/O for the connection to: " + hostName);
        }
        Log.i(TAG, "Connected to server: " + hostName + ":" + portNumber);
        return success;
    }

    public void disconnectFromServer(){
        try {
            socOut.close();
            socIn.close();
            serverSocket.close();
        } catch (Exception e) {
            Log.e(TAG, "Couldn't close the connection to: " + hostName);
        }
        Log.i(TAG, "Disconnected from server: " + hostName + ":" + portNumber);
    }

    public String sendString(String msg){
        String srvResponse = null;
        StringBuilder response = new StringBuilder();

        Log.i(TAG, "Sending message: " + msg);

        if(connectToServer()) {
            try {
                socOut.println(msg);
                while ((srvResponse = socIn.readLine()) != null) {
                    response.append(srvResponse).append("\n");
                }
                Log.i(TAG, "Received response: " + response);
                disconnectFromServer();
            } catch (Exception e) {
                Log.e(TAG, "Couldn't get I/O for the connection to: " + hostName);
            }
        } else {
            Log.e(TAG, "Couldn't connect to server: " + hostName + ":" + portNumber);
        }

        return response.toString();
    }

    public void sendKey(KeyCode key) {
        try{
            sendString(key.toString());
        } catch (Exception e) {
            Log.e(TAG, "Couldn't send key: " + key);
        }
    }
}

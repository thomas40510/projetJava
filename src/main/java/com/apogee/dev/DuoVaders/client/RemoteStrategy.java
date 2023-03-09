package com.apogee.dev.DuoVaders.client;

import javafx.scene.input.KeyCode;

public class RemoteStrategy implements StrategyHandler {
    private final Player player;
    private final TCPClient client;

    public void shoot() {
        // send key to server
        client.sendKey(KeyCode.SPACE);

    }
    public void move(char dir) {
        // send pressed key to server
        client.sendString(String.valueOf(dir));
    }

    public RemoteStrategy(Player player) {
        this.player = player;
        // initialize TCP connection
        client = new TCPClient();
        client.connectToServer();
    }

}

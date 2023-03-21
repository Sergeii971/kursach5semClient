package com.verbovskiy.client.connection;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerConnection {
    private final static ServerConnection instance = new ServerConnection();
    private final ThreadLocal<UserRequest> request;
    private final ThreadLocal<ServerResponse> response;
    private static Socket socket;
    private static final int PORT = 1280;
    private final Logger logger = LogManager.getLogger(ServerConnection.class);
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private ServerConnection() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), PORT);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            request = UserRequest.getInstance();
            response = ServerResponse.getInstance();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
        logger.log(Level.INFO,"Client connected to socket");
    }

    public static ServerConnection getInstance() {
        return instance;
    }

    public ThreadLocal<UserRequest> getRequest() {
        return request;
    }

    public ThreadLocal<ServerResponse> getResponse() {
        return response;
    }

    public void sendRequest() {
        try {
            response.get().clear();
            ThreadLocal<Session> session = Session.getInstance();
            request.get().setAllAttributes(session.get().getAttributes());
            Map<String, Object> request1 = new HashMap<>(request.get().getAttributes());
            out.flush();
            out.writeObject(request1);
            request.get().clear();
            response.get().setAllAttributes((Map<String, Object>) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.ERROR, "error while sending request to server");
        }
    }
}

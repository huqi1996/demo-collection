package com.huqi.qs.socket.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * @author huqi
 */
public class SocketServer {
    private ServerSocket serverSocket;

    public SocketServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(20000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                System.out.println("****************************************");
                System.out.println("waiting : " + serverSocket.getLocalPort() + "   " + serverSocket.getInetAddress().toString());
                Socket socket = serverSocket.accept();
                System.out.println("client address : " + socket.getRemoteSocketAddress() + "   " + socket.getInetAddress());
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                System.out.println(inputStream.readUTF());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.writeUTF("outPutStream : " + socket.getLocalPort() + "   " + socket.getInetAddress().toString());
                socket.close();
            } catch (SocketTimeoutException e) {
                System.out.println("socket timeout");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        SocketServer server = new SocketServer(6666);
        server.run();
    }
}

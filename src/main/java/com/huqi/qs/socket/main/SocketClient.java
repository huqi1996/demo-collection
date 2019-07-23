package com.huqi.qs.socket.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * @author huqi
 */
public class SocketClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 6666);
        System.out.println(socket.getInetAddress().toString() + "   " + socket.getLocalPort());
        System.out.println(socket.getRemoteSocketAddress());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        outputStream.writeUTF("client send message");
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        System.out.println("receive from server : " + inputStream.readUTF());
        socket.close();
    }
}

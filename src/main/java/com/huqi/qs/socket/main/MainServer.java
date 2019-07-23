package com.huqi.qs.socket.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author huqi 20190130
 */
public class MainServer {
    public static void main(String[] args) {
        try {
            //服务端在20006端口监听客户端请求的TCP连接
            ServerSocket server = new ServerSocket(20006);
            Socket client;
            while (true) {
                //等待客户端的连接，如果没有获取连接
                client = server.accept();
                System.out.println("与客户端连接成功！");
                //为每个客户端连接开启一个线程
                new Thread(new ServerThread(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

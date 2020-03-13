package com.xinchen.java.socket.simple;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/3/13 23:56
 */
public class GreetingServer extends Thread{

    private ServerSocket serverSocket;

    public GreetingServer(int port) throws IOException {
        // 端口范围： 0-65535 ， 当为0时随机分配
        serverSocket = new ServerSocket(port);
        // 单位milliseconds, 为0时不超时
        serverSocket.setSoTimeout(60000);
    }

    @Override
    public void run() {
        System.out.println(String.format("Waiting for connection, port: %d ...",serverSocket.getLocalPort()));
        while (true){
            try {
                // The method blocks until a connection is made
                Socket server = serverSocket.accept();

                System.out.println(String.format("connected remote address : %s ...",server.getRemoteSocketAddress()));
                DataInputStream in = new DataInputStream(server.getInputStream());
                if (in.available()>0){
                    System.out.println(in.readUTF());
                }

                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Hello World!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread server = new GreetingServer(8118);
        server.start();
        server.join();
    }
}

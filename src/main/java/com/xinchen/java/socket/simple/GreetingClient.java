package com.xinchen.java.socket.simple;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/3/13 23:56
 */
public class GreetingClient {

    private static final String HOST = "localhost";
    private static final Integer PORT = 8118;

    public static void main(String[] args) {
        try (Socket client = new Socket(HOST, PORT)) {
            System.out.println(String.format("connected to [%s]", client.getRemoteSocketAddress()));

            // 发送消息到服务器
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(String.format("Hello From [%s]", client.getLocalSocketAddress()));


            // 接收服务器返回消息
            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println(in.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

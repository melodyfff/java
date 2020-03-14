package com.xinchen.java.socket.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * 数据格式的规范：
 *
 *
 * <P>
 *
 *     响应消息:
 *     1. HTTP/1.1 200 OK                       状态行
 *     2. Content-Type:text/html;charset=utf-8  消息报头
 *     3.                                       空行
 *     4. <b>Hello World!<b>                    响应正文
 * </P>
 *
 *
 * 参考： [HTTP协议超级详解] https://www.cnblogs.com/an-wen/p/11180076.html
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/3/14 21:23
 */
public class SimpleServer extends Thread{

    private ServerSocket serverSocket;

    public SimpleServer() throws IOException {
        // 端口范围： 0-65535 ， 当为0时随机分配
        serverSocket = new ServerSocket(8888);
        // 单位milliseconds, 为0时不超时
        serverSocket.setSoTimeout(0);
    }

    @Override
    public void run() {
        System.out.println(String.format("server started, listen in port: %d ...",serverSocket.getLocalPort()));
        while (true){
            try (Socket server = serverSocket.accept()){
                // The method blocks until a connection is made

                try (PrintWriter printWriter = new PrintWriter(server.getOutputStream(), true)){
                    // 状态行
                    printWriter.println("HTTP/1.1 200 OK");
                    // 消息报头
                    printWriter.println("Content-Type:text/html;charset=utf-8");
                    // 空行
                    printWriter.println();
                    // 响应正文
                    printWriter.println("<html>");
//                    try (BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()))){
//                        String ok ;
//                        while ((ok=in.readLine())!=null){
//                            printWriter.println(ok+"</br>");
//                        }
//                    }

                    printWriter.println("<b>Hello World!<b>");
                    printWriter.println("</html>");
                    printWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new SimpleServer().start();
    }
}

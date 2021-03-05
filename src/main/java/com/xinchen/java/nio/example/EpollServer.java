package com.xinchen.java.nio.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * Selector I/O多路复用 服务端
 *
 * see: Java NIO(6): Selector - https://zhuanlan.zhihu.com/p/27434028
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2021/3/6 0:46
 */
public class EpollServer {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8000));
        ssc.configureBlocking(false);
        // register channel , bing event Accept
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer readBuf = ByteBuffer.allocate(1024);
        ByteBuffer writeBuf = ByteBuffer.allocate(128);
        writeBuf.put("received".getBytes());
        writeBuf.flip();

        for (;;){
            // blocking until select return
            final int nReady = selector.select();

            final Set<SelectionKey> keys = selector.selectedKeys();
            final Iterator<SelectionKey> keyIterator = keys.iterator();

            while (keyIterator.hasNext()){
                final SelectionKey key = keyIterator.next();
                keyIterator.remove();

                if (key.isAcceptable()){
                    // 如果其中channel 上有连接到达，就接受新的连接，然后把这个新的连接也注册到selector中去
                    final SocketChannel socketChannel = ssc.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()){
                    // 如果有channel是读，那就把数据读出来，并且把它感兴趣的事件改成写
                    final SocketChannel socketChannel = (SocketChannel) key.channel();
                    readBuf.clear();
                    socketChannel.read(readBuf);
                    readBuf.flip();
                    System.out.println("received : " + new String(readBuf.array()));

                    // interesting in write event.
                    key.interestOps(SelectionKey.OP_WRITE);
                } else if (key.isWritable()){
                    // 如果是写，就把数据写出去，并且把感兴趣的事件改成读
                    writeBuf.rewind();
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.write(writeBuf);

                    // interesting in read event.
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }
}

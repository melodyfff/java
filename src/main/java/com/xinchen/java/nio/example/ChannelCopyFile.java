package com.xinchen.java.nio.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 将数据从一个通道复制到另一个通道或从一个文件复制到另一个文件
 *
 * @author xinchen
 * @version 1.0
 * @date 27/04/2020 10:58
 */
public class ChannelCopyFile {

    private static void copyData(ReadableByteChannel src, WritableByteChannel dest) throws IOException {

        final ByteBuffer buffer = ByteBuffer.allocateDirect(2 * 1024);

        // Reads a sequence of bytes from this channel into the given buffer
        while (-1 != src.read(buffer)) {

            // 设置limit到当前的position,把当前position设置为0，忽略mark
            // 从写模式变成读模式
            buffer.flip();

            // 确保数据写完
            while (buffer.hasRemaining()) {
                dest.write(buffer);
            }

            // 设置position=0，limit = capacity，忽略mark
            buffer.clear();
        }

        System.out.println("Copy done!");
    }

    public static void main(String[] args) throws IOException {

        String sourcePath = "";
        String destPath = "";

        final FileInputStream in = new FileInputStream(sourcePath);
        final FileChannel src = in.getChannel();

        final FileOutputStream out = new FileOutputStream(destPath);
        final FileChannel dest = out.getChannel();

        // 拷贝
        try {
            copyData(src, dest);
        } finally {
            // 关闭连接
            // 关闭外层连接，里面的Channel会依次关闭
            in.close();
            out.close();
        }

    }
}

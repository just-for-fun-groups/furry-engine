package com.windrises.netty.nio;

import java.io.*;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Channel不传输数据,需配合缓冲区使用
 * <p>
 * 获取通道(jdk1.7之后)
 * 1.getChannel
 * 2.jdk1.7中的NIO.2针对各个通道提供了静态方法open();
 * 3.jdk1.7中的NIO.2的Files工具类的newByteChannel();
 * <p>
 * 通道之间的数据传输
 * transferForm()
 * transferTo()
 * <p>
 * 分散(Scatter)和聚集(Gather)
 *
 * @author liuhaozhen
 * @version Revision 1.0.0
 * @date 2019/12/3 16:21
 */
public class TestChannel {
    public static void main(String[] args) throws Exception {
        method5();
    }


    private static void method5() throws FileNotFoundException {
        SortedMap<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entries = map.entrySet();
        for (Map.Entry<String, Charset> entry : entries) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    private static void method4() throws FileNotFoundException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        //获取通道
        FileChannel channel = randomAccessFile.getChannel();
        //分配缓冲区大小
        ByteBuffer buffer1 = ByteBuffer.allocate(100);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);

    }

    /**
     * 通道之间的数据传输
     */
    private static void method3() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("C:\\work\\mycode\\windrises\\src\\main\\java\\com\\windrises\\netty\\nio\\1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("C:\\work\\mycode\\windrises\\src\\main\\java\\com\\windrises\\netty\\nio\\4.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

        inChannel.transferTo(0, inChannel.size(), outChannel);
        inChannel.close();
        outChannel.close();
    }


    /**
     * 2.使用直接缓冲区完成文件的复制(内存映射文件)
     */
    private static void method() {
        try {
            FileChannel inChannel = FileChannel.open(Paths.get("C:\\work\\mycode\\windrises\\src\\main\\java\\com\\windrises\\netty\\nio\\1.jpg"), StandardOpenOption.READ);
            FileChannel outChannel = FileChannel.open(Paths.get("C:\\work\\mycode\\windrises\\src\\main\\java\\com\\windrises\\netty\\nio\\3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

            //内存映射文件
            MappedByteBuffer inMappedByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMappedByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
            //直接对缓冲区做数据的读写操作
            byte[] bytes = new byte[inMappedByteBuffer.limit()];
            inMappedByteBuffer.get(bytes);
            outMappedByteBuffer.put(bytes);

            inChannel.close();
            outChannel.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1
     *
     * @throws FileNotFoundException
     */
    private static void method2() throws Exception {
        FileInputStream fis = new FileInputStream("C:\\work\\mycode\\windrises\\src\\main\\java\\com\\windrises\\netty\\nio\\1.jpg");
        FileOutputStream fos = new FileOutputStream("C:\\work\\mycode\\windrises\\src\\main\\java\\com\\windrises\\netty\\nio\\2.jpg");
        //获取通道
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();
        //分配缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将通道的数据存入缓冲区
        while (inChannel.read(byteBuffer) != -1) {
            //切换读取数据的模式
            byteBuffer.flip();
            //将缓冲区的数据写入通道
            outChannel.write(byteBuffer);
            //清空缓冲区
            byteBuffer.clear();
        }
        outChannel.close();
        inChannel.close();
        fis.close();
        fos.close();
    }
}

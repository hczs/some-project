package icu.sunnyc.client;

import icu.sunnyc.common.AppUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author hc
 * @date Created in 2022/7/6 23:30
 * @modified
 */
public class EchoClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 12345);
        // 连接不会阻塞
        if (!socketChannel.connect(socketAddress)) {
            // 如果没连上还可以做其他事情
            while (!socketChannel.finishConnect()) {
                System.out.println("正在连接服务器...");
            }
        }
        Selector selector = Selector.open();
        // 先监听读取事件 读取服务端欢迎语
        socketChannel.register(selector, SelectionKey.OP_READ);
        Scanner scanner = new Scanner(System.in);
        while (selector.isOpen() && 0 != selector.select()) {
            try {
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    // 接收服务端返回的消息
                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        String receiveMsg = AppUtil.readMsgFromChannel(channel);
                        if (receiveMsg == null) {
                            channel.close();
                        } else {
                            System.out.println(receiveMsg);
                            // 读完之后添加监听写事件
                            selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
                        }
                    }
                    // 向服务端发送消息
                    if (selectionKey.isWritable()) {
                        System.out.print("Enter: ");
                        String msg = scanner.nextLine();
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        channel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                        // 写完之后监听读事件 取消 write 监听
                        selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE);
                    }
                    // 移除已经遍历处理过的key
                    keyIterator.remove();
                }
            } catch (Exception e) {
                System.out.println("Disconnected from server");
                break;
            }
        }
    }
}

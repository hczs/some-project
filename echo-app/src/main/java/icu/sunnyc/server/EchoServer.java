package icu.sunnyc.server;

import icu.sunnyc.common.AppConstants;
import icu.sunnyc.common.AppUtil;
import icu.sunnyc.entity.ClientBO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hc
 * @date Created in 2022/7/5 21:42
 * @modified
 */
public class EchoServer {

    /**
     * 服务监听端口
     */
    private final Integer listenPort;

    private Selector selector;

    /**
     * 计数器 给连接的客户端计数
     */
    private final AtomicInteger counter = new AtomicInteger();

    /**
     * 存储客户端连接对象列表
     */
    private final List<ClientBO> clients = new CopyOnWriteArrayList<>();

    /**
     * 服务监听线程
     */
    private Thread echoServerThread;

    public EchoServer(Integer listenPort) {
        this.listenPort = listenPort;
    }

    /**
     * 服务端启动
     */
    public void startService() throws IOException {
        // 启动服务器
        echoServerThread = new Thread(this::run, "EchoServerThread");
        echoServerThread.start();
        // 主线程进行打印管理端
        Scanner scanner = new Scanner(System.in);
        // 打印欢迎语
        System.out.println(AppUtil.getWelcomeString());
        while (AppConstants.runFlag) {
            System.out.print("Enter(h for help): ");
            String cmd = scanner.nextLine();
            processCmd(cmd);
        }
    }

    /**
     * Echo 服务器启动
     */
    public void run() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open() ) {
            // 绑定服务监听端口
            serverSocketChannel.bind(new InetSocketAddress(listenPort));
            this.selector = Selector.open();
            // 配置为非阻塞
            serverSocketChannel.configureBlocking(false);
            // 注册 selector 并监听连接事件
            serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
            // 循环等待客户端连接
            while (AppConstants.runFlag && selector.isOpen() && 0 != this.selector.select() && !Thread.currentThread().isInterrupted()) {
                Iterator<SelectionKey> keyIterator = this.selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    // 依次处理
                    handle(keyIterator.next());
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            System.out.println();
            System.out.println("异常信息：" + e.getMessage());
        }
    }

    /**
     * 处理客户端的连接
     * @param selectionKey SelectionKey
     * @throws IOException
     */
    private void handle(SelectionKey selectionKey) throws IOException {
        try {
            // 如果是客户端连接事件，把这个连接的 channel 放到 selector 管理
            if (selectionKey.isAcceptable()) {
                handleAccept(selectionKey);
            }
            // 如果是可读的，就读取内容，并且把内容存入附加对象中
            if (selectionKey.isReadable()) {
                handleRead(selectionKey);
            }
            // 如果可写，把上次附加的写内容发回去就行
            if (selectionKey.isWritable()) {
                handleWrite(selectionKey);
            }
        } catch (IOException e) {
            System.out.println();
            System.out.println("异常信息：" + e.getMessage());
            selectionKey.channel().close();
        }
    }

    /**
     * 处理客户端连接事件
     * @param selectionKey selectionKey
     * @throws IOException
     */
    private void handleAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        // channel 必须得设置为非阻塞才能向 selector 注册
        socketChannel.configureBlocking(false);
        // 将客户端连接对象添加到已连接的客户端列表中
        ClientBO clientBO = new ClientBO(counter.incrementAndGet(), socketChannel.getRemoteAddress().toString().replace("/", ""),
                AppUtil.getNowDateTime(), socketChannel);
        clients.add(clientBO);
        // 监听读这个 socketChannel 的写事件，附加上欢迎语
        socketChannel.register(this.selector, SelectionKey.OP_WRITE, AppUtil.getWelcomeString());
    }

    /**
     * 处理读事件
     * @param selectionKey selectionKey
     * @throws IOException
     */
    private void handleRead(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        String msg = AppUtil.readMsgFromChannel(channel);
        if (msg == null) {
            // 与客户端断开连接
            disConnect(channel);
        } else {
            // 其他情况继续监听
            channel.register(this.selector, SelectionKey.OP_WRITE, msg);
        }
    }

    /**
     * 处理写事件
     * @param selectionKey SelectionKey
     * @throws IOException
     */
    private void handleWrite(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        String returnMsg = (String) selectionKey.attachment();
        // 如果消息内容是 bye! 返回 Bye bye! 并关闭连接
        if (AppConstants.CLIENT_QUIT.equals(returnMsg)) {
            channel.write(ByteBuffer.wrap(AppUtil.wrapDatetime(AppConstants.SERVER_BYE_CLIENT).getBytes(StandardCharsets.UTF_8)));
            // 与客户端断开连接
            disConnect(channel);
        } else {
            // 正常情况下 echo 回去输入的信息并继续监听读取事件
            channel.write(ByteBuffer.wrap(AppUtil.wrapDatetime(returnMsg).getBytes(StandardCharsets.UTF_8)));
            channel.register(this.selector, SelectionKey.OP_READ);
        }
    }

    /**
     * 处理输入的命令
     * @param cmd 命令内容
     */
    private void processCmd(String cmd) throws IOException {
        cmd = cmd.trim();
        String[] args = cmd.split(" ");
        // 最多允许输入两个参数
        if (args.length > 2 ) {
            printHelp();
            return;
        }
        switch (args[0]) {
            case AppConstants.QUERY_CONNECTIONS:
                queryConnections();
                break;
            case AppConstants.DISCONNECT:
                disConnect(args);
                break;
            case AppConstants.QUIT:
                exitServer();
                break;
            default:
                printHelp();
        }
    }

    /**
     * 打印使用说明
     */
    private void printHelp() {
        System.out.println("The commands:");
        System.out.println("----------------------------------------------------");
        System.out.println("   q        query current connections");
        System.out.println("   d id        disconnect client");
        System.out.println("   x        quit server");
        System.out.println("   h        help");
    }

    /**
     * 打印当前已连接的客户端
     */
    private void queryConnections() {
        System.out.println("Id    Client            LoginTime");
        System.out.println("----------------------------------------------------");
        clients.forEach(System.out::println);
    }

    /**
     * 断开与客户端的连接
     * @param args 命令行参数 格式：d  clientId
     */
    private void disConnect(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Please enter one and only one client ID");
            return;
        }
        int clientId = AppUtil.parseInt(args[1]);
        ClientBO client = clients.stream()
                .filter(clientBO -> clientBO.getId() == clientId)
                .findFirst().orElse(null);
        if (client == null) {
            System.out.println("The client ID you entered does not exist");
        } else {
            client.getSocketChannel().close();
            clients.remove(client);
            System.out.println(AppUtil.wrapDatetimeOnly("The connection '" + client.getAddress() + "' has been disconnected."));
        }
    }

    /**
     * 断开与指定 channel 的客户端连接
     * @param channel SocketChannel
     * @throws IOException
     */
    private void disConnect(SocketChannel channel) throws IOException {
        String clientAddress = channel.getRemoteAddress().toString().replace("/", "");
        clients.stream().filter(client -> client.getAddress().equals(clientAddress)).findFirst().ifPresent(e -> {
            try {
                e.getSocketChannel().close();
                clients.remove(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * 关闭服务器
     */
    private void exitServer() {
        // 中断服务器线程
        echoServerThread.interrupt();
        // 关闭主线程
        AppConstants.runFlag = false;
        // 打印结束语
        System.out.println(AppUtil.wrapDatetimeOnly("The server has exited. Bye!"));
    }
}

package icu.sunnyc;


import icu.sunnyc.server.EchoServer;

import java.io.IOException;

/**
 * @author hc
 * @date Created in 2022/7/5 21:42
 * @modified
 */
public class EchoApplication {

    public static void main(String[] args) throws IOException {

        final int listenPort = 12345;

        // 启动服务端
        EchoServer server = new EchoServer(listenPort);
        server.startService();

    }
}

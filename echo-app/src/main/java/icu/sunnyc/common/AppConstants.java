package icu.sunnyc.common;

/**
 * 应用常量类
 * @author hc
 * @date Created in 2022/7/6 21:45
 * @modified
 */
public class AppConstants {

    /**
     * 查询所有连接命令
     */
    public static final String QUERY_CONNECTIONS = "q";

    /**
     * 断开指定客户端连接命令
     */
    public static final String DISCONNECT = "d";

    /**
     * 退出服务命令
     */
    public static final String QUIT = "x";

    /**
     * 打印帮助信息命令
     */
    public static final String HELP = "h";

    /**
     * 客户端断开连接消息
     */
    public static final String CLIENT_QUIT = "bye!";

    /**
     * 服务端收到客户端断开连接的消息后的回复语
     */
    public static final String SERVER_BYE_CLIENT = "Bye bye!";

    /**
     * 服务端运行标志
     */
    public static boolean runFlag = true;

    /**
     * 欢迎语
     */
    public static final String WELCOME_SERVER = "Welcome to My Echo Server.";

}

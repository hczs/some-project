package icu.sunnyc.common;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 项目工具类
 * 存储一些常用公共方法
 * @author hc
 * @date Created in 2022/7/5 21:53
 * @modified
 */
public class AppUtil {

    /**
     * 时间格式化方式 带毫秒值
     */
    private static final String DATE_FORMAT_PATTERN_MS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 时间格式化方式 不带毫秒值
     */
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private AppUtil() {}

    /**
     * 返回格式化后的当前时间
     * 格式 yyyy-MM-dd HH:mm:ss.SSS
     * @return 例如：2022-07-05 21:53:26.913
     */
    public static String getNowDateTimeWithMs() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN_MS));
    }

    public static String getNowDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
    }

    /**
     * 获取欢迎语
     * @return 例如 2020-03-31 16:58:44.049 - Welcome to My Echo Server.(from SERVER)
     */
    public static String getWelcomeString() {
        return getNowDateTimeWithMs() + " - Welcome to My Echo Server.(from SERVER)";
    }

    /**
     * 为提示信息添加上时间 和 (from SERVER) 后缀
     * @param msg 消息内容
     * @return 例如 2020-03-31 16:58:55.452 - hello!(from SERVER)
     */
    public static String wrapDatetime(String msg) {
        return getNowDateTimeWithMs() + " - " + msg + "(from SERVER)";
    }

    /**
     * 仅仅为提示信息添加上时间 不带 (from SERVER) 后缀
     * @param msg 信息内容
     * @return 例如：2020-03-31 16:58:44.049 - The connection '127.0.0.1:32328' has been disconnected.
     */
    public static String wrapDatetimeOnly(String msg) {
        return getNowDateTimeWithMs() + " - " + msg;
    }

    /**
     * 读取 channel 中的数据为字符串 编码格式 UTF-8
     * @param channel SocketChannel
     * @return 字符串消息 如果 SocketChannel 连接断开了，返回 null
     */
    public static String readMsgFromChannel(SocketChannel channel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int length = channel.read(byteBuffer);
        // -1 代表连接已经断开了
        if (length == -1) {
            return null;
        }
        StringBuilder returnMsg = new StringBuilder();
        while (length > 0) {
            returnMsg.append(new String(byteBuffer.array(), 0, length));
            length = channel.read(byteBuffer);
        }
        return returnMsg.toString();
    }

    /**
     * 解析字符串为整型
     * @param value 字符串
     * @return 整数 解析失败返回 -1
     */
    public static int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

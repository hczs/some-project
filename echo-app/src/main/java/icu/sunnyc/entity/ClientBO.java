package icu.sunnyc.entity;

import java.nio.channels.SocketChannel;

/**
 * 存储客户端连接对象
 * @author hc
 * @date Created in 2022/7/7 22:47
 * @modified
 */
public class ClientBO {

    /**
     * 客户端 id
     */
    private Integer id;

    /**
     * 客户端连接地址
     */
    private String address;

    /**
     * 登录时间
     */
    private String loginTime;

    /**
     * 连接的 SocketChannel
     */
    private SocketChannel socketChannel;

    public ClientBO(Integer id, String address, String loginTime, SocketChannel socketChannel) {
        this.id = id;
        this.address = address;
        this.loginTime = loginTime;
        this.socketChannel = socketChannel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public String toString() {
        return id + "\t" + address + "\t\t" + loginTime;
    }
}

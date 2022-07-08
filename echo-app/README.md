```java
/**
 * 实现要求：
 * 1、根据代码片段实现一个简单的SOCKET ECHO程序；
 * 2、接受到客户端连接后，服务端返回一个欢迎消息;
 * 3、接受到"bye"消息后， 服务端返回一个结束消息，并结束当前连接;
 * 4、采用telnet作为客户端，通过telnet连接本服务端；
 * 5、服务端支持接受多个telnet客户端连接;
 * 6、服务端支持命令操作，支持查看当前连接数、断开指定客户端连接；
 * 7、注意代码注释书写。
 *
 */
public class EchoApplication {

	public static void main(String[] args) throws IOException, InterruptedException {

		final int listenPort = 12345;

		// 启动服务端
		EchoServer server = new EchoServer(listenPort);
		server.startService();

		// 服务端启动后，运行结果示例：
		/**
			java -cp ./classes EchoApplication

			2020-03-31 16:58:44.049 - Welcome to My Echo Server.(from SERVER)
			The current connections:
			Id.			Client				LogonTime
			-----------------------------------------------------
			1			127.0.0.1:32328		2020-03-31 16:59:13
			2			127.0.0.1:43434		2020-03-31 17:03:02
			3			127.0.0.1:39823		2020-03-31 07:03:48

			Enter(h for help): h
			The commands:
			----------------------------------------------------
			   q		query current connections
			   d id		disconnect client
			   x		quit server
			   h		help

			Enter(h for help): d 1
			2020-03-31 16:58:44.049 - The connection '127.0.0.1:32328' has been disconnected.
			The current connections:
			Id.			Client				LogonTime
			-----------------------------------------------------
			1			127.0.0.1:43434		2020-03-31 17:03:02
			2			127.0.0.1:39823		2020-03-31 07:03:48

			Enter(h for help): x
			2020-03-31 16:58:44.049 - The server has exited. Bye!
		 */

		// 在telnet控制台输入，服务端直接原文返回输入信息
		// 客户端结果示例：
		/**
			2020-03-31 16:58:44.049 - Welcome to My Echo Server.(from SERVER)

			Enter: hello!
			2020-03-31 16:58:55.452 - hello!(from SERVER)

			Enter: This is KOAL.
			2020-03-31 16:59:06.565 - This is KOAL.(from SERVER)

			Enter: What can i do for you?
			2020-03-31 16:59:12.828 - What can i do for you?(from SERVER)

			Enter: bye!
			2020-03-31 16:59:16.502 - Bye bye!(from SERVER)
		 */
	}

}

class EchoServer {
	// TODO
}
```

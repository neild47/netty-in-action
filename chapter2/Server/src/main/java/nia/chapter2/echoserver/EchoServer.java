package nia.chapter2.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Listing 2.2 EchoServer class
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args)
            throws Exception {
        int port = 8000;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();// 负责进行事件处理
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)// 指定所使用的NIO传输所使用的Channel
                    .localAddress(new InetSocketAddress(port))// 使用指定的端口设定套接字地址
                    // 当一个新的连接被接受时,会创建新的Channel实例
                    // 注意Channel描述的是一个连接,pipeline也是channel中的属性
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 添加一个ChannelHandler到ChannelPipeline
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // serverHandler的类被标记为@Shared,意味着我们可以使用同样的实例
                            // 在这里所有的客户端都使用同一个实例
                            // ChannelInitializer负责将serverHandler添加到channel的pipeline中
                            ch.pipeline().addLast(serverHandler);
                        }
                    });

            //sync作用 阻塞等待ChannelFuture它完成
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName() +
                    " started and listening for connections on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}

package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class DoubleToIntEncoder extends MessageToMessageEncoder<ByteBuf>{

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        while (msg.readableBytes() >= 8) {
            double v = msg.readDouble();
            out.add((int) v);
        }
    }
}

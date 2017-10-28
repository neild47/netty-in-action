package test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import nia.chapter10.ToIntegerDecoder;
import org.junit.Test;

import static org.junit.Assert.*;

public class ToIntegerDecoderTest {
    @Test
    public void test() {
        ByteBuf buffer = Unpooled.buffer();
        for (int i = 1; i < 11; i++) {
            buffer.writeInt(i);
        }
        EmbeddedChannel channel = new EmbeddedChannel(new ToIntegerDecoder());
        channel.writeInbound(buffer);
        for (int i = 1; i < 11; i++) {
            assertTrue(channel.readInbound().equals(i));
        }
    }
}

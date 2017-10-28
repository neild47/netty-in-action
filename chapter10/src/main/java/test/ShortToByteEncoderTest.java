package test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import nia.chapter10.ShortToByteEncoder;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShortToByteEncoderTest {
    @Test
    public void test() {
        ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            buffer.writeShort(i);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new ShortToByteEncoder());

        channel.writeOutbound(buffer);

        ByteBuf buf = channel.readOutbound();

        while (buf.readableBytes() > 0) {
            System.out.println(buf.readShort());
        }
    }
}

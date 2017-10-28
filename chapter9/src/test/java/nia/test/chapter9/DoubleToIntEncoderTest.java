package nia.test.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import nia.chapter9.DoubleToIntEncoder;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class DoubleToIntEncoderTest {
    @Test
    public void test() {
        ByteBuf buffer = Unpooled.buffer();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            buffer.writeDouble(random.nextDouble() * 100);
        }
        EmbeddedChannel channel = new EmbeddedChannel(new DoubleToIntEncoder());
        channel.writeOutbound(buffer.duplicate().retain());
        for (int i = 0; i < 2; i++) {
            assertTrue(channel.readOutbound().equals((int) buffer.readDouble()));
        }
    }
}

package me.sunlan.fw;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PatternFilterPrintStreamTest {
    @Test
    public void write() throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream ps = new PrintStream(baos);
             PatternFilterPrintStream pfps = new PatternFilterPrintStream(ps, "(?s)^WARNING:.*$")) {

            pfps.write("foo\n".getBytes());
            pfps.write("WARNING: xxx\n".getBytes());
            pfps.write("bar\n".getBytes());

            Assert.assertEquals("foo\nbar\n", new String(baos.toByteArray()));

            pfps.write("foo2\r\n".getBytes());
            pfps.write("WARNING: xxx\r\n".getBytes());
            pfps.write("bar2\r\n".getBytes());

            Assert.assertEquals("foo\nbar\nfoo2\r\nbar2\r\n", new String(baos.toByteArray()));
        }
    }
}

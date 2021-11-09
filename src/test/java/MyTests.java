import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MyTests {


    @Test
    public void ListenerTest() throws IOException, InterruptedException {
        // given:
        Socket socket = Mockito.mock(Socket.class);
        ByteArrayInputStream myInputStream = new ByteArrayInputStream(("Hi!").getBytes(StandardCharsets.UTF_8));
        Mockito.when(socket.getInputStream())
                .thenReturn(myInputStream);
        ByteArrayOutputStream myOutputStream = new ByteArrayOutputStream();
        Mockito.when(socket.getOutputStream())
                .thenReturn(myOutputStream);
        String name = "Tony";
        Writer writer = new Writer(name, socket);
        writer.start();
        Listener listener = new Listener(socket, writer);
        listener.start();
        Thread.sleep(500);
        writer.interrupt();
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Listener extends Thread {

    private Socket socket;
    private BufferedReader inMessage;
    private Thread writer;

    public Listener(Socket socket, Thread writer) throws IOException {
        this.socket = socket;
        this.writer = writer;
        inMessage = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    @Override
    public void run() {
        String msg;
        Logger logger = Logger.getInstance();
        while (writer.isAlive()) {
            try {
                msg = inMessage.readLine();
                printmsg(msg, logger);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printmsg(String msg, Logger logger) {
        System.out.println(msg);
        if (msg.equals("Bye!")) {
            logger.log("Disconnect");
        } else {
            logger.log(msg);
        }
    }
}

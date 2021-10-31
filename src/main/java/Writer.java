import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Writer extends Thread {

    private String name = "";
    private Socket socket;
    private PrintWriter outMessage;

    public Writer(String name, Socket socket) throws IOException {
        this.name = name;
        this.socket = socket;
        outMessage = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
    }

    @Override
    public void run() {
        Logger logger = Logger.getInstance();
        sendMsg("/start");
        logger.log("Connect");
        boolean work = true;
        String msg;
        Scanner scanner = new Scanner(System.in);
        while (work) {
            msg = scanner.nextLine();
            sendMsg(msg);
            logger.log("Me: " + msg);
            if (msg.equals("/exit")) {
                work = false;
            }
        }
        interrupt();
    }
    private void sendMsg(String messageStr) {
        outMessage.println(name + ": " + messageStr);
    }
}

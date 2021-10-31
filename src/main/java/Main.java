import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    static final String PATH_TO_SETTINGS = "settings.txt";
    static String host;
    static int port;

    public static void main(String[] args) throws IOException, InterruptedException {
        getHostAndPort();
        String name = getName();
        Socket socket = new Socket(host, port);
        Writer writer = new Writer(name, socket);
        writer.start();
        Listener listener = new Listener(socket, writer);
        listener.start();
    }

    private static String getName() {
        System.out.println("Input your name");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    private static void getHostAndPort() {
        File file = new File(PATH_TO_SETTINGS);
        host = "";
        String portStr = "";
        boolean needSave = false;
        if (file.exists()) {
            String data = WorkWithFiles.readString(PATH_TO_SETTINGS);
            String[] fileData = data.split(":");
            if (fileData.length == 2) {
                host = fileData[0];
                portStr = fileData[1];
                if (!portStr.matches("[-+]?\\d+")) {
                    portStr = "";
                } else {
                    port = Integer.parseInt(portStr);
                }
            }
        }
        Scanner sc = new Scanner(System.in);
        if (host.equals("")) {
            needSave = true;
            System.out.println("Can't find or read host. Please enter: ");
            host = sc.nextLine();
        }
        if (portStr.equals("")) {
            needSave = true;
            System.out.println("Can't find or read port. Please enter: ");
            while (true) {
                portStr = sc.nextLine();
                if (portStr.matches("[-+]?\\d+")) {
                    port = Integer.parseInt(portStr);
                    break;
                }
                System.out.println("Try again");
            }
        }
        if (needSave) {
            WorkWithFiles.writeString(host + ":" + portStr, PATH_TO_SETTINGS);
        }
    }
}

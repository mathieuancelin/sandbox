package injector;

import java.net.InetAddress;
import java.net.Socket;

public class Injector {
    public static final String BEGIN = "{firstname:'maurice',lastname:'plutanfiard',mail:'maurice.plutanfiard";
    public static final String END = "@gmail.com',password:'xxxxx'}";
    public static final String CONTENT_TYPE = "Content-Type: application/json\r\n\r\n";
    public static final String POST = "POST ";
    public static final String LENGTH = " HTTP/1.0\r\nHost: localhost\r\nContent-Length: ";
    public static final String JUMP = "\r\n";
    public static final String HOSTNAME = "localhost";
    public static final String URI = "/api/user";
    public static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            InetAddress addr = InetAddress.getByName(HOSTNAME);
            Socket socket;
            StringBuilder builder;
            for (int i = 0; i < 1000; i++) {
                socket = new Socket(addr, PORT);
                builder = new StringBuilder();
                builder.append(POST);
                builder.append(URI);
                builder.append(LENGTH);
                builder.append(BEGIN.length() + END.length() + String.valueOf(i).length() + 4 );
                builder.append(JUMP);
                builder.append(CONTENT_TYPE);
                builder.append(JUMP);
                builder.append(JUMP);
                builder.append(BEGIN);
                builder.append(String.valueOf(i));
                builder.append(END);
                socket.getOutputStream().write(builder.toString().getBytes());
                socket.getOutputStream().flush();
                socket.getInputStream().close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package injector;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class Injector {

    public static void main(String[] args) {
        try {
            String data;
            URL url = new URL("http://127.0.0.1:8080/api/user");
            URLConnection conn;
            OutputStreamWriter wr;
            for (int i = 0; i < 100000; i++) {
                data = "{firstname:'maurice',lastname:'plutanfiard',mail:'maurice.plutanfiard" + i + "@gmail.com',password:'xxxxx'}";
                conn = url.openConnection();
                conn.setDoOutput(true);
                wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data + "\r\n\r\n");
                wr.flush();
                wr.close();
                conn.getInputStream().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {

    public void send(Socket socket, String txt) {
        OutputStream out;
        try {
            out = socket.getOutputStream();
            out.write(txt.getBytes());
        } catch (Exception e) {
            System.out.println("Exceção no OutputStream");

        }
    }

    public String receive(Socket socket) {
        InputStream in;
        int bt;
        byte btxt[];
        String txt = "";
        btxt = new byte[79];
        try {
            in = socket.getInputStream();
            bt = in.read(btxt);
            if (bt > 0)
                txt = new String(btxt);
        } catch (Exception e) {
            System.out.println("Excecao no InputStream: " + e);
        }
        return txt;
    }

}
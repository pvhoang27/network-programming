import java.io.*;
import java.net.*;
import java.util.*;

public class Q7SDdTfg {
    public static void main(String[] args) {
        String msv = "B21DCCN393";
        String qCode = "Q7SDdTfg";
        String host = "203.162.10.109";
        int port = 2208;

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(5000);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String req = msv + ";" + qCode;
            bw.write(req + "\n"); bw.flush();
            System.out.println( req);

            String s = br.readLine();
            System.out.println(s);

            String res = handle(s);
            System.out.println( res);

            bw.write(res + "\n"); bw.flush();
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static String handle(String s) {
        StringBuilder r = new StringBuilder();
        HashSet<Character> set = new HashSet<>();
        for (char c : s.toCharArray())
            if (Character.isLetter(c) && set.add(c))
                r.append(c);
        return r.toString();
    }
}

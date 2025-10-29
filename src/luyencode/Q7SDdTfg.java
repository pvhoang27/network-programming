import java.io.*;
import java.net.*;

public class Q7SDdTfg {
    public static void main(String[] args) throws Exception {
        String host = "203.162.10.109";
        int port = 2208;
        String studentCode = "B21DCCN393"; 
        String qCode = "Q7SDdTfg";

        Socket socket = new Socket(host, port);
        socket.setSoTimeout(5000);

        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader br = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        // gui msv + qcode 
        bw.write(studentCode + ";" + qCode);
        bw.newLine();
        bw.flush();

        // du lieu nhan ve
        String input = br.readLine();
        System.out.println( input);
        
        // xu ly de bai
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) { // giữ lại chữ cái
                if (result.indexOf(String.valueOf(c)) == -1)
                    result.append(c);
            }
        }
        
        String output = result.toString();
        System.out.println( output);

        //  Gửi chuỗi kết quả lên server
        bw.write(output);
        bw.newLine();
        bw.flush();

//        bw.close();
//        br.close();
//        socket.close();
    }
}

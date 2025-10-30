//[Mã câu hỏi (qCode): Q7SDdTfg].  [Loại bỏ ký tự đặc biệt, trùng và giữ nguyên thứ tự xuất hiện] Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản dưới đây:
//a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;7D6265E3"
//b.	Nhận một chuỗi ngẫu nhiên từ server
//c.	Loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của ký tự. Gửi chuỗi đã được xử lý lên server.
//d.	Đóng kết nối và kết thúc chương trình
package luyencode;
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

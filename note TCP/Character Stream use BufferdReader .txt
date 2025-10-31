//[Mã câu hỏi (qCode): Q7SDdTfg].  [Loại bỏ ký tự đặc biệt, trùng và giữ nguyên thứ tự xuất hiện] Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản dưới đây:
//a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;7D6265E3"
//b.	Nhận một chuỗi ngẫu nhiên từ server
//c.	Loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của ký tự. Gửi chuỗi đã được xử lý lên server.
//d.	Đóng kết nối và kết thúc chương trình

package tcp_code.CharacterStream_use_BufferedReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashSet;

public class Q7SDdTfg {
    //ham xu ly de bai
    static String handle(String s){
        StringBuilder r = new StringBuilder();
        HashSet<Character> set =  new HashSet<>();
        for(char c : s.toCharArray()){
            if(Character.isLetter(c ) && set.add(c)){
                r.append(c);
            }
        }
        return r.toString();
    }
    public static void main(String[] args) throws Exception{
        // khai bao socket va luong
        Socket socket = new Socket("203.162.10.109",2208);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //gui msv + qcode len sv
        String request = "B21DCCN393;Q7SDdTfg";
        bw.write(request);
        bw.newLine();
        bw.flush();
        //nhan du lieu tu sv
        String s = br.readLine();
        // in du lieu do ra de nhin cho de
        System.out.println(s);
        //xu ly de bai 
        String res = handle(s);
        //in ket qua de bai ra nhin cho de
        System.out.println(res);
        //gui lai ket qua cho sv
        bw.write(res);
        bw.newLine();
        bw.flush();
    }
}

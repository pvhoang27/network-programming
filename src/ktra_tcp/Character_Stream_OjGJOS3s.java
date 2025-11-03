/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): OjGJOS3s].  [Loại bỏ ký tự đặc biệt, trùng và giữ nguyên thứ tự xuất hiện] Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản dưới đây:
//a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;7D6265E3"
//b.	Nhận một chuỗi ngẫu nhiên từ server
//c.	Loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của ký tự. Gửi chuỗi đã được xử lý lên server.
//d.	Đóng kết nối và kết thúc chương trình
package ktra_tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Set;
import java.util.Set;
import java.util.LinkedHashSet;

/**
 *
 * @author hoang
 */
public class Character_Stream_OjGJOS3s {
    public static void main(String[] args) throws Exception{
        // khai bao socket va luong 
        Socket socket = new Socket("203.162.10.109",2206);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader br  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // day msv + qcode len sv
        String request  ="B21DCCN393;OjGJOS3s";
        bw.write(request);
        bw.newLine();
        bw.flush();
        //nhan data từ sv về 
        String data = br.readLine();
        // in data từ sv về cho dễ nhìn 
        System.out.println(data);
        // xử lý bài toán 
        
//        for (int i = 0; i < data.length(); i++) {
//            char ch = data.charAt(i);
//            if (Character.isLetter(ch)) { // chỉ giữ chữ cái
//                if (result.indexOf(ch) == -1) { // chưa xuất hiện trong result
//                    result += ch; // nối thêm ký tự
//                }
//            }
//        } 
        //code này dễ hiểu hơn khi ta dùng set
        Set<Character> seen = new LinkedHashSet<>();
        String result = "";
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            if (Character.isLetter(c) && !seen.contains(c)) {
                seen.add(c);
                result += c; // nối chuỗi trực tiếp
            }
        }
        System.out.println(request);
        
        //gui len sv
        bw.write(result);
        bw.newLine();
        bw.flush();

    }
}

//[Mã câu hỏi (qCode): OjGJOS3s].  [Loại bỏ ký tự đặc biệt, trùng và giữ nguyên thứ tự xuất hiện] Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản dưới đây:
//a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;7D6265E3"
//b.	Nhận một chuỗi ngẫu nhiên từ server
//c.	Loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của ký tự. Gửi chuỗi đã được xử lý lên server.
//d.	Đóng kết nối và kết thúc chương trình

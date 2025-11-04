/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//[Mã câu hỏi (qCode): S5v3uFvK].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;73457A17”
//b.	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;n;A1,A2,...An” , với
//-	requestId là chuỗi ngẫu nhiên duy nhất
//-	n là một số ngẫu nhiên nhỏ hơn 100.
//-            A1, A2 ... Am (m <= n) là các giá trị ngẫu nhiên nhỏ hơn hoặc bằng n và có thể trùng nhau.
//Ex: requestId;10;2,3,5,6,5
//c.	Tìm kiếm các giá trị còn thiếu và gửi lên server theo định dạng “requestId;B1,B2,...,Bm”
//Ex: requestId;1,4,7,8,9,10
//d.	Đóng socket và kết thúc chương trình.
package ktra_udp; // Giả định bạn giữ nguyên package này


import java.util.*; 
import java.net.*;
import java.io.*;

public class S5v3uFvK { // Tên class là mã câu hỏi

    public static void main(String[] args) throws Exception {
        // khai bao socket + luong 
        DatagramSocket socket = new DatagramSocket(); // socket 
        InetAddress sA = InetAddress.getByName("203.162.10.109"); // server Address
        int sP = 2207; // server Port 
        
        // a. Gửi thông điệp msv + qcode
        String code = ";B21DCCN393;S5v3uFvK";
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.length(), sA, sP);
        socket.send(dpGui); // Gửi đi
       
        // b. Nhận thông điệp từ server
        byte[] buffer = new byte[1024]; // Tạo mảng byte để nhận
        DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(dpNhan); // Chờ nhận
        
        // Xử lý data nhận về
        // Phải .trim() để xóa các byte rác (ký tự null) ở cuối buffer
        String s_nhan = new String(dpNhan.getData()).trim();
        System.out.println( s_nhan); // In ra để kiểm tra
        
        // c. Xử lý logic và gửi kết quả
        
        // Tách chuỗi nhận được theo dấu ";"
        // parts[0] sẽ là requestId
        // parts[1] sẽ là n
        // parts[2] sẽ là dãy số A1,A2... (nếu có)
        String[] parts = s_nhan.split(";");
        String requestId = parts[0];
        int n = Integer.parseInt(parts[1]); // Lấy số n
        
        // Dùng Set để lưu các số đã có (tự động xử lý việc trùng lặp)
        Set<Integer> daCo = new HashSet<>();
        
      
        String[] daySoNhanDuoc = parts[2].split(",");
        for (String s : daySoNhanDuoc) {
                // Thêm từng số vào Set
                daCo.add(Integer.parseInt(s));
        }
        
        
        // Dùng StringBuilder để xây dựng chuỗi các số còn thiếu
//        StringBuilder soThieu = new StringBuilder();
//        
//        // Duyệt từ 1 đến n
//        for (int i = 1; i <= n; i++) {
//            if (!daCo.contains(i)) {
//                
//                if (soThieu.length() > 0) {
//                    soThieu.append(",");
//                }
//                soThieu.append(i);
//            }
//        }
            

        // không dùng String builder 
        String soThieu = ""; // Khởi tạo là chuỗi rỗng
        
        // Duyệt từ 1 đến n
        for (int i = 1; i <= n; i++) {
            if (!daCo.contains(i)) { // Nếu số i chưa có
                
                // Nếu chuỗi "soThieu" không rỗng (đã thêm số trước đó)
                if (soThieu.length() > 0) { // Hoặc: !soThieu.isEmpty()
                    soThieu += ","; // Nối dấu phẩy
                }
                soThieu += i; // Nối số bị thiếu
            }
        }
        
        // Tạo chuỗi kết quả cuối cùng
        String ketQua = requestId + ";" + soThieu.toString();
        System.out.println( ketQua);
        
        // Gửi kết quả lên server
        DatagramPacket dpGuiKetQua = new DatagramPacket(ketQua.getBytes(), ketQua.length(), sA, sP);
        socket.send(dpGuiKetQua);

 
    }
}
//[Mã câu hỏi (qCode): S5v3uFvK].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;73457A17”
//b.	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;n;A1,A2,...An” , với
//-	requestId là chuỗi ngẫu nhiên duy nhất
//-	n là một số ngẫu nhiên nhỏ hơn 100.
//-            A1, A2 ... Am (m <= n) là các giá trị ngẫu nhiên nhỏ hơn hoặc bằng n và có thể trùng nhau.
//Ex: requestId;10;2,3,5,6,5
//c.	Tìm kiếm các giá trị còn thiếu và gửi lên server theo định dạng “requestId;B1,B2,...,Bm”
//Ex: requestId;1,4,7,8,9,10
//d.	Đóng socket và kết thúc chương trình.
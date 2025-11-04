/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//[Mã câu hỏi (qCode): SHPTvMGm].  Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
//a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;5B35BCC1"
//b.	Nhận thông điệp từ server theo định dạng "requestId;data" 
//-	requestId là một chuỗi ngẫu nhiên duy nhất
//-	data là chuỗi dữ liệu cần xử lý
//c.	Xử lý chuẩn hóa chuỗi đã nhận thành theo nguyên tắc 
//i.	Ký tự đầu tiên của từng từ trong chuỗi là in hoa
//ii.	Các ký tự còn lại của chuỗi là in thường
//Gửi thông điệp chứa chuỗi đã được chuẩn hóa lên server theo định dạng "requestId;data"
//d.	Đóng socket và kết thúc chương trình
package ktra_udp; 


import java.util.*;
import java.net.*;
import java.io.*;

public class SHPTvMGm { 

    public static void main(String[] args) throws Exception {
        // khai bao socket + luong 
        DatagramSocket socket = new DatagramSocket(); // socket 
        InetAddress sA = InetAddress.getByName("203.162.10.109"); // server Address
        int sP = 2208; // server Port (theo đề bài)
        
        // a. Gửi thông điệp msv + qcode
        String code = ";B21DCCN393;SHPTvMGm"; // 
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.length(), sA, sP);
        socket.send(dpGui); // Gửi đi
        
        // b. Nhận thông điệp từ server
        byte[] buffer = new byte[1024]; // Tạo mảng byte để nhận
        DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(dpNhan); // Chờ nhận
        
        // Xử lý data nhận về
        String s_nhan = new String(dpNhan.getData()).trim();
        System.out.println( s_nhan); // In ra để kiểm tra
        
        // c. Xử lý logic và gửi kết quả
        
        // Tách chuỗi nhận được theo dấu ";"
        // parts[0] sẽ là requestId
        // parts[1] sẽ là data (chuỗi cần chuẩn hóa)
        // Thêm ,2 để đảm bảo chỉ tách thành 2 phần, phòng trường hợp chuỗi data cũng chứa dấu ;
        String[] parts = s_nhan.split(";", 2); 
        String requestId = parts[0];
        String data = parts[1];
        
       
        // 1. Xóa cách thừa đầu/cuối và chuyển hết về chữ thường
        String cleanData = data.trim().toLowerCase();
        
        // 2. Tách thành các từ (dùng "\\s+" để coi nhiều dấu cách như một)
        String[] words = cleanData.split("\\s+");
        
        // 3. Nối chuỗi lại 
        String processedData = ""; 
        
        // Duyệt qua từng từ
        for (String word : words) {
            
            if (word.isEmpty()) {
                continue; 
            }
            
            // Lấy ký tự đầu -> In hoa
            String firstLetter = word.substring(0, 1).toUpperCase();
            // Lấy phần còn lại của từ (đã là chữ thường)
            String restOfWord = word.substring(1);
            
            // Nối thành từ đã chuẩn hóa
            String capitalizedWord = firstLetter + restOfWord;
            
            // Thêm dấu cách ở giữa các từ (nếu đây không phải từ đầu tiên)
            if (processedData.length() > 0) {
                processedData += " ";
            }
            
            // Thêm từ đã chuẩn hóa vào kết quả
            processedData += capitalizedWord;
        }
        // --- Kết thúc logic chuẩn hóa chuỗi ---

        // Tạo chuỗi kết quả cuối cùng
        String ketQua = requestId + ";" + processedData;
        System.out.println(ketQua);
        
        // Gửi kết quả lên server
        DatagramPacket dpGuiKetQua = new DatagramPacket(ketQua.getBytes(), ketQua.length(), sA, sP);
        socket.send(dpGuiKetQua);
    }
}
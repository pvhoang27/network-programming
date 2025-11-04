/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): dG3Zgvjk].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN009;F3E8B2D4".
//
//b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;string", với:
//--- requestId là chuỗi ngẫu nhiên duy nhất.
//--- string là một chuỗi chứa các chuỗi con bị thay đổi vị trí. Ví dụ: "veM3xgA1g:4,IPFfgEanY:5,aWXlSzDwe:2,PHupvPc:3,PR3gH8ahN:6,UEEKHLIt:7,M6dpWTE:1"
//
//c. Xử lý chuỗi xáo trộn và gửi về chuỗi sau khi sắp xếp: "requestId;string". Ví dụ chuỗi đã được xử lý: "M6dpWTE,aWXlSzDwe,PHupvPc,veM3xgA1g,IPFfgEanY,PR3gH8ahN,UEEKHLIt"
//
//d. Đóng socket và kết thúc chương trình.
package ktra_udp;
import java.util.*;
import java.net.*;
import java.io.*;
/**
 *
 * @author hoang
 */
public class dG3Zgvjk {
    public static void main(String[] args) throws Exception {
        // khai bao socket + luong 
        DatagramSocket socket = new DatagramSocket(); // socket 
        InetAddress sA = InetAddress.getByName("203.162.10.109"); // server Address
        int sP = 2207; // server Port (theo đề bài)
        
        // a. Gửi thông điệp msv + qcode
        String code = ";B21DCCN393;dG3Zgvjk"; // Dùng qCode của bài này
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.length(), sA, sP);
        socket.send(dpGui); // Gửi đi
        
        // b. Nhận thông điệp từ server
        byte[] buffer = new byte[1024]; // Tạo mảng byte để nhận
        DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(dpNhan); // Chờ nhận
        
        // Xử lý data nhận về
        String s_nhan = new String(dpNhan.getData()).trim();
        System.out.println("Nhan tu server: " + s_nhan); // In ra để kiểm tra
        
        // c. Xử lý logic và gửi kết quả
        
        // Tách chuỗi nhận được theo dấu ";"
        // parts[0] sẽ là requestId
        // parts[1] sẽ là chuỗi xáo trộn
        String[] parts = s_nhan.split(";");
        String requestId = parts[0];
        
        // Lấy ra chuỗi xáo trộn, ví dụ: "veM3xgA1g:4,IPFfgEanY:5,..."
        String jumbledString = parts[1];
        
        // Tách các cặp "chuoi:vitri" bằng dấu phẩy
        String[] pairs = jumbledString.split(",");
        
        // Tạo một mảng String để lưu kết quả đã sắp xếp
        // Kích thước của mảng bằng số lượng cặp
        String[] sortedStrings = new String[pairs.length];
        
        // Duyệt qua từng cặp
        for (String pair : pairs) {
            // Tách cặp thành "chuoi" và "vitri" bằng dấu hai chấm
            // pairItem[0] = "veM3xgA1g"
            // pairItem[1] = "4"
            String[] pairItem = pair.split(":");
            
            String str = pairItem[0]; // Lấy chuỗi
            int pos = Integer.parseInt(pairItem[1]); // Lấy vị trí (dạng số)
            
            // Vị trí server cho là 1-based (bắt đầu từ 1)
            // Chỉ số mảng (array index) là 0-based (bắt đầu từ 0)
            // Nên ta lưu chuỗi vào mảng tại vị trí (pos - 1)
            sortedStrings[pos - 1] = str;
        }
        
        // Nối mảng đã sắp xếp lại thành một chuỗi
        // (Không dùng StringBuilder theo ý bạn)
        String resultString = "";
        
        // Duyệt qua mảng đã sắp xếp
        for (String s : sortedStrings) {
            // Nếu đây không phải chuỗi đầu tiên, thêm dấu phẩy
            if (resultString.length() > 0) {
                resultString += ",";
            }
            // Nối chuỗi
            resultString += s;
        }

        // Tạo chuỗi kết quả cuối cùng
        String ketQua = requestId + ";" + resultString;
        System.out.println( ketQua);
        
        // Gửi kết quả lên server
        DatagramPacket dpGuiKetQua = new DatagramPacket(ketQua.getBytes(), ketQua.length(), sA, sP);
        socket.send(dpGuiKetQua);

        
    }
}

//[Mã câu hỏi (qCode): dG3Zgvjk].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN009;F3E8B2D4".
//
//b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;string", với:
//--- requestId là chuỗi ngẫu nhiên duy nhất.
//--- string là một chuỗi chứa các chuỗi con bị thay đổi vị trí. Ví dụ: "veM3xgA1g:4,IPFfgEanY:5,aWXlSzDwe:2,PHupvPc:3,PR3gH8ahN:6,UEEKHLIt:7,M6dpWTE:1"
//
//c. Xử lý chuỗi xáo trộn và gửi về chuỗi sau khi sắp xếp: "requestId;string". Ví dụ chuỗi đã được xử lý: "M6dpWTE,aWXlSzDwe,PHupvPc,veM3xgA1g,IPFfgEanY,PR3gH8ahN,UEEKHLIt"
//
//d. Đóng socket và kết thúc chương trình.

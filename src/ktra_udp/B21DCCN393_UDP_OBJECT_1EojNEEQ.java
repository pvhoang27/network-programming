/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Mã câu hỏi (qCode): 1EojNEEQ
package ktra_udp; // Giả định package của bạn

/**
 *
 * @author hoang
 */
import java.util.*;
import java.net.*;
import java.io.*;
import UDP.Product; // Import lớp Product bạn vừa tạo

public class B21DCCN393_UDP_OBJECT_1EojNEEQ { 

    // --- Hàm xử lý logic (tách ra cho gọn) ---

    // a. Đảo tên: "T520 thinkpad lenovo" -> "lenovo thinkpad T520"
    public static String reverseName(String name) {
        String[] words = name.trim().split("\\s+");
        if (words.length <= 1) {
            return name; // Không có gì để đảo
        }
        
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        
        String fixedName = lastWord; // Bắt đầu bằng từ cuối
        // Thêm các từ ở giữa
        for (int i = 1; i < words.length - 1; i++) {
            fixedName += " " + words[i];
        }
        fixedName += " " + firstWord; // Kết thúc bằng từ đầu
        
        return fixedName;
    }

    // b. Đảo số: 9981 -> 1899
    public static int reverseQuantity(int quantity) {
        String qtyStr = String.valueOf(quantity);
        String reversedQtyStr = "";
        
        // Vòng lặp để đảo chuỗi (theo ý bạn không dùng StringBuilder)
        for (int i = qtyStr.length() - 1; i >= 0; i--) {
            reversedQtyStr += qtyStr.charAt(i);
        }
        
        return Integer.parseInt(reversedQtyStr);
    }
    // --- Kết thúc hàm xử lý logic ---


    public static void main(String[] args) throws Exception {
        // khai bao socket + luong 
        DatagramSocket socket = new DatagramSocket(); // socket 
        InetAddress sA = InetAddress.getByName("203.162.10.109"); // server Address
        int sP = 2209; // server Port (theo đề bài)
        
        // b.1 Gửi thông điệp msv + qcode
        String code = ";B21DCCN393;1EojNEEQ"; // Dùng qCode của bài này
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(), code.length(), sA, sP);
        socket.send(dpGui); // Gửi đi
        
        // b.2 Nhận thông điệp (requestId + Object)
        byte[] buffer = new byte[2048]; // Tăng buffer lên cho chắc, vì Object có thể lớn
        DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(dpNhan); // Chờ nhận
        
        // Xử lý data nhận về
        
        // Lấy 8 byte đầu làm requestId
        byte[] reqIdBytes = new byte[8];
        // copy mảng: (từ mảng nguồn, vị trí bắt đầu, đến mảng đích, vị trí bắt đầu, độ dài)
        System.arraycopy(dpNhan.getData(), 0, reqIdBytes, 0, 8);
        String requestId = new String(reqIdBytes);
        System.out.println("Nhan requestId: " + requestId);

        // Lấy phần còn lại làm Object
        // Dùng ByteArrayInputStream để đọc mảng byte
        // (mảng nguồn, vị trí bắt đầu, độ dài)
        // Độ dài = tổng độ dài - 8 byte đầu
        ByteArrayInputStream bais = new ByteArrayInputStream(dpNhan.getData(), 8, dpNhan.getLength() - 8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        
        // Đọc (Giải tuần tự hóa) đối tượng
        Product spNhan = (Product) ois.readObject();
        ois.close();
        bais.close();
        
        System.out.println("Nhan san pham: " + spNhan.name + ", " + spNhan.quantity);

        // b.3 Sửa thông tin sai
        String fixedName = reverseName(spNhan.name);
        int fixedQuantity = reverseQuantity(spNhan.quantity);
        
        // Cập nhật lại đối tượng
        spNhan.name = fixedName;
        spNhan.quantity = fixedQuantity;
        System.out.println("Sua thanh: " + spNhan.name + ", " + spNhan.quantity);
        
        // Gửi đối tượng đã sửa đổi lên server
        
        // 1. Chuyển Object thành mảng byte (Tuần tự hóa)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(spNhan); // Ghi đối tượng vào luồng
        oos.flush();
        byte[] productBytes = baos.toByteArray(); // Lấy mảng byte của đối tượng
        oos.close();
        baos.close();
        
        // 2. Tạo mảng byte tổng để gửi đi (8 byte requestId + N byte object)
        byte[] guiBuffer = new byte[8 + productBytes.length];
        
        // 3. Copy requestId vào 8 byte đầu
        System.arraycopy(reqIdBytes, 0, guiBuffer, 0, 8);
        
        // 4. Copy mảng byte của object vào phần còn lại (bắt đầu từ vị trí 8)
        System.arraycopy(productBytes, 0, guiBuffer, 8, productBytes.length);
        
        // 5. Gửi packet
        DatagramPacket dpGuiSua = new DatagramPacket(guiBuffer, guiBuffer.length, sA, sP);
        socket.send(dpGuiSua);
        System.out.println("Da gui san pham da sua.");

        // b.4 Đóng socket
        socket.close();
        System.out.println("Da hoan thanh, dong socket.");
    }
}
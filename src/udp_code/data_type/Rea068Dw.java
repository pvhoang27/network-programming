/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): Rea068Dw].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;DC73CA2E”
//b.	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;a1,a2,...,a50” 
//-	requestId là chuỗi ngẫu nhiên duy nhất
//-	a1 -> a50 là 50 số nguyên ngẫu nhiên
//c.	Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất thông điệp trong a1 -> a50 và gửi thông điệp lên lên server theo định dạng “requestId;max,min”
//d.	Đóng socket và kết thúc chương trình
package udp_code.data_type;

import java.net.DatagramSocket;
import java.util.*;
import java.net.*;
import java.io.*;

/**
 *
 * @author hoang
 */
public class Rea068Dw {
    public static void main(String[] args) throws  Exception{
        // khai bao socket + luong + ten host , tên port
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("203.162.10.109"); //host
        int sP = 2207; //port
        
        // khai báo biến code + gửi đi 
        String code = ";B21DCCN393;Rea068Dw";
        DatagramPacket dpGui = new DatagramPacket(code.getBytes(),code.length(),sA, sP);
        socket.send(dpGui);
        
        // nhận data về
        byte []buffer = new byte[4096]; // 4096 cho 50 số 
        DatagramPacket dpNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(dpNhan);
        // thao tác in cái chuỗi đó ra cho dễ nhìn 
        String st = new String(dpNhan.getData(), 0 , dpNhan.getLength());
        System.out.println(st);
        st = st.replace(",", " "); st = st.replace(";", " "); // làm sạch kí tự 

        // tách dữ liệu 
        String []tmp = st.trim().split("\\s+");
        String rqID = tmp[0];
        long max = Long.MIN_VALUE, min = Long.MAX_VALUE;
        for(int i = 1; i < tmp.length; i++){
            long v = Long.parseLong(tmp[i].trim());
            if(v > max) max = v;
            if(v < min) min = v;
        }
        // ghép dữ liệu vào để nộp  lên sv 
        String ans = rqID + ";" + max + "," + min;
        System.out.println(ans);
        //Gửi kết quả lên server 
        DatagramPacket dpGui1 = new DatagramPacket(ans.getBytes(), ans.length(), sA, sP);
        socket.send(dpGui1); 
    }
}

//[Mã câu hỏi (qCode): Rea068Dw].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;DC73CA2E”
//b.	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;a1,a2,...,a50” 
//-	requestId là chuỗi ngẫu nhiên duy nhất
//-	a1 -> a50 là 50 số nguyên ngẫu nhiên
//c.	Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất thông điệp trong a1 -> a50 và gửi thông điệp lên lên server theo định dạng “requestId;max,min”
//d.	Đóng socket và kết thúc chương trình

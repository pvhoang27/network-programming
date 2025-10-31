///*
//[Mã câu hỏi (qCode): jHma50N3].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2206 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s).
//Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng các luồng byte (InputStream/OutputStream) để trao đổi thông tin theo thứ tự: 
//a.	Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;2B3A6510"
//b.	Nhận dữ liệu từ server là một số nguyên n nhỏ hơn 400. Ví dụ: 7
//c.	Thực hiện các bước sau đây để sinh ra chuỗi từ số nguyên n ban đầu và gửi lên server.
//        Bắt đầu với số nguyên nn:
//            Nếu n là số chẵn, chia nn cho 2 để tạo ra số tiếp theo trong dãy.
//            Nếu n là số lẻ và khác 1, thực hiện phép toán n=3*n+1 để tạo ra số tiếp theo.
//        Lặp lại quá trình trên cho đến khi n=1, tại đó dừng thuật toán.
//Kết quả là một dãy số liên tiếp, bắt đầu từ n ban đầu, kết thúc tại 1 và độ dài của chuỗi theo format "chuỗi kết quả; độ dài"  Ví dụ: kết quả với n = 7 thì dãy: 7 22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1; 17;  
//d.	Đóng kết nối và kết thúc chương trình.
// */
//package tcp_code.ByteStream_use_InputStream;
//
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.Socket;
//
//
//public class jHma50N3_B21DCCN132 {
//    public static void main(String[] args) throws Exception{
//        // khai bao socket va luong 
//        Socket socket = new Socket("203.162.10.109", 2206);
//        InputStream is = socket.getInputStream();
//        OutputStream os = socket.getOutputStream();
//        //khai bao msv + qcode
//        String req = "B21DCCN132;jHma50N3";
//        // gui msv + qcode cho sv
//        os.write(req.getBytes()); //luu y phai getbyte vi day la cau bytestream
//        os.flush();
//        //nhan du lieu tu sv ve 
//        byte[] buffer = new byte[1024]; //tao 1 mang byte de chua du lieu  doc vao 
//        int length = is.read(buffer); // doc du lieu tu input vao buffer va tra ve so byte thuc te 
//        String data = new String(buffer, 0 , length); //tao chuoi tu du lieu của buffer vua tao
////        data = data.trim().replace("\r","").replace("\n",""); //xoa khoang trang + xoa xuong dong kieu r va n
//        //in du lieu ra cho de nhin
//        System.out.println(data);
//        
//        // xu ly bai toan
//        StringBuilder seq = new StringBuilder();
//        long n = Long.parseLong(data.trim());
//        int count = 0 ;
//        while (true) {
//            // append gia tri hien tai
//            if (count > 0) seq.append(" ");
//            seq.append(n);
//            count++;
//
//            if (n == 1) break; // dieu kien dung
////            if ((n & 1L) == 0L) {
////                n = n / 2;           // n chan
////            } 
//            if (n % 2 ==0) {
//                n = n / 2 ;
//            }
//            else {
//                n = 3 * n + 1;       // n le va khac 1
//            }
//        }
//        String kqua = seq.toString() + "; " + count;
//        System.out.println(kqua);
//        
//        os.write(kqua.getBytes());
//        os.flush(); 
//    }         
//}

// neu ko dung Stringbuilder , 
package tcp_code.ByteStream_use_InputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class jHma50N3_B21DCCN132 {
    public static void main(String[] args) throws Exception {
        // Tạo kết nối đến server
        Socket socket = new Socket("203.162.10.109", 2206);

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        // a) Gửi mã sinh viên và mã câu hỏi
        String req = "B21DCCN132;jHma50N3";
        os.write(req.getBytes());
        os.flush();

        // b) Nhận số n từ server
        byte[] buffer = new byte[1024];
        int length = is.read(buffer);
        String data = new String(buffer, 0, length );
        System.out.println( data);

        // c) Xử lý tạo chuỗi Collatz
        long n = Long.parseLong(data);
        String seq = "";  //  String luc nay nhu la 1 cai mang 
        int count = 0;

        while (true) {
            if (count == 0)
                seq = seq + n;        // phần tử đầu tiên không có khoảng trắng
            else
                seq = seq + " " + n;  // các phần tử sau có khoảng trắng
            count++;

            if (n == 1) break;        // kết thúc khi n = 1
            if (n % 2 == 0)
                n = n / 2;
            else
                n = 3 * n + 1;
        }

        // d) Tạo kết quả đúng định dạng: "chuỗi; độ dài;"
        String result = seq + "; " + count;
        System.out.println( result);

        // Gửi kết quả lên server
        os.write(result.getBytes());
        os.flush();
    }
}

//[Mã câu hỏi (qCode): 8Ci4bRo4].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu sinh viên xây dựng chương trình client để tương tác với server, sử dụng các luồng data (DataInputStream và DataOutputStream) để trao đổi thông tin theo thứ tự sau:
//a. Gửi mã sinh viên và mã câu hỏi: Chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;D68C93F7".
//b. Nhận một số nguyên hệ thập phân từ server. Ví dụ: 45
//c. Chuyển đổi số nguyên nhận được sang hệ nhị phân và gửi chuỗi kết quả này lại cho server. Ví dụ: 45 sẽ thành chuỗi "101101"
//d. Đóng kết nối và kết thúc chương trình.
package luyencode;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class b21dccn393_8Ci4bRo4 {
    public static void main (String[] args) throws IOException{
        // khai bao cong + luong
        Socket socket = new Socket("203.162.10.109", 2207);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());
        
        // khai bao request va gui request di 
        String request = "B21DCCN393;8Ci4bRo4";
        out.writeUTF(request);
        
        //nhan de bai ve 
        int n = in.readInt();
        System.out.println(n);
        
        //code xu ly bai toan
        String bin = Integer.toBinaryString(n);
        System.out.println(bin);
        
        //gui dap an cho sv
        out.writeUTF(bin);
    }
}

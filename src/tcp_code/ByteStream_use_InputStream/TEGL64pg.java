/*
[Mã câu hỏi (qCode): TEGL64pg].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
    a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;76B68B3B".
    b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách bởi ký tự ",". Ví dụ: 5,10,20,25,50,40,30,35.
    c. Tìm chuỗi con tăng dần dài nhất và gửi độ dài của chuỗi đó lên server theo format "chuỗi tăng dài nhất; độ dài". Ví dụ: 5,10,20,25,50;5
    d. Đóng kết nối và kết thúc chương trình.
 */
package tcp_code.ByteStream_use_InputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TEGL64pg {
    public static void main(String[] args) throws Exception{
        //khai bao socket va luong 
        Socket socket = new Socket("203.162.10.109",2206);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        // khai bao msv va qcode 
        String request = "B21DCCN393;TEGL64pg";

        //gui msv va qcode len cho sv
        os.write(request.getBytes());
        os.flush();

        //nhan du lieu tu sv ve 
        byte[] buffer = new byte[4096]; //tao 1 cai mang byte
        int length = is.read(buffer);   // nhan ve do dai cua byte do 
        String received = new String(buffer, 0, length); // tao chuoi tu du lieu trong buffer vua tao 
        received = received.trim().replace("\r","").replace("\n","");

        //in du lieu sv gui ve cho de nhin
        System.out.println(received);

        // ===== xu ly bai toan dat ra (LIS + xay dung day lexicographically maximum) =====
        // buoc 1 chuyen chuoi string thanh int
        String [] parts = received.split(",");
        int n = parts.length;
        int[] nums = new int[n];
        for(int i = 0 ; i < n; i++){
            nums[i] = Integer.parseInt(parts[i].trim()); // xoa khoang trang
        }

        // buoc 2: tinh do dai LIS ket thuc tai moi vi tri: L_end[i]
        int[] L_end = new int[n];
        int[] tailVal1 = new int[n]; // luu tail nho nhat cho moi do dai
        int len1 = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            int pos = lowerBound(tailVal1, len1, x); // vi tri chen
            tailVal1[pos] = x;
            if (pos == len1) len1++;
            L_end[i] = pos + 1;
        }
        int L = len1; // do dai LIS

        // buoc 3: tinh do dai LIS bat dau tai moi vi tri: L_start[i]
        int[] L_start = new int[n];
        int[] tailVal2 = new int[n];
        int len2 = 0;
        for (int i = n - 1; i >= 0; i--) {
            int x = -nums[i]; // doi dau de dung lower_bound
            int pos = lowerBound(tailVal2, len2, x);
            tailVal2[pos] = x;
            if (pos == len2) len2++;
            L_start[i] = pos + 1;
        }

        // buoc 4: truy vet ra day LIS "lon nhat theo tu dien"
        StringBuilder listString = new StringBuilder();
        int last = Integer.MIN_VALUE;
        int prevIndex = -1;
        for (int k = 1; k <= L; k++) {
            int bestIdx = -1;
            int bestVal = Integer.MIN_VALUE;
            for (int i = prevIndex + 1; i < n; i++) {
                // i co the la vi tri thu k trong mot LIS do dai L
                if (nums[i] > last && L_end[i] == k && (L_end[i] + L_start[i] - 1) >= L) {
                    // chon gia tri lon nhat de "lexicographically maximum"
                    if (nums[i] > bestVal) {
                        bestVal = nums[i];
                        bestIdx = i;
                    }
                }
            }
            // append gia tri duoc chon
            if (k > 1) listString.append(",");
            listString.append(nums[bestIdx]);
            last = nums[bestIdx];
            prevIndex = bestIdx;
        }

        // gui kqua len sv voi format "day;do dai"
        String kqua  = listString.toString() + ";" + L;
        System.out.println(kqua);
        os.write(kqua.getBytes());
        os.flush();

        // dong ket noi
        socket.close();
    }

    // ham lower_bound: tim vi tri dau tien co gia tri >= x trong mang a[0..len)
    static int lowerBound(int[] a, int len, int x) {
        int l = 0, r = len;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (a[m] < x) l = m + 1;
            else r = m;
        }
        return l;
    }
}

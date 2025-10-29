package WebService.B21DCCN393;

import java.io.*;
import java.net.*;

public class TEGL64pg {
    public static void main(String[] args) {
        String msv = "B21DCCN393";
        String qCode = "TEGL64pg";
        String host = "203.162.10.109";
        int port = 2206;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000);

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            // a. Gửi mã sinh viên và mã câu hỏi
            String request = msv + ";" + qCode + "\n"; // thêm \n để server đọc hết dòng
            out.write(request.getBytes());
            out.flush();

            // b. Nhận dữ liệu
            String data = reader.readLine();
            System.out.println("Dữ liệu nhận được: " + data);

            if (data == null || data.trim().isEmpty()) return;

            // c. Tìm chuỗi tăng dần dài nhất
            String[] arr = data.split(",");
            int[] a = new int[arr.length];
            for (int i = 0; i < arr.length; i++) a[i] = Integer.parseInt(arr[i].trim());

            int best = 1, cur = 1, end = 0;
            for (int i = 1; i < a.length; i++) {
                if (a[i] > a[i - 1]) cur++;
                else cur = 1;
                if (cur > best) { best = cur; end = i; }
            }

            StringBuilder sb = new StringBuilder();
            for (int i = end - best + 1; i <= end; i++) {
                sb.append(a[i]);
                if (i < end) sb.append(",");
            }

            String result = sb + ";" + best + "\n"; // ✅ thêm \n cho server biết kết thúc chuỗi
            out.write(result.getBytes());
            out.flush();

            System.out.println("Đã gửi kết quả: " + result.trim());

            // Không shutdownOutput hoặc close ngay → để server xử lý xong
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}

        } catch (SocketTimeoutException e) {
            System.out.println("⏳ Timeout khi kết nối hoặc đọc dữ liệu!");
        } catch (IOException e) {
            System.out.println("⚠️ Lỗi IO: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Lỗi khác: " + e.getMessage());
        }
    }
}

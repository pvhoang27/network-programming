package luyencode;
import java.io.*;
import java.net.*;
import java.util.*;

public class TEGL64pg {
    public static void main(String[] args) throws Exception {
        String host = "203.162.10.109";
        int port = 2206;
        String studentCode = "B21DCCN393";
        String qCode = "TEGL64pg";

        Socket socket = new Socket(host, port);
//        socket.setSoTimeout(5000);

        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        // a. Gửi mã sinh viên và mã câu hỏi
        String request = studentCode + ";" + qCode;
        os.write(request.getBytes());
        os.flush();

        // b. Nhận dữ liệu
        byte[] buffer = new byte[4096];
        int bytesRead = is.read(buffer);
        String data = new String(buffer, 0, bytesRead).trim();
        System.out.println(data);

        // c. Tìm chuỗi con tăng dần dài nhất (LIS - không cần liên tiếp)
        String[] parts = data.split(",");
        int n = parts.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(parts[i].trim());

        int[] dp = new int[n];
        int[] prev = new int[n];
        Arrays.fill(dp, 1);
        Arrays.fill(prev, -1);

        int maxLen = 1, lastIndex = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (a[i] > a[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }

        // Truy vết chuỗi LIS
        List<Integer> lis = new ArrayList<>();
        for (int i = lastIndex; i != -1; i = prev[i]) lis.add(a[i]);
        Collections.reverse(lis);

        StringBuilder seq = new StringBuilder();
        for (int i = 0; i < lis.size(); i++) {
            seq.append(lis.get(i));
            if (i < lis.size() - 1) seq.append(",");
        }

        String result = seq.toString() + ";" + lis.size();
        System.out.println("Gửi lại: " + result);
        os.write(result.getBytes());
        os.flush();

        os.close();
        is.close();
        socket.close();
    }
}

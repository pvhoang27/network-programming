/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ktra_tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Data_Stream_S2KGWnpT {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2207);
        socket.setSoTimeout(5000);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        // a) Gửi mã sinh viên + qCode
        String req = "B21DCCN393;S2KGWnpT";
        dos.writeUTF(req);
        dos.flush();

        // b) Nhận n và n số nguyên
        int n = dis.readInt();
        int[] a = new int[n];
        long sum = 0L;
        for (int i = 0; i < n; i++) {
            a[i] = dis.readInt();
            sum += a[i];
        }

        // c) Tính tổng, trung bình, phương sai
        int tong = (int) sum;                 // server yêu cầu gửi int
        double meanD = sum / (double) n;      // tránh chia nguyên
        float mean = (float) meanD;

        double acc = 0.0;
        for (int x : a) {
            double d = x - meanD;
            acc += d * d;
        }
        float variance = (float) (acc / n);

        // Gửi lần lượt: int, float, float
        dos.writeInt(tong);
        dos.writeFloat(mean);
        dos.writeFloat(variance);
        dos.flush();

        // (tuỳ chọn) in ra kiểm tra
        System.out.println("sum=" + tong + ", mean=" + mean + ", var=" + variance);

        dis.close();
        dos.close();
        socket.close();
    }
}

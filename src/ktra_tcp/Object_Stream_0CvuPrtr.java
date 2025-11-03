/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Client_0CvuPrtr {
    public static void main(String[] args) throws Exception {
        // a) Kết nối server qua TCP cổng 2209, đặt timeout 5s
        Socket socket = new Socket("203.162.10.109", 2209);
        socket.setSoTimeout(5000);

        // LƯU Ý: Tạo ObjectOutputStream trước để gửi header, tránh deadlock
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush(); // đẩy header ngay
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        // a) Gửi đối tượng là một chuỗi "studentCode;qCode"
        String request = "B21DCCN393;0CvuPrtr";
        oos.writeObject(request);
        oos.flush();

        // b) Nhận đối tượng Product từ server
        Object obj = ois.readObject();
        if (!(obj instanceof Product)) {
            throw new IllegalStateException("Đối tượng nhận không phải TCP.Product");
        }
        Product p = (Product) obj;
        System.out.println("Nhận: id=" + p.id + ", name=" + p.name + ", price=" + p.price + ", discount=" + p.discount);

        // c) Tính discount = tổng chữ số của phần nguyên của price
        long intPart = (long) Math.floor(p.price);
        int discount = sumDigits(intPart);
        p.discount = discount;

        // Gửi lại đối tượng đã gán discount
        oos.writeObject(p);
        oos.flush();
        System.out.println("Gửi lại: id=" + p.id + ", name=" + p.name + ", price=" + p.price + ", discount=" + p.discount);

        // d) Đóng kết nối
        ois.close();
        oos.close();
        socket.close();
        System.out.println("Hoàn thành!");
    }

    private static int sumDigits(long n) {
        if (n < 0) n = -n;
        int s = 0;
        while (n > 0) {
            s += (int)(n % 10);
            n /= 10;
        }
        return s;
    }
}

/**
 * Lớp TCP.Product đúng định danh đầy đủ mà server dùng.
 * Các thuộc tính và serialVersionUID khớp yêu cầu đề bài.
 * Để đơn giản, để public field; điều này vẫn tương thích tuần tự hoá.
 */
class Product implements Serializable {
    private static final long serialVersionUID = 20231107L;

    public int id;
    public String name;
    public double price;
    public int discount;

    public Product() {} // no-arg constructor để dễ serialize
}

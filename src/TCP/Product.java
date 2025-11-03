/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class 0CvuPrtr {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2209);
        socket.setSoTimeout(5000);

        // Tạo OOS trước để gửi header, tránh deadlock
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        // a) Gửi chuỗi "studentCode;qCode"
        String request = "B21DCCN393;0CvuPrtr";
        oos.writeObject(request);
        oos.flush();

        // b) Nhận đối tượng Product
        Object obj = ois.readObject();
        if (!(obj instanceof Product)) {
            throw new IllegalStateException("Đối tượng nhận không phải TCP.Product");
        }
        Product p = (Product) obj;
        System.out.println("Nhận: id=" + p.id + ", name=" + p.name + ", price=" + p.price + ", discount=" + p.discount);

        // c) discount = tổng chữ số của phần nguyên của price
        long intPart = (long) Math.floor(p.price);
        p.discount = sumDigits(intPart);

        // Gửi lại đối tượng đã cập nhật
        oos.writeObject(p);
        oos.flush();
        System.out.println("Gửi lại: id=" + p.id + ", name=" + p.name + ", price=" + p.price + ", discount=" + p.discount);

        // d) Đóng
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

class Product implements Serializable {
    private static final long serialVersionUID = 20231107L;
    public int id;
    public String name;
    public double price;
    public int discount;
    public Product() {}
}

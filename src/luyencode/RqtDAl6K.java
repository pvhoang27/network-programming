//[Mã câu hỏi (qCode): RqtDAl6K].  Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
//a) Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: “lenovo thinkpad T520” bị chuyển thành “T520 thinkpad lenovo”
//b) Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899
//
//Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) để gửi/nhận và sửa các thông tin bị sai của sản phẩm. Chi tiết dưới đây:
//a) Đối tượng trao đổi là thể hiện của lớp Laptop được mô tả như sau
//      •	Tên đầy đủ của lớp: TCP.Laptop
//      •	Các thuộc tính: id int, code String, name String, quantity int
//      •	Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
//      •	Trường dữ liệu: private static final long serialVersionUID = 20150711L; 
//b)	Tương tác với server theo kịch bản
//1)	Gửi đối tượng là chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;5AD2B818"
//2)	Nhận một đối tượng là thể hiện của lớp Laptop từ server
//3)	Sửa các thông tin sai của sản phẩm về tên và số lượng.  Gửi đối tượng vừa được sửa sai lên server
//4)	Đóng socket và kết thúc chương trình.
package luyencode;
import TCP.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
//import TCP.Laptop;
import java.io.*;
class Laptop implements Serializable{
    private static final long serialVersionUID = 20150711L;
    
    private int id;
    private String code;
    private String name;
    private int quantity ;

    public Laptop(int id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Laptop{" + "id=" + id + ", code=" + code + ", name=" + name + ", quantity=" + quantity + '}';
    }
    
}
public class RqtDAl6K  {
    // ham fix laptop
     private static Laptop fix(Laptop lp) {
        // a) sửa name
        String name = lp.getName().trim().replaceAll("\\s+", " ");
        String[] parts = name.split(" ");
        if (parts.length >= 2) {
            String tmp = parts[0];
            parts[0] = parts[parts.length - 1];
            parts[parts.length - 1] = tmp;
            lp.setName(String.join(" ", parts));
        }
        // b) đảo ngược số lượng
        String reversed = new StringBuilder("" + lp.getQuantity()).reverse().toString();
        lp.setQuantity(Integer.parseInt(reversed));

        return lp;
    }
          
    public static void main(String[] args) throws Exception{
        // khai bao cong va luong 
        Socket socket = new Socket ("203.162.10.109", 2209);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois  = new ObjectInputStream(socket.getInputStream());
        
        //gui msv + qcode len sv
        String request = "B21DCCN393;RqtDAl6K" ;
        oos.writeObject(request);
        
        //nhan du lieu tu sv
        Laptop laptop = (Laptop) ois.readObject();
        
        // xu ly de bai 
        Laptop fixed = fix(laptop);
        
        // gui ket qua len sv
        oos.writeObject(fixed);
    }
}

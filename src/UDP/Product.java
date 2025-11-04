package UDP; 

import java.io.Serializable;

// Lớp này BẮT BUỘC phải "implements Serializable" để có thể gửi qua mạng
public class Product implements Serializable {
    
    // Phải có đúng serialVersionUID này
    private static final long serialVersionUID = 20161107; 
    
    // Các thuộc tính
    public String id; // Để public cho tiện truy cập, hoặc dùng private + getter/setter
    public String code;
    public String name;
    public int quantity;

    // Hàm khởi tạo đủ tham số
    public Product(String id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }
    
    // Bạn cũng nên thêm một constructor rỗng (mặc dù đề không yêu cầu
    // nhưng nó tốt cho việc khởi tạo)
    public Product() {
    }
}
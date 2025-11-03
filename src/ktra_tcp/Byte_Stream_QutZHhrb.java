/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ktra_tcp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author hoang
 */
public class Byte_Stream_QutZHhrb {
    public static void main(String[] args) throws Exception{
        // khai báo socket + luồng
        Socket socket = new Socket("203.162.10.109",2206);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        
        //gửi msv + qcode lên sv
        String request  ="B21DCCN393;QutZHhrb";
        os.write(request.getBytes());
        os.flush();
        // nhan data ve 
        byte[] buffer = new byte[1024] ;
        int length = is.read(buffer);
        String data =  new String(buffer, 0 , length);
        //in ra cho de nhin 
        System.out.println(data);
        // xu ly de bai 
        String[] parts = data.split(",");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i].trim());
        }

        // tìm max1, max2 (distinct)
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE;
        for (int x : arr) {
            if (x > max1) {
                max2 = max1;
                max1 = x;
            } else if (x > max2 && x != max1) {
                max2 = x;
            }
        }

        // tìm vị trí (0-based)
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == max2) {
                index = i;
                break;
            }
        }

        String result = max2 + "," + index + "\n";
        System.out.println(result.trim());

        // 5. Gửi kết quả lên server
        os.write(result.getBytes());
        os.flush();
        
        
        
    }
}

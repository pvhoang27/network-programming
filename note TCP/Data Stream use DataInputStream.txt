/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcp_code.DataStream_use_DataInputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author hoang
 */
public class B21DCCN393_8Ci4bRo4 {
    public static void main(String[] args) throws Exception{
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

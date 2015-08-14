package wyf.wpf;		//声明包语句		
import java.io.DataOutputStream;	//引入相关类
import java.net.ServerSocket;		//引入相关类
import java.net.Socket;				//引入相关类
import java.util.Date;				//引入相关类
public class Server{
	public static void main(String args[]){
		try{
			ServerSocket ss = new ServerSocket(8888);
			System.out.println("Listening...");
			while(true){
				Socket socket = ss.accept();
				System.out.println("Client Connected...");
				DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
				Date d = new Date();
				dout.writeUTF(d.toLocaleString());
				dout.close();
				socket.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
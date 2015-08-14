package wyf.wpf;		//���������		
import java.io.DataOutputStream;	//���������
import java.net.ServerSocket;		//���������
import java.net.Socket;				//���������
import java.util.Date;				//���������
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
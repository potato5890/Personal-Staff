package wpf;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.net.*;
public class MyServlet extends HttpServlet{
	public void doGet (HttpServletRequest request,
		       HttpServletResponse response) {
		try{
			PrintWriter writer = response.getWriter();
			writer.print("����ͨ��Get��ʽ���ص���Ӧ");
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
    }
    public void doPost (HttpServletRequest request,
		       HttpServletResponse response) {
		try{
			PrintWriter writer = response.getWriter();
			writer.print("����ͨ��Post��ʽ���ص���Ӧ");
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}	
    }
}
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
			writer.print("我是通过Get方式返回的响应");
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
			writer.print("我是通过Post方式返回的响应");
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}	
    }
}
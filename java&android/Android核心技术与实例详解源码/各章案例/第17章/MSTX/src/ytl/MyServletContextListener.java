package ytl;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
public class MyServletContextListener implements ServletContextListener{
	ServerThread st = null;//�����������̵߳�����
	public void contextInitialized(ServletContextEvent sce){//��д��contextInitialized����
		sce.getServletContext().log("[ytl] Context Initialized...");
		System.out.println("[ytl] Context Initialized...");
		st = new ServerThread();//���������߳� 
		st.start();//���������߳�
	}
	public void contextDestroyed(ServletContextEvent sce){//��д��contextDestroyed����
		sce.getServletContext().log("[ytl] Context Destroyed...");
		System.out.println("[ytl] Context Destroyed...");
		st.setFlag(false);//ֹͣ�����߳� 
	}
}
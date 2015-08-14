package ytl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ControlServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		String action = request.getParameter("action");
		if(action == null){//��actionΪ��ʱ
			request.getRequestDispatcher("error.jsp?msg=actionΪnull").forward(request,response);
			return;
		}
		if(action.equals("toregister")){//��ע��
			request.getRequestDispatcher("register.jsp").forward(request,response);
		}
		else if(action.equals("favourite")){//���ҵ��ղ�
			if(isLogin(request,response)){//��¼��
				request.getRequestDispatcher("favourite.jsp").forward(request,response);
			}
			else{//û�е�¼
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}
		}
		else if(action.equals("register")){//�û�ע�ᶯ��
			String u_name = request.getParameter("u_name");
			String u_qq	= request.getParameter("u_qq");
			String u_pwd = request.getParameter("u_pwd1");
			String u_Email = request.getParameter("u_Email");
			String u_dis = request.getParameter("u_dis");
			int temNum = DBUtil.insertUser(u_name,u_qq,u_pwd,u_Email,u_dis);
			if(temNum != -1){//�������ݳɹ�
		    	HttpSession session=request.getSession(true);
		    	session.setAttribute("uid",temNum+"");
		    	session.setAttribute("u_name",u_name);
		    	request.getRequestDispatcher("home.jsp").forward(request,response);
			}
			else {//����ʧ��
				request.getRequestDispatcher("error.jsp").forward(request,response);
			}
		}
		else if(action.equals("exit")){//ע��
			HttpSession session=request.getSession(true);
			session.removeAttribute("uid");
			session.removeAttribute("u_name");
			request.getRequestDispatcher("index.jsp").forward(request,response);
		}
		else if(action.equals("login")){//��¼����
			String uid = request.getParameter("uid");
			String u_pwd = request.getParameter("u_pwd");
			String u_name = DBUtil.checkUser(uid, u_pwd);
			if(u_name != null){
		    	HttpSession session=request.getSession(true);
		    	session.setAttribute("uid",uid);
		    	session.setAttribute("u_name",u_name);
		    	request.getRequestDispatcher("index.jsp").forward(request,response);
			}
			else{
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}
		}
		else if(action.equals("home")){//��������ҳ
			if(isLogin(request, response)){
				request.getRequestDispatcher("home.jsp").forward(request,response);
			}
			else{//û�е�¼
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}				
		}
		else if(action.equals("index")){//����ҳ
			request.getRequestDispatcher("index.jsp").forward(request,response);
		}
		else if(action.equals("search")){//����ʳ����ҳ��
			if(isLogin(request,response)){//��¼��
				request.getRequestDispatcher("search.jsp").forward(request,response);
			}
			else{//û�е�¼
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}			
		}
		else if(action.equals("everydayRecommend")){//��ÿ���Ƽ�
			if(isLogin(request,response)){//��¼��
				request.getRequestDispatcher("everydayRecommend.jsp").forward(request,response);
			}
			else{//û�е�¼
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}			
		}
		else if(action.equals("updateRecommend")){//���ϴ��Ƽ�
			if(isLogin(request,response)){//��¼��
				request.getRequestDispatcher("updateRecommend.jsp").forward(request,response);
			}
			else{//û�е�¼
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}			
		}
		else if(action.equals("searchInfo")){//������ʳ
			String type = request.getParameter("type");
			request.getRequestDispatcher("search.jsp?type="+type).forward(request,response);
		}
		else if(action.equals("insertMstxCol")){//�ղض���
			String mid = request.getParameter("mid");
			HttpSession session=request.getSession(true);
			String uid = (String) session.getAttribute("uid");
			if(uid != null){
				DBUtil.insertMstxCol(mid, uid, "");
				request.getRequestDispatcher("favourite.jsp").forward(request,response);
			}
			else {
				request.getRequestDispatcher("error.jsp").forward(request,response);				
			}
		}
		else if(action.equals("deleteMstxCol")){//ɾ���ղض���
			String mid = request.getParameter("mid");
			HttpSession session=request.getSession(true);
			String uid = (String) session.getAttribute("uid");	
			if(uid != null){
				DBUtil.deleteMstxCol(mid, uid);
				request.getRequestDispatcher("favourite.jsp").forward(request,response);
			}
			else {
				request.getRequestDispatcher("error.jsp").forward(request,response);				
			}			
		}
		else if(action.equals("changeHead")){//������ͷ��ҳ��
			HttpSession session=request.getSession(true);
			String uid = (String) session.getAttribute("uid");	
			if(uid != null){
				request.getRequestDispatcher("changeHead.jsp").forward(request,response);
			}
			else {
				request.getRequestDispatcher("error.jsp?msg=���¼").forward(request,response);				
			}			
		}
		else if(action.equals("updateUserHead")){//���µ�ǰ�û���ͷ��
			String id = request.getParameter("id");
			HttpSession session=request.getSession(true);
			String uid = (String) session.getAttribute("uid");
			if(uid != null){
				DBUtil.updateUserHead(Integer.parseInt(uid), Integer.parseInt(id));
				request.getRequestDispatcher("home.jsp").forward(request,response);
			}
			else {
				request.getRequestDispatcher("error.jsp?msg=���¼").forward(request,response);				
			}				
		}
		else if(action.equals("updateUserInfo")){//�����û���Ϣ����
			HttpSession session=request.getSession(true);
			String uid = (String) session.getAttribute("uid");
			String u_name = (String)request.getParameter("u_name");
			String u_qq = (String)request.getParameter("u_qq");
			String u_Email = (String)request.getParameter("u_Email");
			String u_favourite = (String)request.getParameter("u_favourite");
			String u_dis = (String)request.getParameter("u_dis");
			DBUtil.updateUserInfo(uid, u_name, u_qq, u_Email, u_favourite, u_dis);
			request.getRequestDispatcher("home.jsp").forward(request,response);
		}
		else if(action.equals("admin")){//����������
			request.getRequestDispatcher("admin.jsp").forward(request,response);
		}
		else if(action.equals("addToRecommend")){//����Ƽ�����
			String mid = request.getParameter("mid");
			DBUtil.insertMstx_recommend(Integer.parseInt(mid));
			request.getRequestDispatcher("admin.jsp").forward(request,response);
		}
		else if(action.equals("deleteMstx_recommend")){//ɾ��ÿ���Ƽ�
			String mid = request.getParameter("mid");
			DBUtil.deleteMstx_recommend(Integer.parseInt(mid));
			request.getRequestDispatcher("everydayRecommend.jsp").forward(request,response);
		}
		else if(action.equals("mstx_photo")){//ȥ��ʳ���
			request.getRequestDispatcher("mstx_photo.html").forward(request,response);
		}
		else if(action.equals("insertMstxSort")){//������ʳ����
			String sort = request.getParameter("sort");
			DBUtil.insertMstx_sort(sort);
			request.getRequestDispatcher("admin.jsp").forward(request,response);
		}
	}
	public boolean isLogin(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession(true);
		String u_name = (String) session.getAttribute("u_name");
		if(u_name == null){//û�е�¼��
			return false;
		}
		else{//��¼��
			return true;
		}
	}
}
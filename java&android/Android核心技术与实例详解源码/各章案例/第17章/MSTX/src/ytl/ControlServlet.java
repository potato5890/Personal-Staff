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
		if(action == null){//当action为空时
			request.getRequestDispatcher("error.jsp?msg=action为null").forward(request,response);
			return;
		}
		if(action.equals("toregister")){//到注册
			request.getRequestDispatcher("register.jsp").forward(request,response);
		}
		else if(action.equals("favourite")){//到我的收藏
			if(isLogin(request,response)){//登录过
				request.getRequestDispatcher("favourite.jsp").forward(request,response);
			}
			else{//没有登录
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}
		}
		else if(action.equals("register")){//用户注册动作
			String u_name = request.getParameter("u_name");
			String u_qq	= request.getParameter("u_qq");
			String u_pwd = request.getParameter("u_pwd1");
			String u_Email = request.getParameter("u_Email");
			String u_dis = request.getParameter("u_dis");
			int temNum = DBUtil.insertUser(u_name,u_qq,u_pwd,u_Email,u_dis);
			if(temNum != -1){//插入数据成功
		    	HttpSession session=request.getSession(true);
		    	session.setAttribute("uid",temNum+"");
		    	session.setAttribute("u_name",u_name);
		    	request.getRequestDispatcher("home.jsp").forward(request,response);
			}
			else {//插入失败
				request.getRequestDispatcher("error.jsp").forward(request,response);
			}
		}
		else if(action.equals("exit")){//注销
			HttpSession session=request.getSession(true);
			session.removeAttribute("uid");
			session.removeAttribute("u_name");
			request.getRequestDispatcher("index.jsp").forward(request,response);
		}
		else if(action.equals("login")){//登录动作
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
		else if(action.equals("home")){//到个人主页
			if(isLogin(request, response)){
				request.getRequestDispatcher("home.jsp").forward(request,response);
			}
			else{//没有登录
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}				
		}
		else if(action.equals("index")){//到首页
			request.getRequestDispatcher("index.jsp").forward(request,response);
		}
		else if(action.equals("search")){//到美食搜索页面
			if(isLogin(request,response)){//登录过
				request.getRequestDispatcher("search.jsp").forward(request,response);
			}
			else{//没有登录
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}			
		}
		else if(action.equals("everydayRecommend")){//到每日推荐
			if(isLogin(request,response)){//登录过
				request.getRequestDispatcher("everydayRecommend.jsp").forward(request,response);
			}
			else{//没有登录
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}			
		}
		else if(action.equals("updateRecommend")){//到上传推荐
			if(isLogin(request,response)){//登录过
				request.getRequestDispatcher("updateRecommend.jsp").forward(request,response);
			}
			else{//没有登录
				request.getRequestDispatcher("login.jsp").forward(request,response);
			}			
		}
		else if(action.equals("searchInfo")){//搜索美食
			String type = request.getParameter("type");
			request.getRequestDispatcher("search.jsp?type="+type).forward(request,response);
		}
		else if(action.equals("insertMstxCol")){//收藏动作
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
		else if(action.equals("deleteMstxCol")){//删除收藏动作
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
		else if(action.equals("changeHead")){//到更改头像页面
			HttpSession session=request.getSession(true);
			String uid = (String) session.getAttribute("uid");	
			if(uid != null){
				request.getRequestDispatcher("changeHead.jsp").forward(request,response);
			}
			else {
				request.getRequestDispatcher("error.jsp?msg=请登录").forward(request,response);				
			}			
		}
		else if(action.equals("updateUserHead")){//更新当前用户的头像
			String id = request.getParameter("id");
			HttpSession session=request.getSession(true);
			String uid = (String) session.getAttribute("uid");
			if(uid != null){
				DBUtil.updateUserHead(Integer.parseInt(uid), Integer.parseInt(id));
				request.getRequestDispatcher("home.jsp").forward(request,response);
			}
			else {
				request.getRequestDispatcher("error.jsp?msg=请登录").forward(request,response);				
			}				
		}
		else if(action.equals("updateUserInfo")){//更新用户信息动作
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
		else if(action.equals("admin")){//进入管理界面
			request.getRequestDispatcher("admin.jsp").forward(request,response);
		}
		else if(action.equals("addToRecommend")){//添加推荐动作
			String mid = request.getParameter("mid");
			DBUtil.insertMstx_recommend(Integer.parseInt(mid));
			request.getRequestDispatcher("admin.jsp").forward(request,response);
		}
		else if(action.equals("deleteMstx_recommend")){//删除每日推荐
			String mid = request.getParameter("mid");
			DBUtil.deleteMstx_recommend(Integer.parseInt(mid));
			request.getRequestDispatcher("everydayRecommend.jsp").forward(request,response);
		}
		else if(action.equals("mstx_photo")){//去美食相册
			request.getRequestDispatcher("mstx_photo.html").forward(request,response);
		}
		else if(action.equals("insertMstxSort")){//插入美食种类
			String sort = request.getParameter("sort");
			DBUtil.insertMstx_sort(sort);
			request.getRequestDispatcher("admin.jsp").forward(request,response);
		}
	}
	public boolean isLogin(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession(true);
		String u_name = (String) session.getAttribute("u_name");
		if(u_name == null){//没有登录过
			return false;
		}
		else{//登录过
			return true;
		}
	}
}
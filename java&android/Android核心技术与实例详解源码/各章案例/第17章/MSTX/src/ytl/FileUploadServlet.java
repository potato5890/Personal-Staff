package ytl;
import ytl.DBUtil;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private String uploadPath = "D:\\temp";//上传文件的目录 
    private String tempPath = "D:\\temp\\buffer\\";//临时文件目录 
    File tempPathFile; 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
		this.doPost(req, resp);
	}
    @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
           throws IOException, ServletException {
		request.setCharacterEncoding("gbk");
		String action = request.getParameter("action");
		if(action == null){//当action为空时
			request.getRequestDispatcher("error.jsp").forward(request,response);
			return;
		}
		if(action.equals("insertMstxInfo")){//上传推荐动作
			String info_title = request.getParameter("info_title");
			String info_lon = request.getParameter("info_lon");//经度
			String info_lat = request.getParameter("info_lat");//纬度
			String info_dis = request.getParameter("info_dis");//描述
			String info_sort = request.getParameter("info_sort");//种类
			String info_price_str = request.getParameter("info_price");//价格
			String info_hotel = request.getParameter("info_hotel");//饭店名称
	    	HttpSession session=request.getSession(true);
			String uid = (String) session.getAttribute("uid");
			int info_price = 0;
			try{
				info_price = Integer.parseInt(info_price_str);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			int mid = DBUtil.insertMstx_info(info_title, info_dis, info_lon, info_lat, uid,info_sort,info_price,1,info_hotel);
			
	    	try { 
	    		//实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
	    		DiskFileItemFactory factory = new DiskFileItemFactory(); 
	           
	    		factory.setSizeThreshold(4096);//设置缓冲区大小，这里是4kb 
	    		factory.setRepository(tempPathFile);//设置缓冲区目录 
	           
	    		//用以上工厂实例化上传组件
	    		ServletFileUpload upload = new ServletFileUpload(factory); 

	    		upload.setSizeMax(3*1024*1024);//设置最大文件尺寸，这里是3MB 

	    		List<FileItem> items = upload.parseRequest(request);//得到所有的文件 
	    		Iterator<FileItem> i = items.iterator(); 
	    		while (i.hasNext()) {
	    			FileItem fi = (FileItem) i.next(); 
	    			String fileName = fi.getName(); 
	    			if (fileName != null){ 
	    				File fullFile = new File(fi.getName()); 
	    				DBUtil.insertMstx_image(fullFile,mid);//存储到数据库
//	   					存储到硬盘中的文件
//	                  	File savedFile = new File(uploadPath, fullFile.getName()); 
//	                  	fi.write(savedFile); 
	    			} 
	    		}
	    		request.getRequestDispatcher("index.jsp").forward(request,response);
	    	} catch (Exception e) {//可以跳转出错页面 
	    		e.printStackTrace();//打印异常信息 
	    		request.getRequestDispatcher("error.jsp").forward(request,response);
	    	} 			
		}
		else if(action.equals("insertHeadImage")){//上传头像动作
			String tdis = request.getParameter("tdis");
			if(tdis == null){
				tdis = "";
			}
	    	HttpSession session=request.getSession(true);
			String uid = (String) session.getAttribute("uid");
	    	try {
	    		//实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
	    		DiskFileItemFactory factory = new DiskFileItemFactory(); 
	    		factory.setSizeThreshold(4096);//设置缓冲区大小，这里是4kb 
	    		factory.setRepository(tempPathFile);//设置缓冲区目录 

	    		//用以上工厂实例化上传组件
	    		ServletFileUpload upload = new ServletFileUpload(factory); 

	    		upload.setSizeMax(3*1024*1024);//设置最大文件尺寸，这里是3MB

	    		List<FileItem> items = upload.parseRequest(request);//得到所有的文件 
	    		Iterator<FileItem> i = items.iterator(); 
	    		while (i.hasNext()) {
	    			FileItem fi = (FileItem) i.next(); 
	    			String fileName = fi.getName(); 
	    			if (fileName != null){
	    				if(!fileName.equals("")){
	    					File fullFile = new File(fi.getName()); 
	    					DBUtil.insertMstx_head(tdis, fullFile, Integer.parseInt(uid));//存储到数据库	    					
	    				}
	    			}
	    		}
	    		request.getRequestDispatcher("changeHead.jsp").forward(request,response);
	    	} catch (Exception e) {//可以跳转出错页面 
	    		e.printStackTrace();//打印异常信息 
	    		request.getRequestDispatcher("error.jsp").forward(request,response);
	    	}
		}
		else if(action.equals("insertMstxAds")){//上传广告图片
			String gid = request.getParameter("gid");
			if(gid == null){
				gid = "0";
			}
			
			int id = DBUtil.updateIntoAdsInfo(Integer.parseInt(gid));
	    	try {
	    		//实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
	    		DiskFileItemFactory factory = new DiskFileItemFactory(); 
	    		factory.setSizeThreshold(4096);//设置缓冲区大小，这里是4kb 
	    		factory.setRepository(tempPathFile);//设置缓冲区目录 

	    		//用以上工厂实例化上传组件
	    		ServletFileUpload upload = new ServletFileUpload(factory); 

	    		upload.setSizeMax(3*1024*1024);//设置最大文件尺寸，这里是3MB

	    		List<FileItem> items = upload.parseRequest(request);//得到所有的文件 
	    		Iterator<FileItem> i = items.iterator(); 
	    		while (i.hasNext()) {
	    			FileItem fi = (FileItem) i.next();
	    			String fileName = fi.getName(); 
	    			if (fileName != null){
	    				if(!fileName.equals("")){
	    					File fullFile = new File(fi.getName()); 
	    					DBUtil.insertMstx_Ads(fullFile, id);//存储到数据库	    					
	    				}
	    			}
	    		}
	    		request.getRequestDispatcher("admin.jsp").forward(request,response);
	    	} catch (Exception e) {//可以跳转出错页面 
	    		e.printStackTrace();//打印异常信息 
	    		request.getRequestDispatcher("error.jsp").forward(request,response);
	    	}			
		}
    }
    public void init() throws ServletException {//初始化方法
    	File tempPathFile = new File(tempPath);
    	if (!tempPathFile.exists()){
    		tempPathFile.mkdirs();
    	}
    }
}
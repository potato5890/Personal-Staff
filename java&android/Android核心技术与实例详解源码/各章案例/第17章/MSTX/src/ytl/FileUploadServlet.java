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
	//private String uploadPath = "D:\\temp";//�ϴ��ļ���Ŀ¼ 
    private String tempPath = "D:\\temp\\buffer\\";//��ʱ�ļ�Ŀ¼ 
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
		if(action == null){//��actionΪ��ʱ
			request.getRequestDispatcher("error.jsp").forward(request,response);
			return;
		}
		if(action.equals("insertMstxInfo")){//�ϴ��Ƽ�����
			String info_title = request.getParameter("info_title");
			String info_lon = request.getParameter("info_lon");//����
			String info_lat = request.getParameter("info_lat");//γ��
			String info_dis = request.getParameter("info_dis");//����
			String info_sort = request.getParameter("info_sort");//����
			String info_price_str = request.getParameter("info_price");//�۸�
			String info_hotel = request.getParameter("info_hotel");//��������
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
	    		//ʵ����һ��Ӳ���ļ�����,���������ϴ����ServletFileUpload
	    		DiskFileItemFactory factory = new DiskFileItemFactory(); 
	           
	    		factory.setSizeThreshold(4096);//���û�������С��������4kb 
	    		factory.setRepository(tempPathFile);//���û�����Ŀ¼ 
	           
	    		//�����Ϲ���ʵ�����ϴ����
	    		ServletFileUpload upload = new ServletFileUpload(factory); 

	    		upload.setSizeMax(3*1024*1024);//��������ļ��ߴ磬������3MB 

	    		List<FileItem> items = upload.parseRequest(request);//�õ����е��ļ� 
	    		Iterator<FileItem> i = items.iterator(); 
	    		while (i.hasNext()) {
	    			FileItem fi = (FileItem) i.next(); 
	    			String fileName = fi.getName(); 
	    			if (fileName != null){ 
	    				File fullFile = new File(fi.getName()); 
	    				DBUtil.insertMstx_image(fullFile,mid);//�洢�����ݿ�
//	   					�洢��Ӳ���е��ļ�
//	                  	File savedFile = new File(uploadPath, fullFile.getName()); 
//	                  	fi.write(savedFile); 
	    			} 
	    		}
	    		request.getRequestDispatcher("index.jsp").forward(request,response);
	    	} catch (Exception e) {//������ת����ҳ�� 
	    		e.printStackTrace();//��ӡ�쳣��Ϣ 
	    		request.getRequestDispatcher("error.jsp").forward(request,response);
	    	} 			
		}
		else if(action.equals("insertHeadImage")){//�ϴ�ͷ����
			String tdis = request.getParameter("tdis");
			if(tdis == null){
				tdis = "";
			}
	    	HttpSession session=request.getSession(true);
			String uid = (String) session.getAttribute("uid");
	    	try {
	    		//ʵ����һ��Ӳ���ļ�����,���������ϴ����ServletFileUpload
	    		DiskFileItemFactory factory = new DiskFileItemFactory(); 
	    		factory.setSizeThreshold(4096);//���û�������С��������4kb 
	    		factory.setRepository(tempPathFile);//���û�����Ŀ¼ 

	    		//�����Ϲ���ʵ�����ϴ����
	    		ServletFileUpload upload = new ServletFileUpload(factory); 

	    		upload.setSizeMax(3*1024*1024);//��������ļ��ߴ磬������3MB

	    		List<FileItem> items = upload.parseRequest(request);//�õ����е��ļ� 
	    		Iterator<FileItem> i = items.iterator(); 
	    		while (i.hasNext()) {
	    			FileItem fi = (FileItem) i.next(); 
	    			String fileName = fi.getName(); 
	    			if (fileName != null){
	    				if(!fileName.equals("")){
	    					File fullFile = new File(fi.getName()); 
	    					DBUtil.insertMstx_head(tdis, fullFile, Integer.parseInt(uid));//�洢�����ݿ�	    					
	    				}
	    			}
	    		}
	    		request.getRequestDispatcher("changeHead.jsp").forward(request,response);
	    	} catch (Exception e) {//������ת����ҳ�� 
	    		e.printStackTrace();//��ӡ�쳣��Ϣ 
	    		request.getRequestDispatcher("error.jsp").forward(request,response);
	    	}
		}
		else if(action.equals("insertMstxAds")){//�ϴ����ͼƬ
			String gid = request.getParameter("gid");
			if(gid == null){
				gid = "0";
			}
			
			int id = DBUtil.updateIntoAdsInfo(Integer.parseInt(gid));
	    	try {
	    		//ʵ����һ��Ӳ���ļ�����,���������ϴ����ServletFileUpload
	    		DiskFileItemFactory factory = new DiskFileItemFactory(); 
	    		factory.setSizeThreshold(4096);//���û�������С��������4kb 
	    		factory.setRepository(tempPathFile);//���û�����Ŀ¼ 

	    		//�����Ϲ���ʵ�����ϴ����
	    		ServletFileUpload upload = new ServletFileUpload(factory); 

	    		upload.setSizeMax(3*1024*1024);//��������ļ��ߴ磬������3MB

	    		List<FileItem> items = upload.parseRequest(request);//�õ����е��ļ� 
	    		Iterator<FileItem> i = items.iterator(); 
	    		while (i.hasNext()) {
	    			FileItem fi = (FileItem) i.next();
	    			String fileName = fi.getName(); 
	    			if (fileName != null){
	    				if(!fileName.equals("")){
	    					File fullFile = new File(fi.getName()); 
	    					DBUtil.insertMstx_Ads(fullFile, id);//�洢�����ݿ�	    					
	    				}
	    			}
	    		}
	    		request.getRequestDispatcher("admin.jsp").forward(request,response);
	    	} catch (Exception e) {//������ת����ҳ�� 
	    		e.printStackTrace();//��ӡ�쳣��Ϣ 
	    		request.getRequestDispatcher("error.jsp").forward(request,response);
	    	}			
		}
    }
    public void init() throws ServletException {//��ʼ������
    	File tempPathFile = new File(tempPath);
    	if (!tempPathFile.exists()){
    		tempPathFile.mkdirs();
    	}
    }
}
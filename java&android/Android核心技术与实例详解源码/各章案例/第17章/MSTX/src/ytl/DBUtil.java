package ytl;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class DBUtil {
	public static Connection getConnection(){//得到数据库连接
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=DriverManager.getConnection("jdbc:mysql://localhost/mstx","root","tiger");			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		/*数据源*/
//		try{
//			Context ctx = new InitialContext();
//			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql");
//			conn = ds.getConnection(); 
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
		return conn;
	}
	public static int getMstxInfoCount(){
		int result = 0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con = getConnection();
			st = con.createStatement();
			rs = st.executeQuery("select count(mid) from mstx_info");
			if(rs.next()){
				result = rs.getInt(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public static int updateIntoAdsInfo(int gid){
		int result = gid;
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = con.prepareStatement("select * from mstx_ads where gid=?");
			pstmt.setInt(1, gid);
			rs = pstmt.executeQuery();//查询
			if(!rs.next()){
				result = DBUtil.getMaxNumber("mstx_ads");
				DBUtil.updateMaxNumber(5);//将该字段值加1
				pstmt = con.prepareStatement("insert into mstx_ads(gid,gdis) values(?,?)");
				pstmt.setInt(1, result);
				pstmt.setString(2, "");
				pstmt.executeUpdate();
			}
		}
		catch(Exception e){//捕获异常
			e.printStackTrace();//打印异常
		}
		finally{
			try{
				if(rs != null){
					rs.close();//关闭
					rs = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}	
		return result;
	}
	
	public static ArrayList<Blob> getMstx_image(int[] mid){//得到美食图片
		ArrayList<Blob> result = new ArrayList<Blob>();
		for(int i=0; i<mid.length; i++){
			ArrayList<MSTXImage> temp =  getMSTXImage(mid[i]);
			if(temp.size()>0){
				MSTXImage mi = temp.get(0);//用于手机端显示，只取第一张图片即可
				Blob b = mi.getB();
				result.add(b);
			}
		}
		return result;
	}
	
	public static int getMstxInfoCountForPhone(String infoValues, int searchSort,String startPrice,String endPrice){//返回满足搜索添加的总个数
		int result = 0;
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql;
		if(searchSort == -1){
			if(startPrice.trim().equals("")){//当起始价格为空时
				if(endPrice.trim().equals("")){//截止价格也为空时
					//LIMIT M,N   M+1条记录开始（记录号从1开始）  N要几条记录   例如要 5-8条记录  LIMIT 4,4
					sql = "select count(*) from " +
							"mstx_info where info_title like '%"+infoValues+"%';";					
				}
				else{
					sql = "select count(*) from " +
							"mstx_info where info_price<"+endPrice+" and info_title like '%"+infoValues+"%';";					
				}
			}
			else {//起始价格不为空
				if(endPrice.trim().equals("")){//截止价格为空时
					//LIMIT M,N   M+1条记录开始（记录号从1开始）  N要几条记录   例如要 5-8条记录  LIMIT 4,4
					sql = "select count(*) from " +
							"mstx_info where info_price>"+startPrice+" and info_title like '%"+infoValues+"%';";						
				}
				else{//都不为空时
					sql = "select count(*) from " +
					"mstx_info where info_price>"+startPrice+" and info_price<"+endPrice+" and info_title like '%"+infoValues+"%';";					
				}
			}
		}
		else {
			if(startPrice.trim().equals("")){//当起始价格为空时
				if(endPrice.trim().equals("")){//截止价格也为空时
					//LIMIT M,N   M+1条记录开始（记录号从1开始）  N要几条记录   例如要 5-8条记录  LIMIT 4,4
					sql = "select count(*) from mstx_info " +
					"where info_sort="+searchSort+" and info_title like '%"+infoValues+"%';";						
				}
				else{//起始为空，截止不为空
					sql = "select count(*) from mstx_info " +
					"where info_sort="+searchSort+" and info_price>"+startPrice+" " +
					"and info_title like '%"+infoValues+"%';";				
				}
			}
			else {//起始价格不为空
				if(endPrice.trim().equals("")){//截止价格为空时
					sql = "select count(*) from mstx_info where info_sort="+searchSort+" and info_price>"+startPrice+" " +
					"and info_title like '%"+infoValues+"%';";					
				}
				else{//都不为空时					
					sql = "select count(*) from mstx_info " +
							"where info_price<"+endPrice+" and info_sort="+searchSort+" and info_price>"+startPrice+" " +
							"and info_title like '%"+infoValues+"%';";
				}
			}
		}
		try{
			con=getConnection();
			st=con.createStatement();

			//执行检索		
			rs=st.executeQuery(sql);

			if(rs.next()){
				result = rs.getInt(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(st != null){
					st.close();
					st = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}				
		return result;
	}
	
	public static List<String[]> getMstxInfoForPhone(String infoValues, int searchSort,String startPrice,String endPrice,int span,int currentPageNo){
		List<String[]> result=new ArrayList<String[]>();
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql;
		int start=span*(currentPageNo-1);//计算出起始记录编号
		if(searchSort == -1){
			if(startPrice.trim().equals("")){//当起始价格为空时
				if(endPrice.trim().equals("")){//截止价格也为空时
					//LIMIT M,N   M+1条记录开始（记录号从1开始）  N要几条记录   例如要 5-8条记录  LIMIT 4,4
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from " +
							"mstx_info where info_title like '%"+infoValues+"%' limit "+start+","+span+";";					
				}
				else{
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from " +
							"mstx_info where info_price<"+endPrice+" and info_title like '%"+infoValues+"%' limit "+start+","+span+";";					
				}
			}
			else {//起始价格不为空
				if(endPrice.trim().equals("")){//截止价格为空时
					//LIMIT M,N   M+1条记录开始（记录号从1开始）  N要几条记录   例如要 5-8条记录  LIMIT 4,4
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from " +
							"mstx_info where info_price>"+startPrice+" and info_title like '%"+infoValues+"%' limit "+start+","+span+";";						
				}
				else{//都不为空时
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from " +
					"mstx_info where info_price>"+startPrice+" and info_price<"+endPrice+" and info_title like '%"+infoValues+"%' limit "+start+","+span+";";					
				}
			}
		}
		else {
			if(startPrice.trim().equals("")){//当起始价格为空时
				if(endPrice.trim().equals("")){//截止价格也为空时
					//LIMIT M,N   M+1条记录开始（记录号从1开始）  N要几条记录   例如要 5-8条记录  LIMIT 4,4
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info " +
					"where info_sort="+searchSort+" and info_title like '%"+infoValues+"%' limit "+start+","+span+";";						
				}
				else{//起始为空，截止不为空
//					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from " +
//							"mstx_info where info_sort="+searchSort+" info_price<"+endPrice+" and " +
//									"info_title like '%"+infoValues+"%' limit "+start+","+span+";";
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info " +
					"where info_sort="+searchSort+" and info_price>"+startPrice+" " +
					"and info_title like '%"+infoValues+"%' limit "+start+","+span+";";				
				}
			}
			else {//起始价格不为空
				if(endPrice.trim().equals("")){//截止价格为空时
					//LIMIT M,N   M+1条记录开始（记录号从1开始）  N要几条记录   例如要 5-8条记录  LIMIT 4,4
//					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from " +
//							"mstx_info where info_sort="+searchSort+" info_price>"+startPrice+" and info_title " +
//									"like '%"+infoValues+"%' limit "+start+","+span+";";		
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info " +
					"where info_sort="+searchSort+" and info_price>"+startPrice+" " +
					"and info_title like '%"+infoValues+"%' limit "+start+","+span+";";					
				}
				else{//都不为空时
//					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from " +
//					"mstx_info where info_sort="+searchSort+" info_price>"+startPrice+" and info_price<"+endPrice+" and info_title like '%"+infoValues+"%' limit "+start+","+span+";";
					sql = "select hotel_name,info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info " +
							"where info_price<"+endPrice+" and info_sort="+searchSort+" and info_price>"+startPrice+" " +
							"and info_title like '%"+infoValues+"%' limit "+start+","+span+";";
				}
			}
		}
		try{
			con=getConnection();
			//由于MySQL默认创建的是可滚动的结果集合，因此要在创建语句时指定结果集不可滚动，只读
			st=con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			//执行检索		
			rs=st.executeQuery(sql);

			while(rs.next()){
				String[] str = new String[8];
				str[0] = new String(rs.getString(1).getBytes("ISO8859_1"), "GBK");//hotel_name
				str[1] = new String(rs.getString(2).getBytes("ISO8859_1"), "GBK");//info_title
				str[2] = new String(rs.getString(3).getBytes("ISO8859_1"), "GBK");//info_dis
				str[3] = new String(rs.getString(4).getBytes("ISO8859_1"), "GBK");//info_lon
				str[4] = new String(rs.getString(5).getBytes("ISO8859_1"), "GBK");//info_lat
				str[5] = rs.getDate(6).toString();//info_time
				str[6] = new String(rs.getString(7).getBytes("ISO8859_1"), "GBK");//uid
				str[7] = new String(rs.getString(8).getBytes("ISO8859_1"), "GBK");//mid

				result.add(str);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(st != null){
					st.close();
					st = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}		
		return result;
	}
	
	public static ArrayList<MstxInfo> getMstxInfoByName(String name, int type,int span,int currentPageNo, int where){//currentPageNo从1开始
		ArrayList<MstxInfo> result=new ArrayList<MstxInfo>();
		String info_name = name;
		if(where == 2){
			try {
				info_name = new String(name.getBytes("GBK"), "ISO8859_1");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();//打印异常
			}
		}

		String sql;
		if(currentPageNo == -1){
			sql = "select info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info;";
		}
		else {
			int start=span*(currentPageNo-1);//计算出起始记录编号
			if(name.trim().equals("")){
				//LIMIT M,N   M+1条记录开始（记录号从1开始）  N要几条记录   例如要 5-8条记录  LIMIT 4,4
				sql = "select info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info limit "+start+","+span+";";
			}
			else {
				switch(type){
				case 1:
					sql = "select info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info where info_title like '%"+info_name+"%' limit "+start+","+span+";";
					break;
				case 2://按种类
					sql = "select info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info where info_sort like '%"+info_name+"%' limit "+start+","+span+";";
					break;		
				case 3://按价格
					sql = "select info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info where info_price = "+info_name+" limit "+start+","+span+";";
					break;
				default:
					sql = "select info_title,info_dis,info_lon,info_lat,info_time,uid,mid from mstx_info limit "+start+","+span+";";	
				}
			}			
		}

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			con=getConnection();
			//由于MySQL默认创建的是可滚动的结果集合，因此要在创建语句时指定结果集不可滚动，只读
			st=con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			//执行检索		
			rs=st.executeQuery(sql);

			while(rs.next()){
				String info_title = new String(rs.getString("info_title").getBytes("ISO8859_1"), "GBK");
				String info_dis = new String(rs.getString("info_dis").getBytes("ISO8859_1"), "GBK");
				String info_lon = new String(rs.getString("info_lon").getBytes("ISO8859_1"), "GBK");
				String info_lat = new String(rs.getString("info_lat").getBytes("ISO8859_1"), "GBK");
				String uid = new String(rs.getString("uid").getBytes("ISO8859_1"), "GBK");
				String mid = new String(rs.getString("mid").getBytes("ISO8859_1"), "GBK");
				Date info_time = rs.getDate("info_time");
				
				MstxInfo mi = new MstxInfo(info_title, info_dis, info_lon, info_lat, info_time, uid,mid);
				result.add(mi);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(st != null){
					st.close();
					st = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
		return result;
	}
	
	public static boolean isAdmin(String uid){//检查是否为管理员
		boolean result = false;
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = con.prepareStatement("select u_admin from mstx_user where uid=?");
			pstmt.setInt(1, Integer.parseInt(uid));
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getBoolean(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static void insertMstx_recommend(int mid){//插入每日推荐
		int id = DBUtil.getMaxNumber("mstx_recommend");
		DBUtil.updateMaxNumber(7);//将该字段值加1
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = con.prepareStatement("insert into mstx_recommend(id,mid) values(?,?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, mid);
			pstmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
	}
	
	public static void updateUserInfo(String uid,String u_name,String u_qq,String u_Email,String u_favourite,String u_dis){
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		try{
			pstmt = con.prepareStatement("update mstx_user set u_name=?, u_qq=?, u_Email=?, u_hobby=?, u_dis=? where uid=?;");
			pstmt.setString(1, new String(u_name.getBytes("GBK"), "ISO8859_1"));
			pstmt.setString(2, new String(u_qq.getBytes("GBK"), "ISO8859_1"));
			pstmt.setString(3, new String(u_Email.getBytes("GBK"), "ISO8859_1"));
			pstmt.setString(4, new String(u_favourite.getBytes("GBK"), "ISO8859_1"));
			pstmt.setString(5, new String(u_dis.getBytes("GBK"), "ISO8859_1"));
			pstmt.setInt(6, Integer.parseInt(uid));
			pstmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}			
		}
	}
	
	public static void insertMstx_head(String tdis, File fileName, int uid){
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		FileInputStream fis = null; 
		int id = DBUtil.getMaxNumber("mstx_head");
		DBUtil.updateMaxNumber(1);//将该字段值加1
		try {
			fis = new FileInputStream(fileName); 
			pstmt = con.prepareStatement("insert into mstx_head(tid,tdis,tdata,uid) values(?,?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setString(2, tdis);
			pstmt.setBinaryStream(3,fis,(int)fileName.length());
			pstmt.setInt(4, uid);
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(fis != null){
					fis.close();
					fis = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}		
	}

	public static void insertMstx_Ads(File fileName, int gid){//插入广告图片
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		FileInputStream fis = null; 
		int id = DBUtil.getMaxNumber("mstx_ads_image");
		DBUtil.updateMaxNumber(6);//将该字段值加1
		try {
			fis = new FileInputStream(fileName); 
			pstmt = con.prepareStatement("insert into mstx_ads_image(gpid,gid,gdata) values(?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, gid);
			pstmt.setBinaryStream(3,fis,(int)fileName.length()); 
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(fis != null){
					fis.close();
					fis = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}		
	}
	
	public static void insertMstx_image(byte[] image,int mid){
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		FileInputStream fis = null; 
		int id = DBUtil.getMaxNumber("mstx_image");
		DBUtil.updateMaxNumber(4);//将该字段值加1
		try {
			pstmt = con.prepareStatement("insert into mstx_image(id,mid,image_data) values(?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, mid);
			pstmt.setBytes(3, image);
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(fis != null){
					fis.close();
					fis = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}	
	}
	
	
	public static void insertMstx_image(File fileName, int mid){//插入美食图片
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		FileInputStream fis = null; 
		int id = DBUtil.getMaxNumber("mstx_image");
		DBUtil.updateMaxNumber(4);//将该字段值加1
		try {
			fis = new FileInputStream(fileName); 
			pstmt = con.prepareStatement("insert into mstx_image(id,mid,image_data) values(?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, mid);
			pstmt.setBinaryStream(3,fis,(int)fileName.length()); 
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(fis != null){
					fis.close();
					fis = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
	}
	
	public static void insertMstxCol(String mid, String uid, String comment){
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("insert into mstx_col(mid,uid,comment) values(?,?,?)");
			pstmt.setString(1, mid);
			pstmt.setString(2, uid);
			pstmt.setString(3, comment);

			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}	
		}		
	}
	
	public static void deleteMstxCol(String mid, String uid){
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("delete from mstx_col where mid=? and uid=?");
			pstmt.setString(1, mid);
			pstmt.setString(2, uid);

			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}	
		}			
	}
	
	public static int insertMstx_info(String info_title,String info_dis,String info_lon,String info_lat,String uid,String info_sort,int info_price,int where,String mshotelName){//插入美食信息
		int mid = DBUtil.getMaxNumber("mstx_info");
		DBUtil.updateMaxNumber(3);//将该字段值加1
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("insert into mstx_info(mid,info_title,info_dis,info_lon,info_lat,uid,info_sort,info_price,hotel_name) values(?,?,?,?,?,?,?,?,?)");
			if(where == 2){//手机来的
				pstmt.setInt(1, mid);
				pstmt.setString(2, new String(info_title.getBytes("GBK"),"ISO-8859-1"));
				pstmt.setString(3, new String(info_dis.getBytes("GBK"),"ISO-8859-1"));
				pstmt.setDouble(4, Double.parseDouble(info_lon));
				pstmt.setDouble(5, Double.parseDouble(info_lat));
				pstmt.setInt(6, Integer.parseInt(uid));
				pstmt.setInt(7, Integer.parseInt(info_sort));
				pstmt.setInt(8, info_price);
				pstmt.setString(9, new String(mshotelName.getBytes("GBK"),"ISO-8859-1"));
			}
			else{
				pstmt.setInt(1, mid);
				pstmt.setString(2, info_title);
				pstmt.setString(3, info_dis);
				pstmt.setDouble(4, Double.parseDouble(info_lon));
				pstmt.setDouble(5, Double.parseDouble(info_lat));
				pstmt.setInt(6, Integer.parseInt(uid));
				pstmt.setInt(7, Integer.parseInt(info_sort));
				pstmt.setInt(8, info_price);	
				pstmt.setString(9, mshotelName);
			}

			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}	
		}
		return mid;
	}
	
	public static ArrayList<MSTXImage> getMSTXImage(int mid){//得到美食图片
		ArrayList<MSTXImage> result = new ArrayList<MSTXImage>();
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = con.prepareStatement("select * from mstx_image where mid=?");
			pstmt.setInt(1, mid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				Blob b = rs.getBlob("image_data");
				Date image_time  = rs.getDate("image_time");	
				MSTXImage mi = new MSTXImage(id, mid, b, image_time);
				result.add(mi);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}			
		}
		return result;
	}
	
	public static UserInfo getUserByUid(String uid){//根据用户ID得到用户所有信息
		UserInfo result = null;
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = con.prepareStatement("select * from mstx_user where uid=?");
			pstmt.setString(1,new String(uid.getBytes("GBK"), "ISO8859_1"));
			rs = pstmt.executeQuery();
			if(rs.next()){
				String u_name = new String(rs.getString("u_name").getBytes("ISO8859_1"), "GBK");
				String u_qq = new String(rs.getString("u_qq").getBytes("ISO8859_1"), "GBK");
				String u_Email = new String(rs.getString("u_Email").getBytes("ISO8859_1"), "GBK");
				String u_dis = new String(rs.getString("u_dis").getBytes("ISO8859_1"), "GBK");
				int u_head = rs.getInt("u_head");
				boolean u_admin = rs.getBoolean("u_admin");
				String u_mood = new String((rs.getString("u_mood")==null?"":rs.getString("u_mood")).getBytes("ISO8859_1"), "GBK");
				int u_integral = rs.getInt("u_integral");//积分
				String u_hobby = new String((rs.getString("u_hobby")==null?"":rs.getString("u_hobby")).getBytes("ISO8859_1"), "GBK");
				int u_level = rs.getInt("u_level");
				int u_number = rs.getInt("u_number");//访问次数	
				result = new UserInfo(Integer.parseInt(uid), u_name, u_qq, u_Email, u_dis, u_head, u_admin, u_mood, u_integral, u_hobby, u_level, u_number);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}			
		}		
		return result;
	}
	
	public static List<Integer> getMstxAdsGpid(int ID){//根据编号得到所有广告图片
		ArrayList<Integer> result = new ArrayList<Integer>();
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = con.prepareStatement("select gpid from mstx_ads_image where gid=?");
			pstmt.setInt(1, ID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int gid = rs.getInt(1);
				result.add(gid);
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}			
		}		
		return result;
	}
	
	public static List<Integer> getMSTXAdsID(){//得到所有广告位置的编号
		List<Integer> result = new ArrayList<Integer>();
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = con.prepareStatement("select gid from mstx_ads;");
			rs = pstmt.executeQuery();
			while(rs.next()){
				Integer i = rs.getInt(1);
				result.add(i);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
		return result;
	}
	
	public static Blob getMstxAdsImageByGpid(int gpid){
		Blob result = null;
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = con.prepareStatement("select gdata from mstx_ads_image where gpid = ?;");
			pstmt.setInt(1, gpid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getBlob(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
		return result;
	}
	
	public static void updateUserHead(int uid, int u_head){//更新用户头像
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		try{
			pstmt = con.prepareStatement("update mstx_user set u_head=? where uid=?");
			pstmt.setInt(1, u_head);
			pstmt.setInt(2, uid);
			pstmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}			
		}	
	}
	
	public static ArrayList<Integer> getHeadNumber(){//得到所有头像的id
		ArrayList<Integer> result = new ArrayList<Integer>();
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		try{
			pstmt = con.prepareStatement("select tid from mstx_head");
			rs = pstmt.executeQuery();
			while(rs.next()){
				Integer tid = rs.getInt("tid");
				result.add(tid);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}			
		}			
		return result;
	}
	
	public static void insertMstx_sort(String sort){//插入新种类
		int sid = DBUtil.getMaxNumber("mstx_sort");
		DBUtil.updateMaxNumber(8);//将该字段值加1
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		
		try{
			pstmt = con.prepareStatement("insert into mstx_sort(sid,info_sort) values("+sid+", '"+new String(sort.getBytes("GBK"),"ISO-8859-1")+"')");
			pstmt.executeUpdate();
		}
		catch(Exception e){//捕获异常
			e.printStackTrace();//打印异常信息
		}
		finally{
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
	}
	
	public static List<String[]> getMstx_sort(){//得到美食的全部种类
		List<String[]> result = new ArrayList<String[]>();
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = con.prepareStatement("select sid,info_sort from mstx_sort;");
			rs = pstmt.executeQuery();//查询
			while(rs.next()){
				String sid = rs.getString(1);
				String info_sort = rs.getString(2);
				String[] strs = new String[2];
				strs[0] = sid;
				strs[1] = new String(info_sort.getBytes("ISO-8859-1"),"GBK");
				result.add(strs);
			}
		}
		catch(Exception e){//捕获异常
			e.printStackTrace();//打印异常信息
		}
		finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
		return result;
	}
	
	public static ArrayList<MSTXHeadImage> getHeadImage(int tid){//得到指定id的头像图片
		ArrayList<MSTXHeadImage> result = new ArrayList<MSTXHeadImage>();
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try{
			pstmt = con.prepareStatement("select * from mstx_head where tid=?");
			pstmt.setInt(1, tid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String tdis = rs.getString("tdis");
				Blob b = rs.getBlob("tdata");
				int uid = rs.getInt("uid");
				MSTXHeadImage mi = new MSTXHeadImage(tid, tdis, b, uid);
				result.add(mi);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}			
		}		
		
		return result;
	}

	public static ArrayList<MstxInfo> getMstx_recommend(){//得到所有的每日推荐
		ArrayList<MstxInfo> result = new ArrayList<MstxInfo>();
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = con.prepareStatement("select info_title,info_dis,info_lon,info_lat,a.info_time,a.uid,a.mid from mstx_info as a, mstx_recommend as b where a.mid = b.mid");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				String info_title = new String(rs.getString(1).getBytes("ISO-8859-1"),"GBK");
				String info_dis = new String(rs.getString(2).getBytes("ISO-8859-1"),"GBK");
				String info_lon = new String(rs.getString(3).getBytes("ISO-8859-1"),"GBK");
				String info_lat = new String(rs.getString(4).getBytes("ISO-8859-1"),"GBK");
				Date info_time = rs.getDate(5);
				String uid = new String(rs.getString(6).getBytes("ISO-8859-1"),"GBK");
				String mid = new String(rs.getString(7).getBytes("ISO-8859-1"),"GBK");
				MstxInfo mi = new MstxInfo(info_title, info_dis, info_lon, info_lat, info_time, uid, mid);
				result.add(mi);
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}			try{
			if(rs != null){
				rs.close();
				rs = null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		try{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		try{
			if(con != null){
				con.close();
				con = null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static void deleteMstx_recommend(int mid){//删除每日推荐
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		try{
			pstmt = con.prepareStatement("delete from mstx_recommend where mid=?");
			pstmt.setInt(1, mid);
			pstmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
	}
	
	public static ArrayList<MstxInfo> getFavourite(String uid){//得到我的收藏所有美食
		ArrayList<MstxInfo> result = new ArrayList<MstxInfo>();
		String sql = "select info_title,info_dis,info_lon,info_lat,info_time,a.uid,a.mid " +
				"from mstx_info as a,mstx_col as b where a.mid=b.mid and b.uid = "+uid+";";
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		try{
			st = con.createStatement();//创建语句
			rs = st.executeQuery(sql);
			while(rs.next()){
				String info_title = new String(rs.getString("info_title").getBytes("ISO8859_1"), "GBK");
				String info_dis = new String(rs.getString("info_dis").getBytes("ISO8859_1"), "GBK");
				String info_lon = new String(rs.getString("info_lon").getBytes("ISO8859_1"), "GBK");
				String info_lat = new String(rs.getString("info_lat").getBytes("ISO8859_1"), "GBK");
				String mid = new String(rs.getString("mid").getBytes("ISO8859_1"), "GBK");
				Date info_time = rs.getDate("info_time");

				MstxInfo mi = new MstxInfo(info_title, info_dis, info_lon, info_lat, info_time, uid,mid);
				result.add(mi);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(st != null){
					st.close();
					st = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
		return result;
	}
	
	public static String checkUser(String uid,String u_pwd){//检查用户名是否存在
		String result = null;
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		try{
			st = con.createStatement();//创建语句
			rs = st.executeQuery("select u_name from mstx_user where uid = '"+
					uid+"' and u_pwd = '"+u_pwd+"';");
			if(rs.next()){
				result = new String(rs.getString(1).getBytes("ISO8859_1"), "GBK");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		} finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(st != null){
					st.close();
					st = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
		return result;
	}	
	
	public static int insertUser(String u_name,String u_qq,String u_pwd,String u_Email,String u_dis){//添加用户
		int uid = DBUtil.getMaxNumber("mstx_user");
		DBUtil.updateMaxNumber(2);//将该字段值加1
		Connection con = getConnection();
		Statement st = null;
		try {
			u_name = new String(u_name.getBytes("GBK"), "ISO8859_1");
			u_dis = new String(u_dis.getBytes("GBK"), "ISO8859_1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			st = con.createStatement();
			String sql = "insert into mstx_user" +
				"(u_admin,uid,u_name,u_qq,u_pwd,u_Email,u_dis) " +
				"values(false,"+uid+",'"+u_name+"','"+((u_qq.equals(""))?0:u_qq)+"','"+u_pwd+"','"+u_Email+"','"+u_dis+"');";
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				if(st != null){
					st.close();
					st = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
		}
		return uid;
	}
	public static void updateMaxNumber(int what){//更改mstx_max中相应列的值
		Connection con = getConnection();//得到数据库连接
		Statement st = null;
		String sql = "";
		try {
			st = con.createStatement();
			switch(what){
			case 1:
				sql = "update mstx_max set mstx_head = mstx_head + 1 ";
				break;
			case 2:
				sql = "update mstx_max set mstx_user  = mstx_user  + 1 ";
				break;
			case 3:
				sql = "update mstx_max set mstx_info   = mstx_info   + 1 ";
				break;
			case 4:
				sql = "update mstx_max set mstx_image  = mstx_image  + 1 ";
				break;
			case 5:
				sql = "update mstx_max set mstx_ads   = mstx_ads   + 1 ";
				break;			
			case 6:
				sql = "update mstx_max set mstx_ads_image   = mstx_ads_image   + 1 ";
				break;	
			case 7:
				sql = "update mstx_max set mstx_recommend   = mstx_recommend   + 1 ";
				break;
			case 8:
				sql = "update mstx_max set mstx_sort  = mstx_sort   + 1 ";
				break;
			}
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(st != null){
					st.close();
					st = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}	
		}
	}
	public static int getMaxNumber(String what){//得到最大编号，即某个表的主键自加时的最大
		int result = 0;
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from mstx_max");
			rs.next();
			result = rs.getInt(what);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(st != null){
					st.close();
					st = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}	
			try{
				if(con != null){
					con.close();
					con = null;
				}
			}catch(Exception e){
				e.printStackTrace();
			}				
		}
		return result;
	}
	public static void main(String[] args){
		int i = DBUtil.getMstxInfoCountForPhone("a", -1, "", "743");
		System.out.println(i);
	}
}
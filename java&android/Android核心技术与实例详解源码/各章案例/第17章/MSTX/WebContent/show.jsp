<%@page import="ytl.DBUtil"%><%@ page contentType="text/html; charset=gbk" %> 
<%@ page import="java.io.*"%> 
<%@ page import="java.sql.*, javax.sql.*" %> 
<%@ page import="java.util.*"%> 
<%@ page import="java.math.*"%> 
<%@ page import="ytl.*"%> 
<%
	String number = request.getParameter("number");
	String type = request.getParameter("type");
	if(type != null){
		if(type.equals("mstx_image")){
			OutputStream outs = null;
			try{
				ArrayList<MSTXImage> myMSTXImages = DBUtil.getMSTXImage(Integer.parseInt(number));
				if(!myMSTXImages.isEmpty()){
					MSTXImage mi = myMSTXImages.get(0);
					Blob b = mi.getB();
					long size = b.length();
					byte[] bs = b.getBytes(1, (int)size);
					/*
					System.out.println(bs.length+"=============");
					int c = 0;
					for(int i=0; i<bs.length; i++){
						System.out.print(bs[i]);
						if(c == 9){
							System.out.println();
						}
						c = (c+1)%10;
					}
					*/
					response.setContentType("image/jpeg");
					if(outs == null){
						outs = response.getOutputStream();//�õ���
					}
					outs.write(bs);//���
					outs.flush(); 	
					out.clear();//������ϣ�������쳣
					out = pageContext.pushBody(); 
				}
			} 
			catch(Exception e){//�����쳣
				e.printStackTrace();//��ӡ�쳣
			}
			finally{//�ر�
				try{
					if(outs != null){
						outs.close();//�ر���
						outs = null;
					}
				}
				catch(Exception e){//�����쳣
					e.printStackTrace();//��ӡ�쳣 
				}
			}		
		}		
		else if(type.equals("mstx_ads_image")){//��ʾ���������
			OutputStream outs = null;
			try{
				Blob myMSTXImages = DBUtil.getMstxAdsImageByGpid(Integer.parseInt(number));
				long size = myMSTXImages.length();
				byte[] bs = myMSTXImages.getBytes(1, (int)size);
				response.setContentType("image/jpeg");
				if(outs == null){
					outs = response.getOutputStream();//�õ���
				}
				outs.write(bs);//���
				outs.flush(); 	
				out.clear();//������ϣ�������쳣
				out = pageContext.pushBody();
			} 
			catch(Exception e){//�����쳣
				e.printStackTrace();//��ӡ�쳣
			}
			finally{//�ر�
				try{
					if(outs != null){
						outs.close();//�ر���
						outs = null;
					}
				}
				catch(Exception e){//�����쳣
					e.printStackTrace();//��ӡ�쳣 
				}
			}				
		}
		else if(type.equals("mstx_head")){//��ʾͷ��
			OutputStream outs = null;
			try{
				ArrayList<MSTXHeadImage> myMSTXImages = DBUtil.getHeadImage(Integer.parseInt(number));
				if(!myMSTXImages.isEmpty()){
					MSTXHeadImage mi = myMSTXImages.get(0);
					Blob b = mi.getB();
					long size = b.length();
					byte[] bs = b.getBytes(1, (int)size);
					response.setContentType("image/jpeg");
					if(outs == null){
						outs = response.getOutputStream();//�õ���
					}
					outs.write(bs);//���
					outs.flush(); 	
					out.clear();//������ϣ�������쳣
					out = pageContext.pushBody(); 
				}
			} 
			catch(Exception e){//�����쳣
				e.printStackTrace();//��ӡ�쳣
			}
			finally{//�ر�
				try{
					if(outs != null){
						outs.close();//�ر���
						outs = null;
					}
				}
				catch(Exception e){//�����쳣
					e.printStackTrace();//��ӡ�쳣 
				}
			}			
		}
	}
%>

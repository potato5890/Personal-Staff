<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<%@ page import="java.util.*,
	ytl.DBUtil,
	java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<LINK href="mstx.css" type=text/css rel=stylesheet>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��ʳ��--����ͷ��</title>
<script type="text/javascript">
	function changeAction(){
		myform1.action="FileUploadServlet?action=insertHeadImage";
		return true;		
	}
</script>
<%
	ArrayList<Integer> headIDs = DBUtil.getHeadNumber();
%>
</head>
<body bgcolor="#ff7d00">
<jsp:include page="head.jsp"></jsp:include>
	<table  align="center"  width="900px" height="50px" bgcolor="#ffffef">
		<tr>
			<td width="70%">
				<table>
					<tr>
						<td>
							<img src="image/profile.gif"></img>
							<font size="3">����ͷ��</font><br/><br/>
							<div class=arinfo>�ϴ����Ե�ͷ�����ߺ��Ѷ���������ӡ��</div>
						</td>				
					</tr>
					<tr><td><hr color="ffde9e"/></td></tr>
					<tr>
						<td>ѡ��ͷ��:</td>
					</tr>
					<tr>
						<td>
							<table width="400" border="0">
	
	<%				
				int tempRow = ((headIDs.size()%5)==0)?(headIDs.size()/5):((headIDs.size()/5)+1);
				for(int i=0; i<tempRow; i++){
	%>
					<tr>
	<%
						for(int j=0; j<5; j++){
							int temp = (i*5)+j;
							if(temp<headIDs.size()){
								int id = headIDs.get(temp);
	%>
								<td><a href="ControlServlet?action=updateUserHead&id=<%=id%>"><img alt="ͷ��" src="show.jsp?number=<%=id%>&type=mstx_head" width="50" height="50"></a></td>
	<%									
							}
							
							
						}
	%>						
					</tr>
	<%
				}
	%>				
							</table>
						</td>
					</tr>
					<tr><td><hr color="ffde9e"/></td></tr>
					<tr>
						<td>
							<form action="FileUploadServlet" name="myform1" method="post"  enctype="multipart/form-data" onsubmit="return changeAction();">
								<table>
									<tr>
										<td>�ϴ�ͷ��</td>
									</tr>
									<tr>
										<td><input type="file" name="myfile" width="400"/></td>
									</tr>															
									<tr>
										<td><input type="submit" value="ȷ��" class="btn"></td>
									</tr>	
								</table>
							</form>
						</td>
					</tr>									
				</table>
			</td>
			<td width="30%">
				<!-- �Ҳ�հ� -->
			</td>			
		</tr>
	</table>	
<jsp:include page="footer.jsp"></jsp:include>
</body>	
</html>
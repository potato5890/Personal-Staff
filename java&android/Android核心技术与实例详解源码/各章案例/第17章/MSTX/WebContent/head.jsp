<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<%@ page import="java.util.*,ytl.DBUtil,java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 
		Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<LINK href="mstx.css" type=text/css rel=stylesheet>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
	<table  align="center" width="900px" height="50px"  bgcolor="#ffffef">
		<tr>
			<td colspan="6" class="clear">
				<DIV id="logo">
					<H1>
						<A title=��ʳ���� 
							href="index.jsp">
							<img src="image/logo.gif" border="0">
							<STRONG>��ʳ����</STRONG>
						</A>
					</H1>
				</DIV>
			</td>
			<td valign="bottom" align="right">
			<font size="2">
	<%
				String u_name = (String)session.getAttribute("u_name");
				if(u_name == null){
	%>
					<a title="ע��" href="ControlServlet?action=toregister">ע��</a>
					<a title="��¼" href="/MSTX/login.jsp">��¼</a>
	<%
				}else{
					String uid = (String)session.getAttribute("uid");
					if(DBUtil.isAdmin(uid)){//�ǹ���Աʱ
	%>
						<a  href="ControlServlet?action=home"><%=u_name%>,��ʳ����ӭ��</a>
						<a  href="ControlServlet?action=admin">������վ</a>
						<a  href="ControlServlet?action=exit">ע��</a>
	<%
					}
					else{
	%>
						<a  href="ControlServlet?action=home"><%=u_name%>,��ʳ����ӭ��</a>
						<a  href="ControlServlet?action=exit">ע��</a>  
	<%
					}
				}
	%>
			</font>
			</td>		
		</tr>
	</table>
	<DIV id=nav>
	<table  align="center" bgcolor="#f7e6ac" width="900px" height="30px" cellspacing="0">	
		<tr align="center" >
			<td><A title="��ҳ" href="ControlServlet?action=index">��ҳ</A></td>
			<td><A title="�ҵ��ղ�" href="ControlServlet?action=favourite">�ҵ��ղ�</A></td>
			<td><A title="��ʳ����" href="ControlServlet?action=search">��ʳ����</A></td>
			<td><A title="ÿ���Ƽ�" href="ControlServlet?action=everydayRecommend">ÿ���Ƽ�</A></td>
			<td><A title="�ϴ��Ƽ�" href="ControlServlet?action=updateRecommend">�ϴ���ʳ</A></td>
			<!--  <td><A title="�ϴ��Ƽ�" href="ControlServlet?action=mstx_photo">��ʳ���</A></td> -->
		</tr>
	</table>
	</DIV>
</html>
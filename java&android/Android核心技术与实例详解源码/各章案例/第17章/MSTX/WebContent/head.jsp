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
						<A title=美食天下 
							href="index.jsp">
							<img src="image/logo.gif" border="0">
							<STRONG>美食天下</STRONG>
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
					<a title="注册" href="ControlServlet?action=toregister">注册</a>
					<a title="登录" href="/MSTX/login.jsp">登录</a>
	<%
				}else{
					String uid = (String)session.getAttribute("uid");
					if(DBUtil.isAdmin(uid)){//是管理员时
	%>
						<a  href="ControlServlet?action=home"><%=u_name%>,美食网欢迎您</a>
						<a  href="ControlServlet?action=admin">管理网站</a>
						<a  href="ControlServlet?action=exit">注销</a>
	<%
					}
					else{
	%>
						<a  href="ControlServlet?action=home"><%=u_name%>,美食网欢迎您</a>
						<a  href="ControlServlet?action=exit">注销</a>  
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
			<td><A title="首页" href="ControlServlet?action=index">首页</A></td>
			<td><A title="我的收藏" href="ControlServlet?action=favourite">我的收藏</A></td>
			<td><A title="美食搜索" href="ControlServlet?action=search">美食搜索</A></td>
			<td><A title="每日推荐" href="ControlServlet?action=everydayRecommend">每日推荐</A></td>
			<td><A title="上传推荐" href="ControlServlet?action=updateRecommend">上传美食</A></td>
			<!--  <td><A title="上传推荐" href="ControlServlet?action=mstx_photo">美食相册</A></td> -->
		</tr>
	</table>
	</DIV>
</html>
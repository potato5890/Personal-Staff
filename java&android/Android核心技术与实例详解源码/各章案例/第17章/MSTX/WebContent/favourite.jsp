<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<%@ page import="java.util.*,
	ytl.DBUtil,
	ytl.MstxInfo,
	java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<LINK href="mstx.css" type=text/css rel=stylesheet>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>美食天下网--我的收藏</title>
</head>
<body bgcolor="#ff7d00">
	<jsp:include page="head.jsp"></jsp:include>
	<table bgcolor="#ffffef" align="center" width="900px">
		<tr><td>
		<table width="100%"><tr>
			<td width="70%">
			<table>
	<%
			String uid = (String)session.getAttribute("uid");
			ArrayList<MstxInfo> info = DBUtil.getFavourite(uid);
			if(info.size() != 0){
				for(MstxInfo mi : info){
					String mid = mi.getMid();
	%>
				<tr>
				<td><table>
					<tr>
						<td><img src="show.jsp?number=<%=mid%>&type=mstx_image" height="70px" width="110px"></img></td>
						<td>
							<table height="80px">
								<tr valign="top">
									<td><font size="3" color="#ff0000"><%= mi.getInfo_title()%></font><br/>
										<font size="2"  class=arinfo><%= mi.getInfo_time()%></font>
									</td>
								</tr>
								<tr valign="top">
									<td><font size="2"><%= mi.getInfo_dis()%></font></td>
								</tr>
								<tr valign="bottom">
									<td><font size="2">
										<a href="ControlServlet?action=deleteMstxCol&mid=<%=mid%>">删除</a>
										&nbsp&nbsp
										地图
									</font></td>
								</tr>																							
							</table>
						</td>
					</tr>
				</table></td>
				</tr>
				<tr><td><hr color="ffde9e"/></td></tr>
	<%
				}
			}
			else {
	%>
				<tr><td><font size="5">您还没有收藏任何食物！</font></td></tr>
	<%
			}
	%>
			</table>
			</td>
			<td></td>
		</tr></table></td></tr>
	</table>
<jsp:include page="footer.jsp"></jsp:include>	
</body>
</html>
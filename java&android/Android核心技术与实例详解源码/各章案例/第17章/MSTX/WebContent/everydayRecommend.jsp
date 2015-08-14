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
<title>美食天下网--每日推荐</title>
<%
	ArrayList<MstxInfo> mstxInfos = DBUtil.getMstx_recommend();
%>
</head> 
<body bgcolor="#ff7d00">
<jsp:include page="head.jsp"></jsp:include>
	<table bgcolor="#ffffef" align="center" width="900px">
		<tr>
			<td>
				<table>
	<%
					if(mstxInfos.size() != 0){
						for(MstxInfo mi : mstxInfos){
							String mid = mi.getMid();
	%>
							<tr>
							<td><table>
								<tr>
									<td><img src="show.jsp?number=<%=mid%>&type=mstx_image" height="80px" width="120px"></img></td>
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
													<a href="ControlServlet?action=insertMstxCol&mid=<%=mid%>">收藏</a>
													&nbsp&nbsp
													地图&nbsp&nbsp&nbsp
	<%
													String uid = (String)session.getAttribute("uid");
													if(DBUtil.isAdmin(uid)){//是管理员时
	%>
														<a  href="ControlServlet?action=deleteMstx_recommend&mid=<%=mid%>">删除</a>
	<%
													}
	%>
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
	%>
				</table>
			</td> 			
		</tr>			
	</table>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
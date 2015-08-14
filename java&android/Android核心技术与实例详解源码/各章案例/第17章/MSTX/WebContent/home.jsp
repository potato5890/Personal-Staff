<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<%@ page import="java.util.*,
	ytl.*,
	java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<LINK href="mstx.css" type=text/css rel=stylesheet>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>美食网--个人主页</title>
<%
	String uid = (String)session.getAttribute("uid");
	UserInfo user = null; 
	if(uid != null){
		user = DBUtil.getUserByUid(uid);
		if(user == null){
			request.getRequestDispatcher("error.jsp").forward(request,response);
		}
	}
%>
</head> 
<body bgcolor="#ff7d00">
<jsp:include page="head.jsp"></jsp:include>
	<table bgcolor="#ffffef" align="center" width="900px">
	<tr><td width="70%">
		<table>
			<tr>
				<td>
					<img src="image/profile.gif"></img>
					<font size="3">个人资料</font><br/><br/>
					<div class=arinfo>填写完整准确的个人资料，可以让更多的朋友找到您。</div>
				</td>				
			</tr>
			<tr><td><hr color="ffde9e"/></td></tr>	
			<tr>
				<td>
					<form action="ControlServlet" method="post"><div class="mform">
					<input type="hidden" value="updateUserInfo" name="action">
					<table>
						<tr>
							<td align="center">
								<img alt="头像" src="show.jsp?number=<%=user.getU_head()%>&type=mstx_head" width="140" height="140">
							</td>
							<td valign="top">
								<div class="myHead">
								<table>
									<tr>
										<td><font size="3">状态：&nbsp在线</font></td>
									</tr>
									<tr>
										<td  valign="top"><div class=arinfo>
											已有<%=user.getU_number()%>人次访问, <%=user.getU_integral()%>个积分
										</div></td>
									</tr>	
									<tr>
										<td valign="top" class=arinfo>
										心情：<%=user.getU_mood()%> (<a href="home.jsp">更新心情</a>)
										</td>
									</tr>
									<tr>
										<td valign="bottom" class=arinfo>
										美食等级：<img src="image/star_level1.gif"/><img src="image/star_level1.gif"/>
										</td>
									</tr>																		
								</table>
								</div>
							</td>
						</tr>		
						<tr>
							<td align="center">
								<img src="image/profile.gif" height="15px"/>&nbsp<a href="ControlServlet?action=changeHead" class=headText>更改头像</a>
							</td>
							<td></td>
						</tr>			
						<tr>
							<td align="center">
								<em>美食证号：</em>
							</td>
							<td>
								<%=user.getUid()%>
							</td>
						</tr>
						<tr>
							<td >
								<em>昵称：</em>
							</td>
							<td>
								<input type="text" name="u_name" class="f2_input" value="<%=user.getU_name()%>"/>
							</td>
						</tr>						
						<tr>
							<td>
								<em>QQ号：</em>
							</td>
							<td>
								<input type="text" name="u_qq" class="f2_input" value="<%=user.getU_qq()%>"/>
							</td>
						</tr>	
						<tr>
							<td>
								<em>电子邮件：</em>
							</td>
							<td>
								<input type="text" name="u_Email" class="f2_input" value="<%=user.getU_Email()%>"/>
							</td>
						</tr>
						<tr>
							<td>
								<em>爱好：</em>
							</td>
							<td>
								<input type="text" name="u_favourite" class="f2_input" value="<%=user.getU_hobby()%>"/>
							</td>
						</tr>							
						<tr>
							<td>
								<em>个人描述：</em>
							</td>
							<td>
								<textarea rows="5" cols="34" name="u_dis" class=f2_input><%=user.getU_dis()%></textarea>
							</td>
						</tr>
						<tr>
							<td>
							</td>						
							<td align="left"  height="50px">
								<input type="submit" value="确认修改" class="btn">	
							</td>
						</tr>									
					</table>
					</div></form>
				</td>
			</tr>
		</table>
	</td>
	<td width="30%"></td>
	</tr>		
	</table>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
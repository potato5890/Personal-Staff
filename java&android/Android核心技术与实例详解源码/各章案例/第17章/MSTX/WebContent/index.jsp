<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<%@ page import="java.util.*,ytl.DBUtil,ytl.MstxInfo,java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<LINK href="mstx.css" type=text/css rel=stylesheet>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<title>美食网</title>
<script language="JavaScript" src="ads.js"></script>
</head>
<body bgcolor="#ff7d00">
<jsp:include page="head.jsp"></jsp:include>
	<table bgcolor="#ffffef" align="center" width="890px">
		<tr>
			<td>
			<table border="0">
				<tr>
				<td>
					<div class="container" id="idTransformView">
					  	<ul class="slider slider" id="idSlider">
					    	<li><img src="show.jsp?number=<%=0%>&type=mstx_ads_image" border="0"/></li>
					    	<li><img src="show.jsp?number=<%=1%>&type=mstx_ads_image" border="0"/></li>
					    	<li><img src="show.jsp?number=<%=2%>&type=mstx_ads_image" border="0"/></li>
					  	</ul>
					  	<ul class="num" id="idNum">
					    	<li>1</li>
					    	<li>2</li>
					    	<li>3</li>
						</ul>
					</div>
				</td>
				<td>
				<table>
	<%
					ArrayList<MstxInfo> info = DBUtil.getMstxInfoByName("", 2,2,1,1);
						if(info.size() != 0){
						for(MstxInfo mi : info){
							String mid = mi.getMid();
	%>
							<tr>
							<td><table  border="0" width="252px">
								<tr>
									<td width="130"><img src="show.jsp?number=<%=mid%>&type=mstx_image" height="80px" width="120px"></img></td>
									<td>
										<table height="80px" width="162px" border="0">
											<tr valign="top">
												<td>
													<font size="3" color="#ff0000"><%= mi.getInfo_title()%></font>
													<font size="2"  class=arinfo><%= mi.getInfo_time()%></font>
												</td>
											</tr>
											<tr valign="bottom">
												<td><font size="2">
													<a href="ControlServlet?action=insertMstxCol&mid=<%=mid%>">收藏</a>
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
	%>
				</table>
				</td>
				<td>
				<table>
	<%
					ArrayList<MstxInfo> info2 = DBUtil.getMstxInfoByName("", 2,2,2,1);
						if(info2.size() != 0){
						for(MstxInfo mi : info2){
							String mid = mi.getMid();
	%>
							<tr>
							<td>
							<table  border="0" width="245px">
								<tr>
									<td width="130"><img src="show.jsp?number=<%=mid%>&type=mstx_image" height="80px" width="120px"></img></td>
									<td>
										<table height="80px" width="146px" border="0">
											<tr valign="top">
												<td>
													<font size="3" color="#ff0000"><%= mi.getInfo_title()%></font>
													<font size="2"  class=arinfo><%= mi.getInfo_time()%></font>
												</td>
											</tr>
											<tr valign="bottom">
												<td><font size="2">
													<a href="ControlServlet?action=insertMstxCol&mid=<%=mid%>">收藏</a>
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
	%>
				</table>				
				</td>
				</tr>
				<tr>
					<td colspan="3">
						<img src="image/showblog1.jpg" width="885px" id="showAds"/>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
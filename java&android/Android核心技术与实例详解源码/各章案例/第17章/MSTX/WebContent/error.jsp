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
<title>美食天下网--错误</title>
</head>
<body bgcolor="#ff7d00">
<jsp:include page="head.jsp"></jsp:include>
	<table bgcolor="#ffffef" align="center" width="900px">	
		<tr>
			<td align="center"><font size="8">网站出错啦！！！<%= request.getParameter("msg") %></font></td>
		</tr>				
	</table>
<jsp:include page="footer.jsp"></jsp:include>	
</body>
</html>
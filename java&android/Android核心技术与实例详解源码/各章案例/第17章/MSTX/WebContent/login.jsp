<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<%@ page import="java.util.*,ytl.DBUtil,java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 
Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<LINK href="mstx.css" type=text/css rel=stylesheet>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<title>美食天下网--用户登录</title>
<script type=text/javascript>
	String.prototype.Trim = function(){
	   return this.replace(/(^\s*)|(\s*$)/g, "");
	};
	function checkUser(){
		if(document.myform.uid.value.Trim()== ""){//美食证号为空
		 	alert("请输入美食证号！");
		 	return false;
		}
		else if(document.myform.u_pwd.value.Trim()== ""){//设置密码为空
		 	alert("请输入密码！");
		 	return false;
		}		
		return true;
	} 
</script>
</head>
<body bgcolor="#ff7d00">
<jsp:include page="head.jsp"></jsp:include>
	<table bgcolor="#ffffef" align="center" width="900px"><tr><td>
			<form action="ControlServlet" name="myform" onsubmit="return checkUser();" method="post">
			<input type="hidden" name="action" value="login"></input>
			<table width="30%" align="center">
				<tr align="center"><td>登录美食证</td></tr>
				<tr align="center"><td><hr color="ffdeee"/></td></tr>
				<tr align="left"><td><font size="3">美食证号:</font></td></tr>
				<tr align="center"><td><input type="text" class=f2_input name="uid"></td></tr>
				<tr align="left"><td><font size="3">密码:</font></td></tr>
				<tr align="center"><td><input type="password" class=f2_input name="u_pwd"></td></tr>	
				<tr align="left"><td><input type="submit" value="登录" class="btn"></input></tr>							
			</table>
			</form>
	</td></tr></table>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
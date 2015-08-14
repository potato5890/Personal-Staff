<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%><!-- 编码格式等参数的声明-->
<%@ page import="java.util.*,ytl.DBUtil,java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 
	Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><LINK href="mstx.css" type=text/css rel=stylesheet>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<title>美食天下网--用户注册</title><!-- 标题 -->
	<script type="text/javascript">/*javascript代码用于本地验证*/
		String.prototype.Trim = function(){/*通过原型向String类添加trim方法*/
		   return this.replace(/(^\s*)|(\s*$)/g,"");/*通过正则式处理字符串*/
		};
		function checkUserName(){//监测输入的数据是否合法
			if(document.myform.u_name.value.Trim()== ""){//用户名为空
			 	alert("请输入昵称！");/*弹出提示框*/
			 	return false;
			}
			else if(document.myform.u_pwd1.value.Trim()== ""){//设置密码为空
			 	alert("请输入密码！");/*弹出提示框*/
			 	return false;
			}
			else if(document.myform.u_pwd2.value.Trim()== ""){//设置密码为空
			 	alert("请输入确认密码！");/*弹出提示框*/
			 	return false;
			}	
			else if(document.myform.u_pwd2.value.Trim()!=document.myform.u_pwd1.value.Trim()){//两次密码不一致
			 	alert("两次密码不一致！");/*弹出提示框*/
			 	return false;
			}			
			return true;//监测成功
		} 
	</script>
</head>
<body bgcolor="#ff7d00"><!-- 设置body的背景颜色 -->
	<jsp:include page="head.jsp"></jsp:include><!-- 引入导航头文件 -->
	<table bgcolor="#fffff" align="center" width="900px"><tr><td>
		<form action="ControlServlet" name="myform" onsubmit="return checkUserName();" method="post">
			<input type="hidden" name="action" value="register"></input>
			<table width="30%" align="center">
				<tr align="center"><td><font size="6">注册美食证</font></td></tr>
				<tr align="center"><td><hr color="ffdeee"/></td></tr><!-- 画一条横线 -->
				<tr align="left"><td><font size="3">昵称(用户名):</font></td></tr>
				<tr align="center"><td><input type="text" class="f2_input" name="u_name"></td></tr>
				<tr align="left"><td><font size="3">设置密码:</font></td></tr>
				<tr align="center"><td><input type="password" class="f2_input" name="u_pwd1"></td></tr>
				<tr align="left"><td><font size="3">确认密码:</font></td></tr>
				<tr align="center"><td><input type="password" class="f2_input" name="u_pwd2"></td></tr>						
				<tr align="left"><td><font size="3">QQ号:</font></td></tr>
				<tr align="center"><td><input type="text" class="f2_input" name="u_qq"></td></tr>		
				<tr align="left"><td><font size="3">电子邮箱:</font></td></tr>
				<tr align="center"><td><input type="text" class="f2_input" name="u_Email"></td></tr>				
				<tr align="left"><td><font size="3">自我描述:</font></td></tr>
				<tr align="center"><td><!-- 文本区，用于接收用户输入的自我描述 -->
					<textarea rows="7" class="f2_input" name="u_dis" cols="29"></textarea>
				</td></tr>	
				<tr align="left"><td><input type="submit" value="立即注册" class="btn"></input></tr>							
			</table><!-- 表格结束 -->
		</form></td></tr>
	</table><jsp:include page="footer.jsp"></jsp:include><!-- 引入版权声明界面 -->
</body></html>
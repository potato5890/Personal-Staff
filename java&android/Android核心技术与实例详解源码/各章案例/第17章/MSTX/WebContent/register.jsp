<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%><!-- �����ʽ�Ȳ���������-->
<%@ page import="java.util.*,ytl.DBUtil,java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 
	Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><LINK href="mstx.css" type=text/css rel=stylesheet>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<title>��ʳ������--�û�ע��</title><!-- ���� -->
	<script type="text/javascript">/*javascript�������ڱ�����֤*/
		String.prototype.Trim = function(){/*ͨ��ԭ����String�����trim����*/
		   return this.replace(/(^\s*)|(\s*$)/g,"");/*ͨ������ʽ�����ַ���*/
		};
		function checkUserName(){//�������������Ƿ�Ϸ�
			if(document.myform.u_name.value.Trim()== ""){//�û���Ϊ��
			 	alert("�������ǳƣ�");/*������ʾ��*/
			 	return false;
			}
			else if(document.myform.u_pwd1.value.Trim()== ""){//��������Ϊ��
			 	alert("���������룡");/*������ʾ��*/
			 	return false;
			}
			else if(document.myform.u_pwd2.value.Trim()== ""){//��������Ϊ��
			 	alert("������ȷ�����룡");/*������ʾ��*/
			 	return false;
			}	
			else if(document.myform.u_pwd2.value.Trim()!=document.myform.u_pwd1.value.Trim()){//�������벻һ��
			 	alert("�������벻һ�£�");/*������ʾ��*/
			 	return false;
			}			
			return true;//���ɹ�
		} 
	</script>
</head>
<body bgcolor="#ff7d00"><!-- ����body�ı�����ɫ -->
	<jsp:include page="head.jsp"></jsp:include><!-- ���뵼��ͷ�ļ� -->
	<table bgcolor="#fffff" align="center" width="900px"><tr><td>
		<form action="ControlServlet" name="myform" onsubmit="return checkUserName();" method="post">
			<input type="hidden" name="action" value="register"></input>
			<table width="30%" align="center">
				<tr align="center"><td><font size="6">ע����ʳ֤</font></td></tr>
				<tr align="center"><td><hr color="ffdeee"/></td></tr><!-- ��һ������ -->
				<tr align="left"><td><font size="3">�ǳ�(�û���):</font></td></tr>
				<tr align="center"><td><input type="text" class="f2_input" name="u_name"></td></tr>
				<tr align="left"><td><font size="3">��������:</font></td></tr>
				<tr align="center"><td><input type="password" class="f2_input" name="u_pwd1"></td></tr>
				<tr align="left"><td><font size="3">ȷ������:</font></td></tr>
				<tr align="center"><td><input type="password" class="f2_input" name="u_pwd2"></td></tr>						
				<tr align="left"><td><font size="3">QQ��:</font></td></tr>
				<tr align="center"><td><input type="text" class="f2_input" name="u_qq"></td></tr>		
				<tr align="left"><td><font size="3">��������:</font></td></tr>
				<tr align="center"><td><input type="text" class="f2_input" name="u_Email"></td></tr>				
				<tr align="left"><td><font size="3">��������:</font></td></tr>
				<tr align="center"><td><!-- �ı��������ڽ����û�������������� -->
					<textarea rows="7" class="f2_input" name="u_dis" cols="29"></textarea>
				</td></tr>	
				<tr align="left"><td><input type="submit" value="����ע��" class="btn"></input></tr>							
			</table><!-- ������ -->
		</form></td></tr>
	</table><jsp:include page="footer.jsp"></jsp:include><!-- �����Ȩ�������� -->
</body></html>
<%@ page contentType="text/html;charset=GBK" %>
<% 
	request.setCharacterEncoding("GBK");
	String name = (String)request.getParameter("name");
	if(name != null){
		out.print("您好，"+name+"。欢迎使用HTTP连接。");
	}
 %>
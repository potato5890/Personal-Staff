<%@ page contentType="text/html;charset=GBK" %>
<% 
	request.setCharacterEncoding("GBK");
	String name = (String)request.getParameter("name");
	if(name != null){
		out.print("���ã�"+name+"����ӭʹ��HTTP���ӡ�");
	}
 %>
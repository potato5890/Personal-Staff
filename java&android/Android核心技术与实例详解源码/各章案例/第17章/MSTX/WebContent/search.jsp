<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<%@ page import="java.util.*,
	ytl.DBUtil,
	ytl.MstxInfo,
	java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<LINK href="mstx.css" type=text/css rel=stylesheet>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>��ʳ������--��ʳ����</title>
<script type=text/javascript>
	String.prototype.Trim = function(){
	   return this.replace(/(^\s*)|(\s*$)/g, "");
	};
	
	function submitMethod(type){
		var sort = document.myform.info_sort.value.Trim();
		var startprice = document.myform.startprice.value.Trim();
		var endprice = document.myform.endprice.value.Trim();
		var searchValue = document.myform.searchValue.value.Trim();

		if(searchValue == ""){//������Ϊ��ʱ
			alert("�����������ؼ���");
			return;
		}

		if(startprice == "" && endprice == ""){
			document.myform.action = "search.jsp?&searchValue="+searchValue
			+"&sort="+sort;
		}

		else if(startprice == ""){
			document.myform.action = "search.jsp?&searchValue="+searchValue
					+"&sort="+sort+"&endprice="+endprice;
		}
		else if(endprice == ""){
			document.myform.action = "search.jsp?&searchValue="+searchValue
				+"&sort="+sort+"&startprice="+startprice;
		}
		else {
			document.myform.action = "search.jsp?&searchValue="+searchValue
				+"&sort="+sort+"&startprice="+startprice+"&endprice="+endprice;		
		}

		document.myform.submit();
	}
	
</script>

<%
	List<String[]> sorts = DBUtil.getMstx_sort();
%>
</head> 
<body bgcolor="#ff7d00">
<jsp:include page="head.jsp"></jsp:include>

	<table width="900px" align="center" bgcolor="#ffffef" border="0">
	<tr>
		<td width="70%">
			<form action="search.jsp" name="myform" method="post">
			<table border="0" width="100%">
		<tr align="center">
			<td colspan="2"><font size="5">��ʳ����</font></td>
		</tr>
		<tr align="center" height="30px">
			<td align="right">�ؼ��֣�</td>
			<td>
				<input type="text" class=f2_input name="searchValue">
			</td>			
		</tr>
		<tr align="center" height="30px">
			<td align="right">���ࣺ</td>
			<td>
					<select size="1" class="f2_input" name="info_sort">
							<option value="<%= -1 %>">�������</option>
		<%
						
							for(String[] str : sorts){
		%>
								<option value="<%= str[0]%>"><%= str[1] %></option>
		<%
							}
		%>
					</select>
			</td>			
		</tr>	
		<tr align="center" height="30px">
			<td align="right">�۸�</td>
			<td>
				<input type="text" class="f2_input2" name="startprice"> --
				<input type="text" class="f2_input2" name="endprice">
			</td>			
		</tr>	
		<tr align="center" height="30px">
			<td colspan="2">
				<input type="button" value="����" class="btn" onclick="submitMethod('byName');">
			</td>			
		</tr>				
		
		<tr>
			<td colspan="2">
			<table width="100%">
	<%
				request.setCharacterEncoding("gbk");
				String searchValue = request.getParameter("searchValue");
				String sort = request.getParameter("sort");
				String startprice = request.getParameter("startprice");
				String endprice = request.getParameter("endprice");
				
				if(startprice == null){
					startprice = "";
				}
				if(endprice  == null){
					endprice = "";
				}
				
				String currentPageNoType = request.getParameter("currentPageNoType");//��ҳ
				Integer currentPageNo = (Integer)session.getAttribute("currentPageNo");
				if(sort != null){					
					int infoCount = DBUtil.getMstxInfoCountForPhone(searchValue,Integer.parseInt(sort),startprice,endprice);;
					if(currentPageNo == null){
						currentPageNo = 1;
					}
					if(currentPageNoType != null){
						if(currentPageNoType.equals("up")){//���Ϸ�ҳ
							if(currentPageNo > 1){
								currentPageNo = currentPageNo-1;
							}
						}
						else{//���·�ҳ
							
							if(currentPageNo < ((infoCount%2==0)?(infoCount/2):(infoCount/2)+1)){
								currentPageNo = currentPageNo+1;
							}
						}
					}
					session.setAttribute("currentPageNo",currentPageNo);
				
					System.out.println(searchValue+":"+sort+":"+startprice+":"+endprice+":"+currentPageNo);
					
					List<String[]> info = DBUtil.getMstxInfoForPhone(searchValue, Integer.parseInt(sort),startprice,endprice,4,currentPageNo);
	%>
					<tr>
						<td>
							����"<%= new String(searchValue.getBytes("ISO-8859-1"),"GBK") %>"�����ʳ��<%=infoCount %>����
						</td>
					</tr>
					<tr><td colspan="2"><hr color="ffde9e"/></td></tr>	
	<%

					if(info.size() != 0){
						for(int i=0; i<info.size(); i++){
							String[] str = info.get(i);
	%>						
							<tr>
							<td><table>
								<tr>
									<td><img src="show.jsp?number=<%=str[7]%>&type=mstx_image" height="80px" width="120px"></img></td>
									<td>
										<table height="80px">
											<tr valign="top">
												<td><font size="3" color="#ff0000"><%= str[1]%></font><br/>
													<font size="2"  class=arinfo><%= str[5]%></font>
												</td>
											</tr>
											<tr valign="top">
												<td><font size="2"><%=str[2]%></font></td>
											</tr>	
											<tr valign="bottom">
												<td><font size="2">
													<a href="ControlServlet?action=insertMstxCol&mid=<%=str[7]%>">�ղ�</a>
													&nbsp&nbsp
													��ͼ
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
	%>
							<tr><td align="center">
								<div class="meneame" align="center">
									<a href="search.jsp?&searchValue=<%= (new String(searchValue.getBytes("ISO-8859-1"),"GBK")) %>&currentPageNoType=up&sort=<%=sort%>&startprice=<%=startprice%>&endprice=<%=endprice%>" >&nbsp��һҳ </a>
									<a href="search.jsp?&searchValue=<%= (new String(searchValue.getBytes("ISO-8859-1"),"GBK")) %>&currentPageNoType=down&sort=<%=sort%>&startprice=<%=startprice%>&endprice=<%=endprice%>">&nbsp��һҳ </a>
								</div>
							</td></tr>
	<%
						}
						else {
	%>
							<tr><td><font size="5">û���������κη���������ʳ�</font></td></tr>
	<%
						}					
				}
	%>

			</table>
			</td>
			</tr>			
			</table>
			</form>	
			</td>
	<td width="30%"><!-- �Ҳ�Ŀհף�����������ʾ������Ϣ�� --></td></tr>
	</table> 
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
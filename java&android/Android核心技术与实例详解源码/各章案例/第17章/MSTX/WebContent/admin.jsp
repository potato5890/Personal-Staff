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
<title>��ʳ��--�������</title>
<script type="text/javascript">
	String.prototype.Trim = function(){
	   return this.replace(/(^\s*)|(\s*$)/g, "");
	};

	function check(){
		if(document.myform2.gid.value.Trim()== ""){//����Ϊ��ʱ
		 	alert("������λ�ã� ");
		 	document.myform2.gid.focus();
		 	return false;
		}	
		var gid = myform2.gid.value;
		myform1.action="FileUploadServlet?action=insertMstxAds&gid="+gid;
		return true;
	}
</script>

<%
	List<String[]> sorts = DBUtil.getMstx_sort();
%>
</head>
<body bgcolor="#ff7d00">
<jsp:include page="head.jsp"></jsp:include>
	<table bgcolor="#ffffef" align="center" width="900px">
		<tr>
			<td>
	<%
			String uid = (String)session.getAttribute("uid");
			if(uid == null){
	%>
				<font size="6">�����ȵ�¼</font>

	<%
			}
			else{
	%>
				<table width="890px">
				<tr align="center"><td><font size="5">���ÿ���Ƽ�</font></td></tr>
	<%
				String currentPageNoType = request.getParameter("currentPageNoType");//��ҳ
				Integer currentPageNo1 = (Integer)session.getAttribute("currentPageNo1");
				if(currentPageNo1 == null){
					currentPageNo1 = 1;
				}
				
				if(currentPageNoType != null){
					if(currentPageNoType.equals("up")){//���Ϸ�ҳ
						if(currentPageNo1 > 1){
							currentPageNo1 = currentPageNo1-1;
						}
					}
					else{//���·�ҳ
						int infoCount = DBUtil.getMstxInfoCount();
						if(currentPageNo1 < ((infoCount%2==0)?(infoCount/2):(infoCount/2)+1)){
							currentPageNo1 = currentPageNo1+1;
						}
					}
				}
				session.setAttribute("currentPageNo1",currentPageNo1);

				ArrayList<MstxInfo> info = DBUtil.getMstxInfoByName("", 3,2,currentPageNo1,1);//�õ�������ʳ��Ϣ
				if(info.size() != 0){
					for(MstxInfo mi : info){
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
												<a href="ControlServlet?action=insertMstxCol&mid=<%=mid%>">�ղ�</a>&nbsp&nbsp
												<a href="ControlServlet?action=addToRecommend&mid=<%=mid%>">��ӵ�ÿ���Ƽ�</a>
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
							<a href="admin.jsp?currentPageNoType=up" >&nbsp��һҳ </a>
							<a href="admin.jsp?currentPageNoType=down">&nbsp��һҳ </a>
						</div>
					</td></tr>
	<%					
					}
			}
	%>
			</table>
			</td>
		</tr>
		<tr><td><hr color="efde9e"/></td></tr>
	</table>
	<table bgcolor="#ffffef" align="center" width="900px">
		<tr>
			<td align="center">
				<font size="5">������</font>
			</td>
		</tr>
		<tr>
			<td>
				<table width="890px" align="center">
							<tr align="center">
								<td align="left"  width="410px"></td>
								<td align="left"></td>
							</tr>
	<%
						List<Integer> mstxIDs = DBUtil.getMSTXAdsID();
						for(Integer i : mstxIDs){
	%>
							<tr align="center">
								<td align="left"  width="410px">λ�ã�<%=i%></td>
								<td align="left">ͼƬ��</td>
							</tr>
	<%
							List<Integer> mstxGPIDs = DBUtil.getMstxAdsGpid(i);
							for(Integer gpid : mstxGPIDs){
	%>
							<tr align="center">
								<td align="left"  width="410px"></td>
								<td align="left"><img src="show.jsp?number=<%=gpid%>&type=mstx_ads_image" height="80px" width="120px"></img></td>
							</tr>
	<%
							}
						}
	%>
					<tr align="center">
						<td align="left"></td>
						<td align="left">
						<form action="" name="myform2"  method="get">
							<table>
								<tr align="center">
									<td align="left">
										λ�ã�<input type="text" name="gid" class="f2_input"></input>
									</td>
								</tr>
							</table>
						</form>	
					</tr>
					<tr>
						<td></td>
						<td>
							<form name="myform1"  action="FileUploadServlet" onsubmit="return check();"  method="post"  enctype="multipart/form-data" >
								�ϴ���<input type="file" name="fileName"></input><br/>
								<input type="submit" value="ȷ��" class="btn"></input>
							</form>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td><hr color="efde9e"/></td></tr>
	</table>
	<table bgcolor="#ffffef" align="center" width="900px">
		<tr>
			<td align="center">
				<font size="5">��ʳ�������</font>
			</td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td width="410px"><font size="3">��ʳ����:</font></td>
						<td><font size="3">�������:</font></td>
					</tr>
					<tr>
						<td>
						<select size="5" class="f2_input" name="info_sort">
		<%
							for(String[] str : sorts){
		%>
								<option value="<%= str[0]%>"><%= str[1] %></option>
		<%
							}
		%>
						</select>
						</td>
						<td>
							<form action="ControlServlet?action=insertMstxSort" method="post" name="sortForm"><br/>
								<input type="text" name="sort" class="f2_input"><br/><br/>
								<input type="submit" value="���" class="btn"></input>
							</form>
						</td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page import="java.util.*,ytl.DBUtil,java.sql.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gbk"/>
	<link href="mstx.css" type="text/css" rel="stylesheet"/>
	<script src="http://maps.google.com/maps?file=api&amp;
			v=2&amp;key=ABQIAAAA0DBHEJH01_0VNMudvDaa6xTnXSjFu29eJd6cb
			MAcBwfCDrkn8RTOtwnL4wJ4A6GZWdE82cQ6geLv4Q" type="text/javascript" 
			charset="utf-8"></script>
	<script src="http://www.google.com/jsapi?key=ABQIAAAA0DBHEJH01_0VN
		MudvDaa6xTnXSjFu29eJd6cbMAcBwfCDrkn8RTOtwnL4wJ4A6GZWdE82cQ6geLv4Q" 
		type="text/javascript" charset="utf-8"></script>
	<script src="map.js" type="text/javascript" charset="utf-8"></script>
	<title>美食网--上传美食</title>
	<script type="text/javascript">
		String.prototype.Trim = function(){
		   return this.replace(/(^\s*)|(\s*$)/g, "");
		};
		function check(){
			if(document.myform2.info_title.value.Trim()== ""){//用户名为空
			 	alert("请输入美食名称！");
			 	document.myform2.info_title.focus();
			 	return false;
			}
			if(document.myform2.info_lon.value.Trim()== ""){//经度为空
			 	alert("请输入经度值！");
			 	document.myform2.info_lon.focus();
			 	return false;
			}
			if(document.myform2.info_lat.value.Trim()== ""){//纬度为空
			 	alert("请输入纬度值！");
			 	document.myform2.info_lat.focus(); 
			 	return false;
			}		
			var info_title = myform2.info_title.value;
			var info_lon= myform2.info_lon.value;
			var info_lat= myform2.info_lat.value;
			var info_dis= myform2.info_dis.value;
			var info_sort= myform2.info_sort.value;
			var info_price= myform2.info_price.value;
			var info_hotel= myform2.info_hotel.value;
			myform1.action="FileUploadServlet?action=insertMstxInfo&info_title="+info_title+
					"&info_lon="+info_lon+"&info_lat="+info_lat+"&info_dis="+info_dis+
					"&info_sort="+info_sort+"&info_price="+info_price+"&info_hotel="+info_hotel;
			return true;
		}
	</script>
	<%
		List<String[]> sorts = DBUtil.getMstx_sort();
	%>
</head> 
<body bgcolor="#ff7d00"  onload="load()">
	<jsp:include page="head.jsp"></jsp:include>
	<table bgcolor="#ffffef" align="center" width="900px" border="0">
		<tr>
			<td width="50%">
				<table border="0" width="100%" align="center">
					<tr><td>
						<table align="center" width="100%">
							<tr align="center"><td><font size="6">上传美食</font></td></tr>
							<tr align="center"><td><hr color="ffdeee"/></td></tr>						
						</table>
					</td></tr>
					<tr><td>
						<form action="" name="myform2"  method="get">
						<table align="center">
							<tr>
								<td>美食名称：</td><td><input type="text" class=f2_input name="info_title"></td>
							</tr>
							<tr>
								<td>饭店名称:</td><td><input type="text" class=f2_input name="info_hotel"></td>
							</tr>							
							<tr>
								<td>种类：</td>
								<td>
									<select size="1" class="f2_input" name="info_sort">
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
							<tr>
								<td>价格：</td><td><input type="text" class=f2_input name="info_price"></td>
							</tr>													
							<tr>
								<td><input type="hidden" class=f2_input name="info_lon" disabled="disabled" id="info_lon"></td>
							</tr>	
							<tr>
								<td><input type="hidden" class=f2_input name="info_lat" disabled="disabled" id="info_lat"></td>
							</tr>
							<tr>
								<td>美食描述：</td><td><textarea rows="7" class=f2_input name="info_dis" cols="29"></textarea></td>
							</tr>		
							<tr>
								<td></td>
								<td>
									<div class="arinfo">注：单击右侧的地图获取经纬度</div>
								</td>
							</tr>				
						</table>
						</form>
					</td></tr>				
					<tr><td>
						<form name="myform1"  action="FileUploadServlet" onsubmit="return check();"  method="post"  enctype="multipart/form-data" >
						<table align="center" >
							<tr>
								<td>美食图片：</td><td><input type="file" name="myfile"/></td>
							</tr>														
							<tr>
								<td><input type="submit" value="确定" class="btn"></td>
							</tr>
						</table>
						</form>
					</td></tr>
				</table>			
			</td>
			<td width="50%">
			<div id="map" style="height:350px;width:80%;border:solid 1px #000000;"></div>	
			</td>
		</tr>			
	</table>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
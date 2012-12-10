<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String contextPath=request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--STATUS OK-->
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/jquery-1.2.6.pack.js"></script>
<script type="text/javascript"  language="javascript">
function _submit(form1)
{
	
	var data=jQuery(form1).serialize();
	alert(data);
	$.post("<%=contextPath%>/rest/user-contact/${userId}.xml",data,function(resp){
		alert(resp);
	});
}
</script>
<body>
<p>RequestURI=<%=request.getRequestURI() %></p>
<form action="updateContact.action" method="POST" enctype="application/x-www-form-urlencoded">
<s:hidden name="_method" value="put" />
<p>
<s:textfield  name="stuEmail"/>
</p>
<p>
<s:textfield  name="personalEmail"/>
</p>
<p>
<s:textfield name="homeAddress"/>
</p>
<p>
<s:textfield name="dormBuildingAddress"/>
</p>
<p>
<s:textfield name="dormRoom"/>
</p>
<p>
<s:textfield name="mobile"/>
</p>
<p>
<s:textfield name="phoneShortnumber"/>
</p>
<p>
<s:textfield name="officePhone"/>
</p>
<p>
<s:textfield name="homePage"/>
</p>
<input type="button" value="submit" onclick="_submit(this.form)"/>
</form>
<%--
	private String stuEmail;
	private String personalEmail;
	private String homeAddress;
	private String dormBuildingAddress;
	private String dormRoom;
	private String mobile;
	private String phoneShortnumber;
	private String officePhone;
	private String homePage;
 --%>
</body>
</html>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--STATUS OK-->
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<body>
<p>RequestURI=<%=request.getRequestURI() %></p>
<form action="<%=request.getContextPath()%>/rest/user-cover/135809.json" method="POST" >
<input type="hidden" name="_method" value="put"/>
<input name="url" type="text"/>
<input type="submit" value="submit"/>
</form>
</body>
</html>
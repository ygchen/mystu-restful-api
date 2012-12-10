<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--STATUS OK-->
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<body>
<p>RequestURI=<%=request.getRequestURI() %></p>
<form action="uploadLogo.action" method="POST" enctype="multipart/form-data">
<input type="file" name="image"/>
<input type="hidden" name="callback" value="alert"/>
<input type="submit"/>
</form>
</body>
</html>
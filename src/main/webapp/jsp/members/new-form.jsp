<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--※ JSP 실행 시 url에 .jsp까지 작성해야 함--%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="/jsp/members/save.jsp" method="post">
    username: <input type="text" name="username"/>
    age: <input type="text" name="age"/>
    <button type="submit">전송</button>
</form>
</body>
</html>
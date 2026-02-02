<%--자바 페이지 임포트--%>
<%@ page import="com.ian.springmvc.servlet.domain.member.MemberRepository" %>
<%@ page import="com.ian.springmvc.servlet.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--자바 코드 삽입--%>
<%
    // request, response 그냥 사용 가능 -> JSP는 결국 서블릿으로 변환 및 컴파일됨
    MemberRepository memberRepository = MemberRepository.getInstance();

    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);
%>
<%--※ JSP 실행 시 url에 .jsp까지 작성해야 함--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id<%=member.getId()%>
    </li>
    <li>username<%=member.getUsername()%>
    </li>
    <li>age<%=member.getAge()%>
    </li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>

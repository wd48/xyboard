<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Board list</title>
</head>
<body>
	<span>${user.name}님 환영합니다.</span>
	<a href="<c:url value="/logout" />">로그아웃</a>
	<table border="1">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
	<c:forEach var="board" items="${boardList}" varStatus="loop">
		<tr>
			<td>${board.num}</td>
			<td><a href="<c:url value="/boardRead/${board.num}" />"> ${board.title}</a></td>
			<td>${board.name}</td>
			<td>${board.date}</td>
			<td>${board.count}</td>
		</tr>		
	</c:forEach>
<!-- 경로 뒤에 ${board.num}이라는 게시글의 번호를 주어 컨트롤러에서는 @PathVariable 어노테이션으로 받을 수 있습니다. -->
	</table>
	<a href="<c:url value="/boardWrite" />">글쓰기</a>
	<c:if test="${msg ne null}">
		<p style="color:#f00;">${msg}</p>
	</c:if>
</body>
</html>
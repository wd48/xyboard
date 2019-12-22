<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- form 태그라이브러리 추가 -->
<!-- BindingResult를 사용하기 위해 추가하는 것임  -->
<!-- form 태그 라이브러리를 사용하면 action 속성이 없는데, 브라우저 주소창을 참고해서 스프링이 자동으로 action 정보를 설정해준다. -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Board edit</title>
</head>
<body>
<!-- 변경 코드 -->
	<form:form commandName="boardDTO" mehtod="post">
		<table border="1">
			<tr>
				<th><form:label path="title">제목</form:label></th>
				<td>
					<form:input path="title" />
					<form:errors path="title" />
				</td>
			</tr>
			<tr>
				<th><form:label path="contents">내용</form:label></th>
				<td>
					<form:input path="contents" />
					<form:errors path="contents" />
				</td>
			</tr>
		</table>
		<div>
			<input type="submit" value="등록">
			<a href="<c:url value="/boardPageList" />"> 목록</a>
		</div>
	</form:form>
</body>
</html>
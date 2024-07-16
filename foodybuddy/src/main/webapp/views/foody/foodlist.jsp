<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="css/theme.css">
<title>맛집</title>
</head>
<body>
	<section>
	<h1>맛집</h1>
	<div style="flex-direction: row; display: flex;">
		<div>조회수 순</div>&nbsp;&nbsp;&nbsp;<div>최신 순</div>&nbsp;&nbsp;&nbsp;<div>좋아요 순</div>
	</div>
	<div>
	<select>
		<option value="">선택하세요</option>
		<option value="작성자">작성자</option>
		<option value="제목">제목</option>
		<option value="위치">위치</option>
	</select>
	<input type="text" name="searchbar"><button onclick="result();">검색</button>
	</div>
	<a href="/foody/create">작성</a>
	<br>
	<br>
	<br>
	<div class="book_list">
				<table>
					<colgroup>
						<col width="10%">
						<col width="50%">
						<col width="20%">
						<col width="20%">
					</colgroup>
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일자</th>
						</tr>
					</thead>
					<tbody>
						<%--  <%@page import="com.foodybuddy.foody.vo.Foody, java.util.*" %>
						<%
							List<Foody> list = (List<Foody>)request.getAttribute("resultList");
							for(int i = 0 ; i < list.size(); i++){ %>
								<tr>
									<td><%=list.get(i).getFoody_no()%></td>
									<td><%=list.get(i).getFoody_title()%><%=list.get(i).getFoody_title()%><%=list.get(i).getFoody_title()%></td>
									<td><%=list.get(i).getFoody_name()%></td>
									<td><%=list.get(i).getReg_date()%></td>
								</tr>
						<%}%> --%>
						<%@page import="com.foodybuddy.foody.vo.Foody, java.util.*" %>
						<% List<Foody> list = (List<Foody>) request.getAttribute("resultList");
               for (Foody foody : list) { %>
                <tr>
                    <td><%= foody.getFoody_no() %></td>
                    <td><a href="${pageContext.request.contextPath}/board/detail?foody_no=<%= foody.getFoody_no() %>"><%= foody.getFoody_title() %></a></td>
                    <td><%= foody.getFoody_name() %></td>
                    <td><%= foody.getReg_date() %></td>
                </tr>
            <% } %>
						
						
					</tbody>
				</table>
			</div>
	
	</section>
		<%@page import="com.foodybuddy.common.sql.Paging, java.util.List" %>
		<% Foody paging = (Foody)request.getAttribute("paging"); %>
		<% if(paging != null){ %>
			<div class="center">
				<div class="pagination">
					<% if(paging.isPrev()){ %>
					<a href="/foody/list?nowPage=<%=(paging.getPageBarStart()-1)%>">
					&laquo;
					</a>
					<%} %>
					<% for(int i = paging.getPageBarStart() ; i <= paging.getPageBarEnd() ; i++){ %>
					<a href="/foody/list?nowPage=<%=i%>"
						<%=paging.getNowPage() == i ? "class='active'" : "" %>>
						<%=i %>
					</a>
					<%} %>
					<% if(paging.isNext()){ %>
						<a href="/foody/list?nowPage=<%=(paging.getPageBarEnd()+1)%>">&raquo;</a>
					<%} %>
				</div>
			</div>
		<% } %> 
	<script src="js/theme.js"></script>
</body>
</html>
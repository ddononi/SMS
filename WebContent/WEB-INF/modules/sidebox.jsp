 <%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>	    
<%@ page import="kr.go.police.board.*" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	BoardDAO dao  = new BoardDAO();
	List<BoardBean> list = (List<BoardBean>)dao.getRecentNoticeList(5);
%>    
<c:set var="list"  value ="<%=list %>" />
    	<div id="subWrap">
        	<div class="user_box">
            	<ul>
                	<li><strong><%=session.getAttribute("name") %> 님</strong> 반갑습니다.</li>
                    <li>이달의 남은 건수 : <span><%=session.getAttribute("sendLimit") %></span> 건</li>
                </ul>
                <div class="btn">
            		<a href="./LogoutAction.ac"><img src="./images/boder/btn_logout.gif" alt="로그아웃" border="0"/></a>
                    <a href="./MyInfoAction.ac"><img src="./images/boder/btn_myinfo.gif" alt="나의정보" border="0"/></a>
                    <a href="./MyMessageAction.sm"><img src="./images/boder/btn_myletter.gif" alt="내문자함" border="0"/></a>                
                </div>
            </div>
            <div class="notice_box">
            	<div class="notice_box_title">
                	<h2><img src="./images/boder/title_notice.gif" alt="공지사항" /></h2>
                    <a href="./NoticeListAction.bo"><img src="./images/boder/btn_more.gif" alt="more" /></a>
                </div> 
                <ul>
					<!-- 공지사항 -->
					<c:forEach var="data"  items="${list}" >
						<li><img src="./images/boder/bullet.gif" /> 
                        		<a href="./NoticeDetailView.bo?index=${data.index}" > 
                   					${data.title}</a></li>
					</c:forEach>	
                </ul>
            </div>
        </div>
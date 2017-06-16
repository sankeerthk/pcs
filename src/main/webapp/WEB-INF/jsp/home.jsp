<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp" />
<div class="mainContainer">
	<div class="wrapper">
		<div class="row">
			<div class="col-md-12">
				<div class="row">
				<h4>Home</h4>
					<div class="col-md-6">
						<div class="col-md-3">
							<h2>welcome ${user}</h2>
						</div>
						<div class="col-md-3"><h2>Employee ID: ${hello}</h2></div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp" />
<div class="container">
	<div class="wrapper">
		<div class="row">
			<div class="col-md-12">
				<div class="row pcsTitle" style="margin-top: 20px;">
				<div class="logo-pcs">
					<img src="/static/images/pioneer-logo.png" alt="Pioneer Consulting Services LLC">
				</div>
					<div class="col-md-12 heading">
						<h2>Pioneer Consulting Services LLC Time Sheets Management</h2>
						<hr>
					</div>
				</div>
				<c:if test="${logout != null}">
					<div class="alert alert-success col-md-12 mt10">
						<p>You have been logged out successfully.</p>
					</div>
				</c:if>
				<c:if test="${reset != null}">
					<div class="alert alert-info col-md-12 mt10">
						<c:out value="${reset}" />
					</div>
				</c:if>
				<c:if test="${accessDenied != null}">
					<div class="alert alert-danger  col-md-12 mt10">
						<font color="red"><c:out value="${accessDenied}" /></font>
					</div>
				</c:if>
				<div class="row mt20">
					<div class="col-md-6 subHeading">
						<h1>Please Log In:</h1>
					</div>
				</div>
				<div class="row mt10">
					<div class="col-md-6 col-sm-6 col-xs-12">
						<form method="post" class="form-horizontal">

							<div class="row">
								<div class="col-md-10 col-sm-10 col-xs-10 subHeading">
									<h2 for="exampleInputEmail1">Email address</h2>
									<input type="email" class="form-control bordered" id="userName" max="60" name="userName" placeholder="Enter Email Address" required>
								</div>
							</div>

							<div class="row">
								<div class="col-md-10 col-sm-10 col-xs-10 subHeading">

									<h2 for="exampleInputPassword1">Password</h2>
									<input type="password" class="form-control bordered" id="password" max="6" name="password" placeholder="Enter Password" required>

								</div>
							</div>
							<div class="row mt20">
								<div class="col-md-10 col-sm-10 col-xs-10">
									<input type="submit" class="btn btn-primary btn-sm" name="mySubmitBtn" value="Sign In">
								</div>
							</div>
							<div class="row">
								<div class="col-md-10 col-sm-10 col-xs-10 pt30">
									<a href="<c:url value='/forgotPassword'/>">I forgot my password.</a>
								</div>
							</div>
							<%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
						</form>
					</div>
				</div>

				<!-- /row -->
			</div>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />
				
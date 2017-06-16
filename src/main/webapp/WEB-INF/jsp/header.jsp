<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<html class="no-js" lang="">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Pioneer Time Sheets Portal</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="apple-touch-icon" href="apple-touch-icon.png">
<link href='https://fonts.googleapis.com/css?family=PT+Sans:400,700,400italic' rel='stylesheet' type='text/css'>
<link rel="icon" type="image/vnd.microsoft.icon" href="/static/images/pioneer-logo.png" />
<link rel="shortcut icon" type="image/vnd.microsoft.icon" href="/static/images/pioneer-logo.png" />

<link rel="stylesheet" href="/static/css/bootstrap.min.css">
<link rel="stylesheet" href="/static/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="/static/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" href="/static/css/main.css">
<script src="/static/js/jquery-1.11.2.min.js"></script>
<script src="/static/js/modernizr-2.8.3-respond-1.4.2.min.js"></script>
 
 </head>

 <body>


	<div class="navbar-header">
		<button class="navbar-toggle collapsed mobile-menu-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse"
			aria-controls="bs-navbar" aria-expanded="false">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
	</div>

	<nav id="bs-navbar" class="collapse navbar-collapse">
		<div class="navContainer">
			<ul class="nav">
				<li class="col-md-12"><a target="_blank" href="http://www.pioneeritconsulting.com/Sites/index.aspx">Pioneer Home Page</a></li>
			</ul>
		</div>
	</nav>
	
	<div class="mainContainer">
		<header class="navbar navbar-masthead navbar-default container" role="banner">


			<%-- <div class="pcs-logo row mb10">
				<div class="col-xs-5 col-sm-3 col-md-3">
					<c:choose>
						<c:when test="${loggedInUser != null}">
							<a href="<c:url value='/user/authorization'/>"><img src="/static/images/pioneer-logo.png" class="img-responsive" /></a>
						</c:when>
						<c:otherwise>
							<a href="<c:url value='/login'/>"><img src="/static/images/pioneer-logo.png" class="img-responsive" /></a>
						</c:otherwise>
					</c:choose>
				</div>
				<c:if test="${loggedInUser != null}"> 
					<div class="col-xs-5 col-xs-offset-2 col-sm-3 col-sm-offset-6 col-md-3 col-md-offset-6">
						<a href="<c:url value="/logout" />">
							<button type="submit" class="btn btn-default ">Logout</button>
						</a>
					</div>
					<div class="col-xs-5 col-xs-offset-2 col-sm-3 col-sm-offset-6 col-md-3 col-md-offset-6 mt10">
						<span class="loggedInUser"> ${loggedInUser.getUserName()} </span>
					</div>
				</c:if>
			</div> --%>
		</header>
</div>
 
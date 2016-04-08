<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="shortcut icon" href="/AppBenchmark/resources/images/hike_logo.ico" type="image/x-icon"> 
<title>AppBenchmark</title>
<!-- <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script> -->
<spring:url value="/resources/css/header.css" var="headerCSS" />
<spring:url value="/resources/css/styles.css" var="styleCSS" />
<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS" />
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />

<script type="text/javascript" src="${jqueryJS}"></script>
<script type="text/javascript" src="${bootstrapJS}"></script>
<link href="${bootstrapCSS}" rel="stylesheet" />
<link href="${styleCSS}" rel="stylesheet" />
<link href="${headerCSS}" rel="stylesheet" />
<!-- <script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
	

</head>
<header class="header-basic-light">

<div class="header-limiter">
	<img src="/AppBenchmark/resources/images/hike_logo.png" alt="hike" />

	<h1>
		<a href="/AppBenchmark"><span>App Benchmark</span></a>
	</h1>


</div>
<div id="cssmenu" class="align-center">

	<ul class="menu">
		<li><a href="/AppBenchmark/triggerBenchmark">Benchmark</a></li>
		<li><a href="/AppBenchmark">View Graphs</a></li>
		<li><a href="/AppBenchmark/diffStats">Diff Stats</a></li>
		<!-- <li><a href="/AppBenchmark/pricing">Pricing</a></li> -->
		
	</ul>
</div>
</header>
<body>
</body>
</html>
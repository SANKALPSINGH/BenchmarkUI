<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <html>

            <head>
                <title>AppBenchmark</title>
                <jsp:include page="header.jsp" />
                <spring:url value="/resources/css/main.css" var="mainCSS" />
                <spring:url value="/resources/js/runTest.js" var="runTestJS" />

                <link href="${mainCSS}" rel="stylesheet" />
                <script src="${runTestJS}"></script>
                <script type="text/javascript">
                    $(document).ready(function() {
                        /* $(this).tooltip(); */
                        $('[data-toggle="tooltip"]').tooltip();
                    });
                </script>
            </head>

            <body>

                <section>
                    <div id="content" align="center">
                        <form id="tiggerBenchmarkRun" action="${pageContext.request.contextPath}/triggerBenchmark/newBenchmarkRun" class="form-horizontal">
                            <div class="flexparent">
                                <div id="buildApk" align="left">
                                    <label for="apkBranchInput" class="pull-left">Enter android client git repo's branch to build apk and run benchmark on : </label>
                                    <input type="text" id="apkBranchInput" class="form-control" placeholder="Enter branch"></input>
                                </div>
                                <div>
                                    <input data-toggle="tooltip" data-placement="left" title="Trigger benchmarking process" value="Trigger benchmarking" type="submit">
                                </div>
                        </form>
                        <div id="runConfirmation"></div>

            </body>

            </html>
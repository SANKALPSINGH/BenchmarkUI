<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <html>

            <head>
                <title>AppBenchmark</title>
                <jsp:include page="header.jsp" />

                <spring:url value="/resources/css/main.css" var="mainCSS" />
                <spring:url value="/resources/js/bootstrap-multiselect.js" var="multiselectJS" />
                <spring:url value="/resources/js/report.js" var="reportJS" />
                <spring:url value="/resources/css/bootstrap-multiselect.css" var="multiselectCSS" />

                <link href="${mainCSS}" rel="stylesheet" />
                <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
                <script src="${multiselectJS}"></script>
                <script src="${reportJS}"></script>
                <link href="${multiselectCSS}" rel="stylesheet" />
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
                        <form id="getDataForGraphForm" action="${pageContext.request.contextPath}/getDataForGraph" class="form-horizontal">
                            <div class="flexparent">
                                <div class="form-group">
                                    <label class="col-lg-5 control-label">Select Action : </label>
                                    <div class="col-lg-5">
                                        <select id="action" class="form-control">
                                            <c:forEach var="eachAction" items="${actions}">
                                                <option value="${eachAction}">${eachAction}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-5 control-label">Select Apks : </label>
                                    <div class="col-lg-5">
                                        <select id="apk" class="form-control" multiple="multiple">
                                            <c:forEach var="eachApk" items="${apks}">
                                                <option value=${eachApk}>${eachApk}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div>
                                    <input data-toggle="tooltip" data-placement="left" title="Draw graph" value="Show graph" type="submit">
                                </div>
                            </div>
                        </form>
                    </div>
                   <div id="chart_div" style="width: 900px; height: 500px;"></div>
               </section>
            </body>

            </html>

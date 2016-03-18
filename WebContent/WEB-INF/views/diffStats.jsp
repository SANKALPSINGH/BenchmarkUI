<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <html>

            <head>
                <title>AppBenchmark</title>
                <jsp:include page="header.jsp" />
                <spring:url value="/resources/css/main.css" var="mainCSS" />
                <spring:url value="/resources/js/diffStats.js" var="diffStatsJS" />

                <link href="${mainCSS}" rel="stylesheet" />
                <script src="${diffStatsJS}"></script>
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
                        <form id="diffStatsForm" action="${pageContext.request.contextPath}/diffStats/getDiffStats" class="form-horizontal">
                            <div class="flexparent">
                                <div class="form-group">
                                    <label class="col-lg-5 control-label">Select Apks : </label>
                                    <div class="col-lg-5">
                                        <select id="apk" class="form-control">
                                            <c:forEach var="eachApk" items="${apks}">
                                                <option value=${eachApk}>${eachApk}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div>
                                    <input data-toggle="tooltip" data-placement="left" title="Get Diff data" value="Get Diff data" type="submit">
                                </div>
                            </div>
                        </form>
                        <div class="flexparent">
                        <div id="diffData">
                            <table border="1" class="table table-hover" style="display:hidden" id="diffTable">
                        <tr style="background: #3db2e1;">
                            <th>Percentile User</th>
                            <th>50th</th>
                            <th>80th</th>
                            <th>95th</th>
                            <th>99th</th>
                        </tr>
                        <tr>
                            <th>Chat Opening</td>
                            <td id="50CO"></td>
                            <td id="80CO"></td>
                            <td id="95CO"></td>
                            <td id="99CO"></td>
                        </tr>

                        <tr>
                            <th>Chat Scrolling</td>
                            <td id="50CS"></td>
                            <td id="80CS"></td>
                            <td id="95CS"></td>
                            <td id="99CS"></td>
                        </tr>

                        <tr>
                            <th>App Opening</td>
                            <td id="50AO"></td>
                            <td id="80AO"></td>
                            <td id="95AO"></td>
                            <td id="99AO"></td>
                        </tr>

                        <tr>
                            <th>App 'onCreate' call</td>
                            <td id="50OC"></td>
                            <td id="80OC"></td>
                            <td id="95OC"></td>
                            <td id="99OC"></td>
                        </tr>

                        <tr>
                            <th>App 'onCreate and onResume' call</td>
                            <td id="50OCR"></td>
                            <td id="80OCR"></td>
                            <td id="95OCR"></td>
                            <td id="99OCR"></td>
                        </tr>

                        <tr>
                            <th>Compose Screen</td>
                            <td id="50COS"></td>
                            <td id="80COS"></td>
                            <td id="95COS"></td>
                            <td id="99COS"></td>
                        </tr>
                    </table>
                        </div>

            </body>

            </html>
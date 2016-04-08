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
                <spring:url value="/resources/css/magnific-popup.css" var="magneficPopupCSS" />
                <spring:url value="/resources/js/jquery.magnific-popup.js" var="magenficpopupJS" />

                <link href="${mainCSS}" rel="stylesheet" />
                <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
                <script src="${multiselectJS}"></script>
                <script src="${reportJS}"></script>
                <link href="${multiselectCSS}" rel="stylesheet" />
                <link href="${magneficPopupCSS}" rel="stylesheet" />
                <script src="${magenficpopupJS}"></script>
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
                         <div class="flexparent">
                            <div> The base apk taken for comparison is : <b>4.0.8.81</b></div>
                        </div>
                    </div>
                   <div id="chart_div" style="width: 900px; height: 500px;"></div>
                   <div class="flexaparent">
                    <div id="details" class="hideElement"><a class="open-popup-link">Click here for steps performed to take readings</a></div>
                </div>
               </section>

               <div id="app-force-stop" class="white-popup mfp-hide">
                    <ol>
                        <li>Import contacts.vcf file to the native android address book. Address Book for the respective percentile user has been created as per the Data shared by the Data Team.</li>
                        <li>Install Hike obfuscated build.</li>
                        <li>Signup with the required percentile user.</li>
                        <li>Restore the backup created for the percentile user as per the Data shared by the Data Team.</li>
                        <li>Force Stop the app using </b>"adb shell am force-stop com.bsb.hike"</b>.</li>
                        <li>Launch app and calculate the Launch time.</li>
                        <li>Repeat Step #5 and #6 for next four iterations.</li>
                        <li>Calculate the average of the above five readings.</li>
                    </ul>
                </div>

                <div id="app-force-kill" class="white-popup mfp-hide">
                    <ol>
                        <li>Import contacts.vcf file to the native android address book. Address Book for the respective percentile user has been created as per the Data shared by the Data Team.</li>
                        <li>Install Hike obfuscated build.</li>
                        <li>Signup with the required percentile user.</li>
                        <li>Restore the backup created for the percentile user as per the Data shared by the Data Team.</li>
                        <li>Force Kill the app using UIAutomator (swiping the app off from Recents).</li>
                        <li>Launch app and calculate the Launch time.</li>
                        <li>Repeat Step #5 and #6 for next four iterations.</li>
                        <li>Calculate the average of the above five readings.</li>
                    </ul>
                </div>

                <div id="chat-thread-opening" class="white-popup mfp-hide">
                    <ol>
                        <li>Import contacts.vcf file to the native android address book. Address Book for the respective percentile user has been created as per the Data shared by the Data Team.</li>
                        <li>Install Hike obfuscated build.</li>
                        <li>Signup with the required percentile user.</li>
                        <li>Restore the backup created for the percentile user as per the Data shared by the Data Team.</li>
                        <li>Launch the app.</li>
                        <li>Open the first chat thread.</li>
                        <li>Calculate the difference of start and end time of above value.</li>
                        <li>Repeat Step #6 and #7 for next four iterations.</li>
                        <li>Calculate the average of the above five readings.</li>
                    </ul>
                </div>

                <div id="chat-thread-scroll" class="white-popup mfp-hide">
                    <ol>
                        <li>Import contacts.vcf file to the native android address book. Address Book for the respective percentile user has been created as per the Data shared by the Data Team.</li>
                        <li>Install Hike obfuscated build.</li>
                        <li>Signup with the required percentile user.</li>
                        <li>Restore the backup created for the percentile user as per the Data shared by the Data Team.</li>
                        <li>Launch the app.</li>
                        <li>Open the first chat thread.</li>
                        <li>Scroll till the top end of the messages.</li>
                        <li>Calculate the sum of all the DB query time.</li>
                        <li>Repeat Step #6 and #8 for next four iterations.</li>
                        <li>Calculate the average of the above five readings.</li>
                    </ul>
                </div>

                <div id="compose-screen" class="white-popup mfp-hide">
                    <ol>
                        <li>Import contacts.vcf file to the native android address book. Address Book for the respective percentile user has been created as per the Data shared by the Data Team.</li>
                        <li>Install Hike obfuscated build.</li>
                        <li>Signup with the required percentile user.</li>
                        <li>Restore the backup created for the percentile user as per the Data shared by the Data Team.</li>
                        <li>Launch the app.</li>
                        <li>Open the Compose screen.</li>
                        <li>Calculate the difference of start and end time of above value.</li>
                        <li>Press Device Back button twice to kill the app.</li>
                        <li>Repeat Step #5 and #8 for next four iterations.</li>
                        <li>Calculate the average of the above five readings.</li>
                    </ul>
                </div>

            </body>

            </html>

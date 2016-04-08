var action;
$(document).ready(function() {

    $('.open-popup-link').magnificPopup({
        type:'inline',
        midClick: true 
    });

    google.charts.load('current', {
                    'packages': ['corechart']
                });
                

    $('#apk').multiselect({
        includeSelectAllOption: true,
        enableFiltering: true,
    });

    $('#getDataForGraphForm').submit(function(event) {

        //unhide the details link and set apt link href
        var detailsLink = document.getElementById("details");
        detailsLink.className = "unhideElement";

        $('#chart_div').empty();

        action = $('#action').val();
        var detailsLinkAnchor = document.getElementsByClassName("open-popup-link")[0];
        if("App Force Kill".localeCompare(action) == 0) {
            detailsLinkAnchor.setAttribute("href", "#app-force-kill");
        } else if("App Force Stop".localeCompare(action) == 0) {
            detailsLinkAnchor.setAttribute('href', "#app-force-stop");
        } else if("Chat Thread Opening".localeCompare(action) == 0) {
            detailsLinkAnchor.setAttribute('href', "#chat-thread-opening");
        } else if("Chat Thread Scroll".localeCompare(action) == 0) {
            detailsLinkAnchor.setAttribute('href', "#chat-thread-scroll");
        } else if("Contact Loading time in Compose screen".localeCompare(action) == 0) {
            detailsLinkAnchor.setAttribute('href', "#compose-screen");
        }

        var apkElement = document.getElementById("apk");
        var apksSelected = "";
        for (var i = 0; i < apkElement.options.length; i++) {
            if (apkElement.options[i].selected) {
                apksSelected = apksSelected + "," + apkElement.options[i].value;
            }

        }

        var json = {
            "action": action,
            "apk": apksSelected
        }

        $.ajax({
            url: $('#getDataForGraphForm').attr("action"),
            data: JSON.stringify(json),
            dataType: "json",
            type: "POST",

            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },

            success: function(successData) {

                google.charts.setOnLoadCallback(drawVisualization);
                function drawVisualization() {
                    // Some raw data (not necessarily accurate)
                    
                    var data = new google.visualization.DataTable(successData);

                    var options = {
                        title: action,
                        vAxis: {
                            title: 'Time (in ms)'
                        },
                        hAxis: {
                            title: 'Percentile User'
                        },
                        seriesType: 'bars'
                    };

                    var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
                    console.log(data.toJSON());
                    chart.draw(data, options);
                }
            },
            error: function(status) {
                console.log(status);
            }
        });
        event.preventDefault();
        return false;
    });

});
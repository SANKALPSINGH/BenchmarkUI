var action;
$(document).ready(function() {
    google.charts.load('current', {
                    'packages': ['corechart']
                });
                

    $('#apk').multiselect({
        includeSelectAllOption: true,
        enableFiltering: true,
    });

    $('#getDataForGraphForm').submit(function(event) {

        $('#chart_div').empty();

        action = $('#action').val();

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
                            title: 'Percentile'
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
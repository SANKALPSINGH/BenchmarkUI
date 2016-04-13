$(document).ready(function() {

    $('.open-popup-link').magnificPopup({
        type:'inline',
        midClick: true 
    });


$('#diffStatsForm').submit(function(event) {

    var apksSelected = $('#apk').val();
    var json = {
        "apk": apksSelected
    }

    $.ajax({
        url: $('#diffStatsForm').attr("action"),
        data: JSON.stringify(json),
        dataType: "json",
        type: "POST",

        beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },

        success: function(successData) {

            var tableTag = document.getElementById("diffTable");
            tableTag.style.display = 'table';

            $('#50CO').html(successData["50CO"]);
            $('#80CO').html(successData["80CO"]);
            $('#95CO').html(successData["95CO"]);
            $('#99CO').html(successData["99CO"]);

            $('#50CS').html(successData["50CS"]);
            $('#80CS').html(successData["80CS"]);
            $('#95CS').html(successData["95CS"]);
            $('#99CS').html(successData["99CS"]);

            $('#50COS').html(successData["50COS"]);
            $('#80COS').html(successData["80COS"]);
            $('#95COS').html(successData["95COS"]);
            $('#99COS').html(successData["99COS"]);

            $('#50AO').html(successData["50AO"]);
            $('#80AO').html(successData["80AO"]);
            $('#95AO').html(successData["95AO"]);
            $('#99AO').html(successData["99AO"]);

            $('#50OC').html(successData["50OC"]);
            $('#80OC').html(successData["80OC"]);
            $('#95OC').html(successData["95OC"]);
            $('#99OC').html(successData["99OC"]);

            $('#50OCR').html(successData["50OCR"]);
            $('#80OCR').html(successData["80OCR"]);
            $('#95OCR').html(successData["95OCR"]);
            $('#99OCR').html(successData["99OCR"]);

            $('td').each(function(i, n) {
                if ($(n).text().match("^\\+")) $(n).css('color', 'red');
                else if ($(n).text().match("^-")) $(n).css('color', 'green');
            });

        },
        error: function(status) {
            console.log(status);
        }
    });
    event.preventDefault();
    return false;
});
});
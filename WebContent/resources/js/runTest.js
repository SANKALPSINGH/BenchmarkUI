$(document).ready(function() {

    $('#tiggerBenchmarkRun').submit(function(event) {

        apkBranch = document.getElementById("apkBranchInput").value;
        var json = {
            "apkBranch": apkBranch
        };

        $.ajax({
            url: $('#tiggerBenchmarkRun').attr("action"),
            data: JSON.stringify(json),
            dataType: "json",
            type: "POST",

            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },

            success: function(successData) {
                var runConfirmationDiv = document.getElementById("runConfirmation");
                var runId = successData["runId"];
                if ("0".localeCompare(runId) == 0) {
                    //error in validation
                    var errorMessage = successData["errorField"];
                    runConfirmationDiv.innerHTML = "Your request was rejected due to errors. Error found : ".concat(errorMessage);
                } else {
                    runConfirmationDiv.innerHTML = "Your request has been registered. Your identifier is : ".concat(runId).concat(". You can check the graph after the sanity is performed. That usually takes 4-6 hours.");
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
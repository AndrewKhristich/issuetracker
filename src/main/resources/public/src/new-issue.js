$(document).ready(function () {
    $("#issue-form").submit(function (event) {
        var username = $("#username").text();
        var description = $("#description").val();
        var issueName = $("#issue-name").val();
        var userDTO = {
            issueName: issueName,
            description: description,
            username: username
        };
        var resultJson = JSON.stringify(userDTO);
        if (issueName !== "" && description !== "") {
            sentUserData(resultJson, "/issues/new");
            event.preventDefault();
        } else {
            alert("Both fields must be required");
            event.preventDefault();
        }
    })

    function sentUserData(json, url) {
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json",
            data: json,
            success: function () {
                window.location.replace("/issues");
            },
            error: function (result) {
                alert("Issue creation error! \n " +
                    "status :  " + result.status + " "
                    + result.statusText + "\n" +
                    "Messege : " + result.responseText);
            }

        });
    }
});
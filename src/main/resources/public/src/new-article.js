$(document).ready(function () {
    $("#article-form").submit(function (event) {
        var username = $("#username").text();
        var description = $("#description").val();
        var articleName = $("#article-name").val();
        var userDTO = {
            articleName: articleName,
            description: description,
            username: username
        };
        var resultJson = JSON.stringify(userDTO);
        if (articleName !== "" && description !== "") {
            sentUserData(resultJson, "/articles/new");
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
                window.location.replace("/articles");
            },
            error: function (result) {
                alert("Article creation error! \n " +
                    "status :  " + result.status + " "
                    + result.statusText + "\n" +
                    "Messege : " + result.responseText);
            }

        });
    }
});
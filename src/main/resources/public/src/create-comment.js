$(document).ready(function () {
    $("#commentDescription").val("");
    var urlParameter = getUrlParameter("id");
    $("#comment-form").submit(function (event) {
        var current_user = $("#current-user").text();
        var selection = $("#selection").val();
        var commentDescription = $("#commentDescription").val();
        var userDTO = {
            userName : current_user,
            issueId : urlParameter,
            commentDescription : commentDescription,
            status : selection
        };
        var resultJson = JSON.stringify(userDTO);
        if (commentDescription !== "") {
            sentUserData(resultJson);
            // username.val("");
            // password.val("");
            event.preventDefault();
        } else {
            alert("Add your issue solution");
            event.preventDefault();
        }
    });

    function sentUserData(json) {
        $.ajax({
            type: "POST",
            url: "/issues/comment",
            contentType: "application/json",
            data: json,
            success: function () {
                window.location.replace("/issues/art?id=" + urlParameter);
            },
            error: function () {
                alert("Some error");
            }

        });
    }
});
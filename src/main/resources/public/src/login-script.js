function doPost (event, url) {
    var username = $("#username").val();
    var password = $("#password").val();
    var userDTO = {
        name: username,
        password: password
    };
    var resultJson = JSON.stringify(userDTO);
    if (username !== "" && password !== "") {
        sentUserData(resultJson, url);
        // username.val("");
        // password.val("");
        event.preventDefault();
    } else {
        alert("Both fields must be required");
        event.preventDefault();
    }
};

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
            alert("Registration error! status :  " + result.status + " "
                + result.statusText +
                " ... Messege : " + result.responseText);
        }

    });
}
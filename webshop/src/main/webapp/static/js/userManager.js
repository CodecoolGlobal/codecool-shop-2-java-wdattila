alert("here we go again")

function showRelevantUserButtons(){
    sessionStorage.setItem("username","test")
    let username = sessionStorage.getItem("username")
    let buttonContainer = document.getElementById("user-buttons")
    if(username === null){
        buttonContainer.innerHTML =
            "<li class=\"nav-item\">" +
                "<div class=\"btn-nav\"><a class=\"btn btn-secondary\" href=\"/login\">" +
                    "Login" +
                "</a>" +
                "</div>" +
            "</li>" +
            "<li class=\"nav-item\">" +
                "<div class=\"btn-nav\"><a class=\"btn btn-secondary\" href=\"/register\">" +
                    "Register" +
                "</a>" +
                "</div>" +
            "</li>"
    }else{
        buttonContainer.innerHTML =
            "<li class=\"nav-item\">" +
                "<div class=\"btn-nav\"><a class=\"btn btn-secondary\" href=\"/logout\">" +
                    "Logout" +
                "</a>" +
                "</div>" +
            "</li>" +
            "<li>"+
                `Logged in as: ${username}`+
            "</li>"
    }
}

showRelevantUserButtons()
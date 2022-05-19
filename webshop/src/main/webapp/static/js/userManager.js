import {dataHandler} from "./dataHandler.js";

function showRelevantUserButtons(){
    let username = sessionStorage.getItem("username")
    let buttonContainer = document.getElementById("user-buttons")
    if(username === null){
        removeSaveCartButton()
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
        createSaveCartButton()
        buttonContainer.innerHTML =
            "<li class=\"nav-item\">" +
                "<div class=\"btn-nav\"><a class=\"btn btn-secondary\" href=\"/logout\" id='logout'>" +
                    "Logout" +
                "</a>" +
                "</div>" +
            "</li>" +
            "<li>"+
                `<div style='color:white; margin-top: 5px; margin-left: 10px'> Logged in as: ${username} </div>`+
            "</li>"
        initLogoutButton()
    }
}

function createSaveCartButton(){
    let modalFooter = document.getElementsByClassName("modal-footer")[0]
    let saveCartDiv = document.createElement("div")
    let saveCartButton = document.createElement("button")
    saveCartDiv.classList.add("btn-nav")
    saveCartDiv.id = "saveCart"
    saveCartButton.type = "button"
    saveCartButton.classList.add("btn")
    saveCartButton.classList.add("btn-success")
    saveCartButton.innerText = "Save Cart"

    saveCartDiv.appendChild(saveCartButton)
    modalFooter.appendChild(saveCartDiv)

    initSaveCartButton()

}

function initSaveCartButton(){
    let saveCartDiv = document.getElementById("saveCart")
    saveCartDiv.addEventListener("click",saveCartButtonClicked)
}

function saveCartButtonClicked(){
    dataHandler.save_cart(localStorage,sessionStorage.getItem("userid"))
}

function removeSaveCartButton(){
    if(document.getElementById("saveCart") !== null){
        let saveCartDiv = document.getElementById("saveCart")
        console.log(saveCartDiv)
        saveCartDiv.remove()
    }


}

function initLogoutButton(){
    let logoutButton = document.getElementById("logout")
    logoutButton.addEventListener("click",removeSession)
}

function removeSession(){
    sessionStorage.removeItem("username")
    sessionStorage.removeItem("userid")
}

async function getSessionDataFromServer(){
    let sessionData = await dataHandler.get_session_data()
    console.log(sessionData)
    if(!isEmptyJSON(sessionData)){
        sessionStorage.setItem("username",sessionData["username"])
        sessionStorage.setItem("userid",sessionData["userid"])
    }
}

function isEmptyJSON(obj) {
    return Object.keys(obj).length === 0;
}



removeSession()
await getSessionDataFromServer()
showRelevantUserButtons()

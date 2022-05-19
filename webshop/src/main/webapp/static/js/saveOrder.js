import {dataHandler} from "./dataHandler.js";

async function postForm(){
    let form = document.body.querySelector(".form");
    let object = {};
    let formDataObject = new FormData(form);
    await formDataObject.forEach(function(value, key){
        object[key] = value;
    });
    await dataHandler.save_order(object);
}

async function handlePaymentClick(event){
    await postForm();
    console.log("hello")
    let data = await dataHandler.get_payment_info(localStorage);
    console.log("2")
    postToPayment(data)
    localStorage.clear()
}

function postToPayment(data){
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = new URL(data.targetUrl, window.location.href).href;
    document.body.appendChild(form);

    for (const key in data) {
        const formField = document.createElement('input');
        formField.type = 'hidden';
        formField.name = key;
        formField.value = data[key];

        form.appendChild(formField);
    }
    form.submit();
}

document.body.querySelector("#btnSubmit").addEventListener("click", handlePaymentClick);
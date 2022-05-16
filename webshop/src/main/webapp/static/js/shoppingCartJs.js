import {dataHandler} from "./dataHandler.js";

function initAddToCart(){
    const addButtons = document.getElementsByClassName("addToCart")
    for (let addButton of addButtons){
        addButton.addEventListener("click",addProduct)
    }
}

function initChangeQuantity(){
    const quantityInputFields = document.getElementsByClassName("productQuantity")
    for(let input of quantityInputFields){
        input.addEventListener("change",changeProductQuantity)
    }
}

async function loadCart(){
    const productsData = await getProductsForCart()
    insertProductDataToModal(productsData)
}

function enablePaymentButton(){
    const paymentButton = document.getElementById("payment")
    if(paymentButton.disabled){
        paymentButton.removeAttribute("disabled")
    }

}

function addProduct(e){
    addProductToLocalStorage(e)
    loadCart()
    enablePaymentButton()
}

function addProductToLocalStorage(click){
    let productId = click.target.getAttribute("productid")
    if(localStorage.getItem(productId)){
        localStorage.setItem(productId, String(parseInt(localStorage.getItem(productId))+1))
    }else{
        localStorage.setItem(productId,"1")
    }
}

function getProductsForCart(){
    let productIds = Object.keys(localStorage)
    let joinedIds = productIds.join(",")
    return dataHandler.get_products_by_Id(joinedIds)
}

function insertProductDataToModal(productsData){
    const cartList = document.getElementById("cartItems")
    let productRows = "<table class=\"table table-bordered\">" +
        "<thead>" +
            "<tr>" +
            "<th class=\"col col-4\">Product Name</th>" +
            "<th class=\"col col-2\">Unit Price</th>" +
            "<th class=\"col col-2\">Quantity</th>" +
            "<th class=\"col col-3\">Sum</th>" +
        "</tr>" +
        "<tbody>"
    let overallPrice = 0;
    for (const productData of productsData) {
        let productQuantity = localStorage.getItem(String(productData["id"]))
        let productSum = productData["defaultPrice"]*productQuantity
        overallPrice+=productSum
        productRows+="<tr>" +
                        `<td>${productData["name"]}</td>` +
                        `<td>${productData["defaultPrice"]} EUR</td>` +
                        `<td><input data-product-id=${productData["id"]} class="productQuantity" type="number" width='10px'
                            min="0" max="100" step="1" value=${productQuantity}></td>` +
                        `<td>${productSum.toFixed(2)} EUR</td>` +
                    "</tr>"
    }
    productRows+="<tr>" +
        "<td colspan='2'></td>" +
        "<td style='text-align: right'><strong>Overall Price:</strong> </td>"+
        `<td class='overallValue'>${overallPrice.toFixed(2)} EUR</td>`+
        "</tr>" +
        "</table>"
    cartList.innerHTML=productRows
    initChangeQuantity()
}

function changeProductQuantity(e){
    let quantity = e.target.value
    let productId = e.target.getAttribute("data-product-id")
    localStorage.setItem(productId,quantity)
    if(parseInt(localStorage.getItem(productId)) === 0){
        localStorage.removeItem(productId)
        if(!isEmptyCart()){
            manageEmptyCart()
        }else {
            loadCart()
        }
    }else{
        loadCart()
    }

}

function manageEmptyCart(){
    const paymentButton = document.getElementById("payment")
    const cartModal = document.getElementById("cartItems")
    paymentButton.setAttribute("disabled","true")
    cartModal.innerHTML = "<p>The cart is Empty!</p>"
}

function isEmptyCart(){
    return Object.keys(localStorage).length > 0
}

async function handlePaymentClick(event){
    let data = await dataHandler.get_payment_info(localStorage);
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

initAddToCart()
if(isEmptyCart()){
    loadCart()
}else{
    manageEmptyCart()
}
document.querySelector("#payment").addEventListener("click", handlePaymentClick);
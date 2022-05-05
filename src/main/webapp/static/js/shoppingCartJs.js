import {dataHandler} from "./dataHandler.js";

function initAddToCart(){
    const addButtons = document.getElementsByClassName("addToCart")
    for (let addButton of addButtons){
        addButton.addEventListener("click",addProductToSession)
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

async function addProductToSession(click){
    let productId = click.target.getAttribute("productid")
    if(localStorage.getItem(productId)){
        localStorage.setItem(productId, String(parseInt(localStorage.getItem(productId))+1))
    }else{
        localStorage.setItem(productId,"1")
    }
    loadCart()
}

function getProductsForCart(){
    let productIds = Object.keys(localStorage)
    let joinedIds = productIds.join(",")
    return dataHandler.get_products_by_Id(joinedIds)
}

function insertProductDataToModal(productsData){
    const cartList = document.getElementById("cartItems")
    let productRows = ""
    let overallPrice = 0;
    for (const productData of productsData) {
        let productQuantity = localStorage.getItem(String(productData["id"]))
        let productSum = productData["defaultPrice"]*productQuantity
        overallPrice+=productSum
        productRows+="<tr>" +
                        `<td>${productData["name"]}</td>` +
                        `<td>${productData["defaultPrice"]} EUR</td>` +
                        `<td><input data-product-id=${productData["id"]} class="productQuantity" type="number" 
                            min="0" max="100" step="1" value=${productQuantity}></td>` +
                        `<td>${productSum.toFixed(2)} EUR</td>` +
                    "</tr>"
    }
    productRows+="<tr>" +
        "<td colspan='3'></td>" +
        `<td class='overallValue'>${overallPrice.toFixed(2)} EUR</td>`
    cartList.innerHTML=productRows
    initChangeQuantity()
}

function changeProductQuantity(e){
    let quantity = e.target.value
    let productId = e.target.getAttribute("data-product-id")
    localStorage.setItem(productId,quantity)
    if(parseInt(localStorage.getItem(productId)) === 0){
        localStorage.removeItem(productId)
    }
    loadCart()
}

async function handlePaymentClick(event){
    let data = await dataHandler.get_payment_info(localStorage);
    console.log(data);
}

initAddToCart()
loadCart()

document.querySelector("#payment").addEventListener("click", handlePaymentClick);
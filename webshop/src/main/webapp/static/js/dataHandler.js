export let dataHandler = {
    get_products_by_Id : function(productIds){
        let url = new URL("api/products",window.location.href)
        url.searchParams.append("products", productIds);
        return apiGet(url)
    },
    get_payment_info : function(data){
        let url = new URL("api/payment",window.location.href)
        return apiPost(url, data);
    },
    save_cart: function(data, user_id){
        let url = new URL("api/save/cart",window.location.href);
        url.searchParams.append("userId", user_id);
        return apiPost(url, data);
    },
    get_session_data : function(){
        let url = new URL("api/session", window.location.href)
        return apiGet(url)
    }
}

async function apiGet(url){
    const response = await fetch(url,{
        method:"GET"
    })
    if(response.status === 200){
        return await response.json()
    }
}

async function apiPost(url, data){
    const response = await fetch(url,{
        method:"POST",
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: JSON.stringify(data)
    })
    if(response.status === 200){
        return await response.json()
    }
}
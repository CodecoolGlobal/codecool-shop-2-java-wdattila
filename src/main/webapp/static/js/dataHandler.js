export let dataHandler = {
    get_products_by_Id : function(productIds){
        let url = new URL("api/products",window.location.href)
        url.searchParams.append("products", productIds);
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
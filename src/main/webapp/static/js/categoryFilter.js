function HandleSelection(event){
    let url = new URL(window.location.href);
    if(this.checked === true){
        if(url.searchParams.has("category")){
            url.searchParams.set("category", url.searchParams.get("category")+','+this.dataset.id);
        }
        else{
            url.searchParams.append("category", this.dataset.id);
        }
    }
    else if(url.searchParams.has("category")){
        let filteredParams = url.searchParams.get("category").split(',').filter(i => i !== this.dataset.id);
        let paramNewValue = filteredParams.join(',');
        if(paramNewValue === ""){
            url.searchParams.delete("category");
        }
        else {
            url.searchParams.set("category", paramNewValue);
        }
    }
    window.location.href = url.href;
}

document.querySelectorAll(".category")
    .forEach(el => el.addEventListener("change", HandleSelection));
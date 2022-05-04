function HandleSelection(event){
    let paramName = this.className
    let url = new URL(window.location.href);
    if(this.checked === true){
        if(url.searchParams.has(paramName)){
            url.searchParams.set(paramName, url.searchParams.get(paramName)+','+this.dataset.id);
        }
        else{
            url.searchParams.append(paramName, this.dataset.id);
        }
    }
    else if(url.searchParams.has(paramName)){
        let filteredParams = url.searchParams.get(paramName).split(',').filter(i => i !== this.dataset.id);
        let paramNewValue = filteredParams.join(',');
        if(paramNewValue === ""){
            url.searchParams.delete(paramName);
        }
        else {
            url.searchParams.set(paramName, paramNewValue);
        }
    }
    window.location.href = url.href;
}

document.querySelectorAll(".category")
    .forEach(el => el.addEventListener("change", HandleSelection));

document.querySelectorAll(".supplier")
    .forEach(el => el.addEventListener("change", HandleSelection));
function HandleSelection(event){
    if(this.checked === true){
        let url = new URL(window.location.href);
        url.searchParams.append("category", this.dataset.id);
        window.location.href = url.href;
    }
}

document.querySelector("#Tablet").addEventListener("change", HandleSelection);
function HandleSelection(event){
    if(this.checked === true){
        let url = new URL(window.location.href);
        url.searchParams.append("category", this.dataset.id);
        window.location.href = url.href;
    }
    else{
        let url = new URL(window.location.href);
        url.searchParams.delete("category");
        window.location.href = url.href;
    }
}

document.querySelectorAll(".category")
    .forEach(el => el.addEventListener("change", HandleSelection));
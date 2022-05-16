function init_CardNumberInput(){
    const input = document.getElementById("cardNumber")
    input.addEventListener('keyup', numberValidation)
    input.addEventListener('keypress', formats)
    input.addEventListener('focusout',formats)
}

function numberValidation(e) {
    e.target.value = e.target.value.replace(/[^\d ]/g, '');
    return false;
}

function formats(e) {
    if (e.target.value.length < 19) {
        e.target.value = e.target.value.replace(/\W/gi, '').replace(/(.{4})/g, '$1 ');
        return true;
    } else {
        return false;
    }
}

init_CardNumberInput()
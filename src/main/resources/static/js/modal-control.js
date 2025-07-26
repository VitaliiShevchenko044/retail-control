document.getElementById("openAddProductModalBtn").onclick = () => {
    document.getElementById("addProductModal").style.display = "block";
};

document.getElementById("closeAddProductModalBtn").onclick = () => {
    document.getElementById("addProductModal").style.display = "none";
};

document.getElementById("closeSearchProductModalBtn")?.addEventListener("click", () => {
    document.getElementById("searchProductModal")?.style.setProperty("display", "none");
});

window.addEventListener("load", () => {
    const hasErrorsElement = document.getElementById("hasErrors");
    if (hasErrorsElement && hasErrorsElement.value === "true") {
        const modal = document.getElementById("addProductModal");
        modal.style.display = "block";
    }
    const hasSuccess = document.getElementById("hasSuccess");
    if (hasSuccess && hasSuccess.value === "true") {
        showTempMessage("successAction", "Збережено успішно!", 3000);
    }
    const hasFailed = document.getElementById("hasFailed");
    if (hasFailed && hasFailed.value === "true") {
        showTempMessage("failedAction", "Товар з таким штрихкодом вже існує!", 3000);
    }
    const isBarcodeBlank = document.getElementById("isBarcodeBlank");
    if (isBarcodeBlank && isBarcodeBlank.value === "true") {
        showTempMessage("failedAction", "Поле не може бути пустим!", 3000);
    }
    const productNotFound = document.getElementById("productNotFound");
    if (productNotFound && productNotFound.value === "true") {
        showTempMessage("failedAction", "Товар не знайдено!", 3000);
    }
    const editSuccess = document.getElementById("editSuccess");
    if (editSuccess && editSuccess.value === "true") {
        showTempMessage("successAction", "Змінено успішно!", 3000);
    }
    const deleteSuccess = document.getElementById("deleteSuccess");
    if (deleteSuccess && deleteSuccess.value === "true") {
        showTempMessage("successAction", "Видалено успішно!", 3000);
    }
});

function showTempMessage(id, message, duration) {
    const toast = document.getElementById(id);
    if (toast) {
        const messageEl = toast.querySelector(".toast-message");
        if (messageEl) {
            messageEl.innerHTML = message;
        }
        toast.style.display = "block";
        setTimeout(() => {
            toast.style.display = "none";
        }, duration)
    }
}
const dropZone = document.getElementById("dropZone");
const imageFileInput = document.getElementById("imageFileInput");

function updateImage(file) {
    dropZone.innerHTML = "";

    const url = URL.createObjectURL(file);
    const img = document.createElement("img");
    img.src = url;
    dropZone.appendChild(img);

    const dataTransfer = new DataTransfer();
    dataTransfer.items.add(file);
    imageFileInput.files = dataTransfer.files;
}

dropZone.addEventListener("paste", event => {
    event.preventDefault();
    const items = event.clipboardData.items;

    for (const item of items) {
        if (item.type.startsWith('image')) {
            updateImage(item.getAsFile());
            return;
        }
    }
    dropZone.textContent = "Форма обробляє лише зображення (до 5 MB)";
});

dropZone.addEventListener('drop', event => {
    event.preventDefault();
    dropZone.style.backgroundColor = "";
    const file = event.dataTransfer.files[0];
    if (file && file.type.startsWith("image/")) {
        updateImage(file);
    } else {
        dropZone.textContent = "Форма обробляє лише зображення (до 5 MB)";
    }
});

dropZone.addEventListener('dragover', event => {
    event.preventDefault();
    dropZone.style.backgroundColor = "#f5f5f5";
});

dropZone.addEventListener('dragleave', () => {
    dropZone.style.backgroundColor = "";
});

dropZone.addEventListener("focus", () => {
    dropZone.style.backgroundColor = "#f5f5f5";
    dropZone.style.border = "2px solid #7f7876";
});

dropZone.addEventListener("blur", () => {
    dropZone.style.backgroundColor = "";
    dropZone.style.border = "";
});
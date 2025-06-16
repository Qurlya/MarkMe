document.getElementById('myBtn').addEventListener('click', function() {
    document.getElementById('myModal').style.display = "flex";
});

// Закрытие модального окна
window.addEventListener('click', function(event) {
    if (event.target == document.getElementById('myModal')) {
        document.getElementById('myModal').style.display = "none";

        // Отправка данных после закрытия модального окна
        fetch('/changeMark', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'nameBoss=hidden&imageBoss=image'
        });
    }
});
/////////// Редактирование:

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementsByName('editForm');

    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Предотвращаем отправку формы по умолчанию

        const url = form.getAttribute('action'); // Получаем URL для отправки запроса

        // Отправляем GET-запрос на указанный URL
        fetch(url, {
            method: 'GET'
        })
            .then(response => {
                // Перезагружаем страницу после получения ответа от сервера
                window.location.reload();
            })
            .catch(error => {
                console.error('Ошибка:', error);
            });
    });
});

window.addEventListener('load', function () {
    // Получаем ссылку на кнопку или элемент, который открывает модальное окно
    const myModal = new bootstrap.Modal(document.getElementById('editModal'));
    // Вызываем метод show() для открытия модального окна
    myModal.show();
});

/////////// Удаление:

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementsByName('deleteForm');

    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Предотвращаем отправку формы по умолчанию

        const url = form.getAttribute('action'); // Получаем URL для отправки запроса

        // Отправляем GET-запрос на указанный URL
        fetch(url, {
            method: 'GET'
        })
            .then(response => {
                // Перезагружаем страницу после получения ответа от сервера
                window.location.reload();
            })
            .catch(error => {
                console.error('Ошибка:', error);
            });
    });
});

window.addEventListener('load', function () {
    // Получаем ссылку на кнопку или элемент, который открывает модальное окно
    const myModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    // Вызываем метод show() для открытия модального окна
    myModal.show();
});

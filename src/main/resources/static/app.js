// Получение элементов DOM
const infoUsersButton = document.getElementById('infoUsersButton');
const userMessageButton = document.getElementById('userMessageButton');
const infoUsersModal = document.getElementById('infoUsersModal');
const userMessageModal = document.getElementById('userMessageModal');
const closeInfoUsers = document.getElementById('closeInfoUsers');
const closeUserMessage = document.getElementById('closeUserMessage');
const messageOutput = document.getElementById('messageOutput');
const submitMessageButton = document.getElementById('submitMessageButton');

// Открытие модального окна Info Users
infoUsersButton.addEventListener('click', async () => {
    try {
        const response = await fetch('/service/getInfoUsers'); // Используем правильный путь
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        const usersTableBody = document.querySelector('#usersTable tbody');
        usersTableBody.innerHTML = ''; // Очищаем таблицу
        data.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `<td>${user.chatId}</td><td>${user.name}</td>`;
            usersTableBody.appendChild(row);
        });
        infoUsersModal.style.display = 'block';
    } catch (error) {
        console.error('Ошибка при получении данных о пользователях:', error);
        alert('Не удалось загрузить информацию о пользователях. Попробуйте снова позже.');
    }
});

// Закрытие модального окна Info Users
closeInfoUsers.addEventListener('click', () => {
    infoUsersModal.style.display = 'none';
});

// Открытие модального окна User Message
userMessageButton.addEventListener('click', () => {
    userMessageModal.style.display = 'block';
});

// Закрытие модального окна User Message
closeUserMessage.addEventListener('click', () => {
    userMessageModal.style.display = 'none';
});

// Обработка отправки сообщения
submitMessageButton.addEventListener('click', async () => {
    const chatId = document.getElementById('chatIdInput').value;
    const from = document.getElementById('fromInput').value;
    const size = document.getElementById('sizeInput').value;

    try {
        const response = await fetch(`/service/getMessage?chatId=${chatId}&from=${from}&size=${size}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();

        // Преобразование данных для более читаемого вывода
        const formatTextList = (items) => items.map(item => `
            <li>
                <span class="message-time">${item.dateTime}</span>
                <span class="message-text">${item.text}</span>
            </li>
        `).join('');

        const formattedText = formatTextList(data.text);
        const formattedBotText = formatTextList(data.textBot);

        messageOutput.innerHTML = `
            <p><strong>Chat ID:</strong> ${data.chatId}</p>
            <p><strong>Name:</strong> ${data.name}</p>
            <p><strong>Text:</strong></p>
            <ul class="message-list">
                ${formattedText}
            </ul>
            <p><strong>Bot Text:</strong></p>
            <ul class="message-list">
                ${formattedBotText}
            </ul>
        `;
    } catch (error) {
        messageOutput.innerHTML = `<p>Ошибка при получении сообщения: ${error.message}</p>`;
    }
});

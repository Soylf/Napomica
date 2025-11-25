function sendParams() {
    const chatId = document.getElementById("chatIdInput").value;
    const from = document.getElementById("fromInput").value || 0;
    const size = document.getElementById("sizeInput").value || 6;

    if (!chatId) {
        alert("Введите chatId");
        return;
    }

    fetch(`/message/service/getMessage?chatId=${chatId}&from=${from}&size=${size}`)
        .then(r => r.json())
        .then(data => {
            openPopup(data);
        })
        .catch(err => console.error("Ошибка:", err));
}

function openPopup(data) {
    document.getElementById("popupChatId").innerText = `ID: ${data.chatId}`;

    const msgBox = document.getElementById("popupMessages");
    msgBox.innerHTML = "";

    const allMessages = [];

    data.text.forEach(t => {
        allMessages.push({
            from: "user",
            text: t.text,
            date: t.dateTime
        });
    });
    data.textBot.forEach(t => {
        allMessages.push({
            from: "bot",
            text: t.text,
            date: t.dateTime
        });
    });

    allMessages.sort((a, b) => new Date(a.date) - new Date(b.date));

    const paired = [];

    for (let i = 0; i < allMessages.length; i++) {
        const msg = allMessages[i];

        if (msg.from === "user") {
            let bot = null;

            for (let j = i + 1; j < allMessages.length; j++) {
                if (allMessages[j].from === "bot") {
                    bot = allMessages[j];
                    break;
                }
            }

            paired.push({ user: msg, bot: bot });
        }
    }

    paired.forEach(p => {
        const userDiv = document.createElement("div");
        userDiv.className = "user-msg";
        userDiv.innerHTML =
            `<strong>${p.user.date}</strong><br>${p.user.text}`;
        msgBox.appendChild(userDiv);

        if (p.bot) {
            const botDiv = document.createElement("div");
            botDiv.className = "bot-msg";
            botDiv.innerHTML =
                `<strong>${p.bot.date}</strong><br>${p.bot.text}`;
            msgBox.appendChild(botDiv);
        }
    });

    document.getElementById("messagePopup").style.display = "block";
}

function closePopup() {
    document.getElementById("messagePopup").style.display = "none";
}

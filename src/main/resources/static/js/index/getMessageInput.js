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

    function parseCustomDate(str) {
        const [time, day, monthName, year] = str.split(" ");
        const [hours, minutes, seconds] = time.split(":");

        const months = {
            "янв.": 0, "февр.": 1, "мар.": 2, "апр.": 3, "мая": 4, "июн.": 5,
            "июл.": 6, "авг.": 7, "сент.": 8, "окт.": 9, "нояб.": 10, "дек.": 11
        };

        return new Date(year, months[monthName], day, hours, minutes, seconds || 0);
    }

    data.text.forEach(t => {
        allMessages.push({
            from: "user",
            text: t.text,
            date: parseCustomDate(t.dateTime)
        });
    });

    data.textBot.forEach(t => {
        allMessages.push({
            from: "bot",
            text: t.text,
            date: parseCustomDate(t.dateTime),
            used: false
        });
    });

    allMessages.sort((a, b) => a.date - b.date);

    const pairs = [];

    for (let i = 0; i < allMessages.length; i++) {
        const msg = allMessages[i];

        if (msg.from === "user") {
            let botReply = null;

            for (let j = i + 1; j < allMessages.length; j++) {
                if (allMessages[j].from === "bot" && !allMessages[j].used) {
                    botReply = allMessages[j];
                    allMessages[j].used = true;
                    break;
                }
            }

            pairs.push({ user: msg, bot: botReply });
        }
    }

    pairs.forEach(p => {
        const userDiv = document.createElement("div");
        userDiv.className = "user-msg";
        userDiv.innerHTML =
            `<strong>${p.user.date.toLocaleString()}</strong><br>${p.user.text}`;
        msgBox.appendChild(userDiv);

        if (p.bot) {
            const botDiv = document.createElement("div");
            botDiv.className = "bot-msg";
            botDiv.innerHTML =
                `<strong>${p.bot.date.toLocaleString()}</strong><br>${p.bot.text}`;
            msgBox.appendChild(botDiv);
        }
    });

    document.getElementById("messagePopup").style.display = "block";
}


function closePopup() {
    document.getElementById("messagePopup").style.display = "none";
}
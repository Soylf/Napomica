function loadUsers() {
    fetch("/message/service/getInfoUsers")
    .then(response => response.json())
    .then(data => {
        const list = document.getElementById("userList");

        data.forEach(user => {
            const item = document.createElement("div");
            item.className = "user-item";
            item.innerHTML = `
                <span>${user.name}</span>
                <span>${user.chatId}</span>
            `;
            list.appendChild(item);
        });
    })
}

document.addEventListener('DOMContentLoaded', () => { loadUsers();});
function openModifyModal(userId, username) {
    document.getElementById('modifyModal').style.display = 'block';
    document.getElementById('modify-username').value = username;
    document.getElementById('modify-userId').value = userId; // Ensure there's a hidden input with id modify-userId
}

function closeModifyModal() {
    document.getElementById('modifyModal').style.display = 'none';
}

function openDeleteModal(userId) {
    document.getElementById('deleteModal').style.display = 'block';
    document.getElementById('confirm-delete').setAttribute('onclick', `confirmDelete(${userId})`);
}

function closeDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none';
}

function toggleNewField() {
    const action = document.getElementById('modify-action').value;
    if (action === 'username') {
        document.getElementById('new-username-div').style.display = 'block';
        document.getElementById('new-password-div').style.display = 'none';
    } else {
        document.getElementById('new-username-div').style.display = 'none';
        document.getElementById('new-password-div').style.display = 'block';
    }
}

function submitModify() {
    const userId = document.getElementById('modify-userId').value;
    const oldPassword = document.getElementById('modify-old-password').value;
    const action = document.getElementById('modify-action').value;
    const newValue = action === 'username' ? document.getElementById('modify-new-username').value : document.getElementById('modify-new-password').value;

    fetch(`/modify-user/${userId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            oldPassword: oldPassword,
            action: action,
            newValue: newValue,
        }),
    })
        .then(response => response.json())
        .then(data => {
            closeModifyModal();
            openNotificationModal(data.message);
        })
        .catch(error => console.error('Error:', error));
}

function confirmDelete(userId) {
    fetch(`/delete-user/${userId}`, {
        method: 'POST',
    })
        .then(response => {
            closeDeleteModal();
            openNotificationModal('User deleted successfully.');
            location.reload();
        })
        .catch(error => console.error('Error:', error));
}

function openNotificationModal(message) {
    document.getElementById('notificationMessage').innerText = message;
    document.getElementById('notificationModal').style.display = 'block';
}

function closeNotificationModal() {
    document.getElementById('notificationModal').style.display = 'none';
}

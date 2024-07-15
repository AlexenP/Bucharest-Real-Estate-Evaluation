function openModifyModal(userId, username) {
    document.getElementById('modifyModal').style.display = 'block';
    document.getElementById('username').value = username;
    document.getElementById('modifyUserId').value = userId;
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
    const action = document.getElementById('action').value;
    const newValueLabel = document.querySelector("label[for='newValue']");
    newValueLabel.textContent = action === 'username' ? 'New Username:' : 'New Password:';
}

function submitModify() {
    const userId = document.getElementById('modifyUserId').value;
    const oldPassword = document.getElementById('oldPassword').value;
    const action = document.getElementById('action').value;
    const newValue = document.getElementById('newValue').value;

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
            if (data.success) {
                openNotificationModal(action === 'username' ? 'Username modified successfully!' : 'Password modified successfully!');
                closeModifyModal();
                location.reload(); // Reload the page to reflect changes
            } else {
                openNotificationModal(data.message);
            }
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

// Function to get the CSRF token from the meta tags
function getCsrfToken() {
    return document.querySelector('meta[name="_csrf"]').getAttribute('content');
}

// Function to get the CSRF header name from the meta tags
function getCsrfHeaderName() {
    return document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
}

function showModifyModal(button) {
    const userId = button.getAttribute('data-user-id');
    const username = button.getAttribute('data-username');
    document.getElementById('modifyUserId').value = userId;
    document.getElementById('username').value = username;
    document.getElementById('modifyModal').style.display = 'block';
}

function showDeleteModal(button) {
    const userId = button.getAttribute('data-user-id');
    document.getElementById('deleteUserId').value = userId;
    document.getElementById('deleteModal').style.display = 'block';
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}

function modifyUser() {
    var userId = document.getElementById('modifyUserId').value;
    var username = document.getElementById('username').value;
    var oldPassword = document.getElementById('oldPassword').value;
    var action = document.getElementById('action').value;
    var newValue = document.getElementById('newValue').value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/modify-user/" + userId, true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.setRequestHeader(getCsrfHeaderName(), getCsrfToken());
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.success) {
                    openNotificationModal(action === 'username' ? 'Username modified successfully!' : 'Password modified successfully!');
                    closeModal('modifyModal');
                    location.reload(); // Reload the page to reflect changes
                } else {
                    openNotificationModal('Error: ' + response.message);
                }
            } else {
                openNotificationModal('Error modifying user.');
            }
        }
    };
    xhr.send(JSON.stringify({
        username: username,
        oldPassword: oldPassword,
        action: action,
        newValue: newValue
    }));
}

function deleteUser() {
    var userId = document.getElementById('deleteUserId').value;

    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/delete-user/" + userId, true);
    xhr.setRequestHeader(getCsrfHeaderName(), getCsrfToken());
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                openNotificationModal('User deleted successfully!');
                closeModal('deleteModal');
                location.reload(); // Reload the page to reflect changes
            } else {
                openNotificationModal('Error deleting user.');
            }
        }
    };
    xhr.send();
}

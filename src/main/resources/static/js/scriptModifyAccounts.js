document.addEventListener('DOMContentLoaded', function() {
    function showModifyModal(button) {
        var userId = button.getAttribute('data-user-id');
        var username = button.getAttribute('data-username');
        document.getElementById('modifyUserId').value = userId;
        document.getElementById('username').value = username; // Set the username field correctly
        document.getElementById('modifyModal').style.display = 'block';
        toggleNewField(); // Call to set the initial state of the new field
    }

    function showDeleteModal(button) {
        var userId = button.getAttribute('data-user-id');
        document.getElementById('deleteUserId').value = userId;
        document.getElementById('deleteModal').style.display = 'block';
    }

    function closeModal(modalId) {
        document.getElementById(modalId).style.display = 'none';
    }

    function toggleNewField() {
        const action = document.getElementById('action').value;
        const newValueDiv = document.getElementById('newValueDiv');
        const newValueInput = document.getElementById('newValue');
        if (action === 'username') {
            newValueDiv.style.display = 'block';
            newValueInput.type = 'text';
            newValueInput.placeholder = "New Username";
        } else if (action === 'password') {
            newValueDiv.style.display = 'block';
            newValueInput.type = 'password';
            newValueInput.placeholder = "New Password";
        }
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
        xhr.setRequestHeader('X-CSRF-TOKEN', document.querySelector('input[name="_csrf"]').value);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    var response = JSON.parse(xhr.responseText);
                    alert(response.message ? response.message : 'User modified successfully.');
                    if (response.success) {
                        closeModal('modifyModal');
                        location.reload(); // Reload the page to reflect changes
                    }
                } else {
                    alert('Error modifying user.');
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
        xhr.open("POST", "/delete-user/" + userId, true); // Change DELETE to POST
        xhr.setRequestHeader('X-CSRF-TOKEN', document.querySelector('input[name="_csrf"]').value);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    var response = JSON.parse(xhr.responseText);
                    alert(response.message ? response.message : 'User deleted successfully.');
                    if (response.success) {
                        closeModal('deleteModal');
                        location.reload(); // Reload the page to reflect changes
                    }
                } else {
                    alert('Error deleting user.');
                }
            }
        };
        xhr.send();
    }

    window.showModifyModal = showModifyModal;
    window.showDeleteModal = showDeleteModal;
    window.closeModal = closeModal;
    window.modifyUser = modifyUser;
    window.deleteUser = deleteUser;
    window.toggleNewField = toggleNewField;
});

const renderUsers = (users) => {
    output = '',
        users.forEach(user => {
            output += ` 
              <tr> 
                    <td>${user.id}</td> 
                    <td>${user.name}</td> 
                    <td>${user.surname}</td> 
                    <td>${user.age}</td> 
                    <td>${user.roles.map(role => role.name === 'ROLE_USER' ? 'USER' : 'ADMIN')}</td> 
              <td> 
                   <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info" 
                    data-toggle="modal" data-target="modal" id="edit-user" data-id="${user.id}">Edit</button> 
               </td> 
               <td> 
                   <button type="button" class="btn btn-danger" id="delete-user" data-action="delete" 
                   data-id="${user.id}" data-target="modal">Delete</button> 
                    </td> 
              </tr>`
        })
    info.innerHTML = output;
}
let users = [];
const updateUser = (user) => {
    const foundIndex = users.findIndex(x => x.id == user.id);
    users[foundIndex] = user;
    renderUsers(users);
    console.log('users');
}
const removeUser = (id) => {
    users = users.filter(user => user.id != id);
    console.log(users)
    renderUsers(users);
}

// Получить всех пользователей

const info = document.querySelector('#allUsers');
const url = 'http://localhost:8080/rest/admin'

fetch(url, {mode: 'cors'})
    .then(res => res.json())
    .then(data => {
        users = data;
        renderUsers(data)
    })

// Добавить пользователя

const addUserForm = document.querySelector('#addUser')
const addName = document.getElementById('name2')
const addSurname = document.getElementById('surname2')
const addAge = document.getElementById('age2')
const addPassword = document.getElementById('password2')
const addRoles = document.getElementById('roles2')

addUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: addName.value,
            lastName: addSurname.value,
            email: addAge.value,
            password: addPassword.value,
            roles: [
                addRoles.value
            ]
        })
    })
        .then(res => res.json())
        .then(data => {
            users = data;
            renderUsers(users);
        })
        .then(res => {
            document.getElementById('very_important_button').click()
        })
})

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

// Редактировать пользователя

on(document, 'click', '#edit-user', e => {
    const userInfo = e.target.parentNode.parentNode
    document.getElementById('id').value = userInfo.children[0].innerHTML
    document.getElementById('name').value = userInfo.children[1].innerHTML
    document.getElementById('surname').value = userInfo.children[2].innerHTML
    document.getElementById('age').value = userInfo.children[3].innerHTML
    document.getElementById('roles').value = userInfo.children[4].innerHTML
    document.getElementById('password').value = userInfo.children[5].innerHTML

    $("#modalEdit").modal("show")
})

const editUserForm = document.querySelector('#modalEdit')
editUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('id').value,
            name: document.getElementById('name').value,
            surname: document.getElementById('surname').value,
            age: document.getElementById('age').value,
            password: document.getElementById('password').value,
            roles: [
                document.getElementById('roles').value
            ]
        })
    })
        .then(res => res.json()).then(data => updateUser(data))
        .catch((e) => console.error(e))

    $("#modalEdit").modal("hide")
})

// Удалить пользователя

let currentUserId = null;
const deleteUserForm = document.querySelector('#modalDelete')
deleteUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    e.stopPropagation();
    fetch('http://localhost:8080/rest/admin/' + currentUserId, {
        method: 'DELETE'
    })
        .then(res => res.json())
        .then(data => {
            removeUser(currentUserId);
            deleteUserForm.removeEventListener('submit', () => {
            });
            $("#modalDelete").modal("hide")
        })
})

on(document, 'click', '#delete-user', e => {
    const fila2 = e.target.parentNode.parentNode
    currentUserId = fila2.children[0].innerHTML

    document.getElementById('id1').value = fila2.children[0].innerHTML
    document.getElementById('name1').value = fila2.children[1].innerHTML
    document.getElementById('surname1').value = fila2.children[2].innerHTML
    document.getElementById('age1').value = fila2.children[3].innerHTML
    document.getElementById('roles1').value = fila2.children[4].innerHTML

    $("#modalDelete").modal("show")
})

//Навигационня панель
const url3 = 'http://localhost:8080/rest/user'
let loggedUserHeaderElem = document.querySelector('#navBarAdmin')

fetch(url3)
    .then(res => res.json())
    .then(data => {
        loggedUserHeaderElem.innerHTML = `<span class="align-middle font-weight-bold mr-1">${data.name}  </span></b> 
                <span class="align-middle mr-1"> with roles:  </span> 
                <span>  ${data.roles.map(role => role.name === 'ROLE_USER' ? 'USER' : 'ADMIN')}</span>`;
    })
$(async function () {
    await getTableWithUsers();
    await getDefaultModal();
    await addNewUser();
})

const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
    },
    findAllUsers: async () => await fetch('api/users'),
    findOneUser: async (id) => await fetch(`api/users/${id}`),
    addNewUser: async (user) => await fetch('api/users', {method: 'POST', headers: userFetchService.head, body: JSON.stringify(user)}),
    updateUser: async (user, id) => await fetch(`api/users/${id}`, {method: 'PUT', headers: userFetchService.head, body: JSON.stringify(user)}),
    deleteUser: async (id) => await fetch(`api/users/${id}`, {method: 'DELETE', headers: userFetchService.head}),
    findOneRole: async (id) => await fetch(`api/roles/${id}`)
}

async function getTableWithUsers() {
    let table = $('#mainTableWithUsers tbody');
    table.empty();
    await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                let roles = ""
                    user.roles.forEach(role => {
                    roles+= role.roleName + " "
                });
                let tableFilling = `$(
                        <tr>   
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.login}</td>
                            <td>${roles}</td>
                
                            <td>
                                <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-primary" 
                                data-toggle="modal" data-target="#modalFormEdit">Edit</button>
                            </td>
                            
                            <td>
                                <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-danger" 
                                data-toggle="modal" data-target="#someDefaultModal">Delete</button>
                            </td>
                        </tr> 
                )`;
                table.append(tableFilling);
            })
        })

    $("#mainTableWithUsers").find('button').on('click', (event) => {
        let defaultModal = $('#modalFormDefault');

        let targetButton = $(event.target);
        let buttonUserId = targetButton.attr('data-userid');
        let buttonAction = targetButton.attr('data-action');

        defaultModal.attr('data-userid', buttonUserId);
        defaultModal.attr('data-action', buttonAction);
        defaultModal.modal('show');
    })
}

async function getDefaultModal() {
    $('#modalFormDefault').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userid = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');
        switch (action) {
            case 'edit':
                editUser(thisModal, userid);
                break;
            case 'delete':
                deleteUser(thisModal, userid);
                break;
        }
    }).on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}

async function editUser(modal, id) {
    let preuser = await userFetchService.findOneUser(id);
    let user = preuser.json();

    modal.find('.modal-title').html('Edit user');

    let editButton = `<button  class="btn btn-success" id="editButton">Edit</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(editButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {
        let bodyForm = `
            <form class="form-group" id="editUser">
            <div class="form-group text-center">
                <label class = "font-weight-bold" for="id">ID:</label>
                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled><br>
            </div>
            <div class="form-group text-center">
                <label class = "font-weight-bold" for="login">Login:</label>
                <input class="form-control" type="text" id="login" value="${user.login}"><br>
            </div>    
            <div class="form-group text-center">
                <label class = "font-weight-bold" for="password">Password:</label>
                <input class="form-control" type="password" id="password" value="Password"><br>
            </div>  
            <div class="form-group text-center">   
                <label class = "font-weight-bold" for="name">Name:</label>  
                <input class="form-control" id="name" type="text" value="${user.name}">
            </div> 
            <select class="custom-select" id="rolesSelectForEdit" multiple="multiple" size="2">
                <option value="1">ROLE_ADMIN</option>
                <option value="2">ROLE_USER</option>
            </select>
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })

    $("#editButton").on('click', async () => {
        let id = modal.find("#id").val().trim();
        let login = modal.find("#login").val().trim();
        let password = modal.find("#password").val().trim();
        let name = modal.find("#name").val().trim();

        let roles = Array.from(document.getElementById("rolesSelectForEdit").options)
            .filter(option => option.selected)
            .map(option => option.value)

        const res = []
        for (let i = 0; i < roles.length; i++) {
            let num = roles[i]
            res.push(await userFetchService.findOneRole(num).then(result => result.json()));
        }

        let data = {
            id: id,
            login: login,
            password: password,
            name: name,
            roles : res
        }
        const response = await userFetchService.updateUser(data, id);

        if (response.ok) {
            getTableWithUsers();
            modal.modal('hide');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="sharaBaraMessageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}

async function deleteUser(modal, id) {
    let preuser1 = await userFetchService.findOneUser(id);
    let user = preuser1.json();

    modal.find('.modal-title').html('Delete user');

    let deleteButton = `<button  class="btn btn-danger" id="deleteButton">Delete</button>`;
    let closeButton1 = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(deleteButton);
    modal.find('.modal-footer').append(closeButton1);

    user.then(user => {
        let bodyForm = `
            <form class="form-group" id="editUser">
            <div class="form-group text-center">
                <label class = "font-weight-bold" for="id">ID:</label>
                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled><br>
            </div>
            <div class="form-group text-center">
                <label class = "font-weight-bold" for="login">Login:</label>
                <input class="form-control" type="text" id="login" value="${user.login}" disabled><br>
            </div>    
            <div class="form-group text-center">
                <label class = "font-weight-bold" for="password">Password:</label>
                <input class="form-control" type="password" id="password" disabled><br>
            </div>  
            <div class="form-group text-center">   
                <label class = "font-weight-bold" for="name">Name:</label>  
                <input class="form-control" id="name" type="text" value="${user.name}" disabled>
            </div> 
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })

    $("#deleteButton").on('click', async () => {
        await userFetchService.deleteUser(id);
        getTableWithUsers();
        modal.modal('hide');
    })
}

async function addNewUser() {
    $('#addNewUserButton').click(async () =>  {
        let addUserForm = $('#newUserForm')
        let login = addUserForm.find('#AddNewUserLogin').val().trim();
        let password = addUserForm.find('#AddNewUserPassword').val().trim();
        let name = addUserForm.find('#AddNewUserName').val().trim();

        let roles = Array.from(document.getElementById("rolesSelect").options)
            .filter(option => option.selected)
            .map(option => option.value)

        const res = []
        for (let i = 0; i < roles.length; i++) {
            let num = roles[i]
            res.push(await userFetchService.findOneRole(num).then(result => result.json()));
        }

        let data = {
            login: login,
            password: password,
            name: name,
            roles : res
        }
        await userFetchService.addNewUser(data);
        console.log(JSON.stringify(data))
        getTableWithUsers();
    })
}
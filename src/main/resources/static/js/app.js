$(async function () {
    await getTableWithUsers();
    await getDefaultModal();
    await addNewUser();
    await principalName();
    await principalInfoTable();
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
    findOneRole: async (id) => await fetch(`api/roles/${id}`),
    findPrincipal: async () => await fetch('api/principal'),
    findAllRoles: async () => await fetch('api/roles')
}

async function tableButtons() {
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

async function principalName() {
    let principalForm = $('#formPrincipal');

    await userFetchService.findPrincipal()
        .then(res => res.json())
        .then(principal => {
            let roles = ""
                principal.roles.forEach(role => {
                    roles+= role.roleName + " "
                })
            let nameAndRoles = `
            <h5 class="my-0 mr-md-auto font-weight-normal text-white">${principal.name} with roles ${roles}</h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <a class="navbar-nav" href="/logout">Logout</a>
            </nav>
         `;
            principalForm.append(nameAndRoles);
        })
}

async function principalInfoTable() {
    let infoTable = $('#tableInfo')
    await userFetchService.findPrincipal()
        .then(res => res.json())
        .then(principal => {
            let roles = ""
            principal.roles.forEach(role => {
                roles+= role.roleName + " "
            })
            let principalInfo = `$(
            <tr class="table-info">
               <td>${principal.id}</td>
                <td>${principal.name}</td>
                <td>${principal.login}</td>
                <td>${roles}</td> 
            </tr>
         )`;
            infoTable.append(principalInfo);
        })
}

async function getTableWithUsers() {
    let table = $('#mainTableWithUsers tbody');
    await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                let roles = ""
                    user.roles.forEach(role => {
                    roles+= role.roleName + " "
                });
                let tableFilling = `$(
                        <tr id="${user.id}">   
                            <td id="${user.id}">${user.id}</td>
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

    await tableButtons();
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
    }) .on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}

async function editUser(modal, id) {
    let preUser = await userFetchService.findOneUser(id);
    let user = preUser.json();

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
                <input class="form-control" type="password" id="password" value="password"><br>
            </div>  
            <div class="form-group text-center">   
                <label class = "font-weight-bold" for="name">Name:</label>  
                <input class="form-control" id="name" type="text" value="${user.name}">
            </div> 
            <select class="custom-select" id="rolesSelectForEdit" multiple="multiple" size="2">
                
            </select>
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })

    let roleArr = await userFetchService.findAllRoles().then(res => res.json());
    let rolesRoles = [];
    roleArr.forEach(role => {
        rolesRoles.push(role.roleName);
    })

    $.each(rolesRoles, function(key, value) {
        $('#rolesSelectForEdit').append('<option value="' + ++key + '">' + value + '</option>');
    });

    $("#editButton").on('click', async () => {
        let id = modal.find("#id").val().trim();
        let login = modal.find("#login").val().trim();
        let password = modal.find("#password").val().trim();
        let name = modal.find("#name").val().trim();

        let roles = Array.from(document.getElementById("rolesSelectForEdit").options)
            .filter(option => option.selected)
            .map(option => option.value)

        let res = [];
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
        await userFetchService.updateUser(data, id);

        await userFetchService.findOneUser(id)
            .then(res=>res.json())
            .then(upUser => {
                let roles = ""
                upUser.roles.forEach(role => {
                    roles+= role.roleName + " "
                });
                let updatedUserRes = `$(
                        <tr id="${upUser.id}">
                            <td id="${upUser.id}">${upUser.id}</td>
                            <td>${upUser.name}</td>
                            <td>${upUser.login}</td>
                            <td>${roles}</td>
                
                            <td>
                                <button type="button" data-userid="${upUser.id}" data-action="edit" class="btn btn-primary" 
                                data-toggle="modal" data-target="#modalFormEdit">Edit</button>
                            </td>
                            
                            <td>
                                <button type="button" data-userid="${upUser.id}" data-action="delete" class="btn btn-danger" 
                                data-toggle="modal" data-target="#someDefaultModal">Delete</button>
                            </td>
                        </tr> 
                )`;
                $(`tr#${upUser.id}`).replaceWith(updatedUserRes);
            });
        modal.modal('hide')
        await tableButtons();
    })

}

async function deleteUser(modal, id) {
    let preUser = await userFetchService.findOneUser(id);
    let user = preUser.json();

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
        document.getElementById(id).remove();
        modal.modal('hide');
    })
}

async function addNewUser() {
    let roleArr = await userFetchService.findAllRoles().then(res => res.json());
    let rolesRoles = [];
    roleArr.forEach(role => {
        rolesRoles.push(role.roleName);
    })

    $.each(rolesRoles, function(key, value) {
        $('#rolesSelect').append('<option value="' + ++key + '">' + value + '</option>');
    });

    $('#addNewUserButton').click(async () =>  {
        let addUserForm = $('#newUserForm')
        let login = addUserForm.find('#AddNewUserLogin').val().trim();
        let password = addUserForm.find('#AddNewUserPassword').val().trim();
        let name = addUserForm.find('#AddNewUserName').val().trim();

        let roles = Array.from(document.getElementById("rolesSelect").options)
            .filter(option => option.selected)
            .map(option => option.value)

        let res = [];
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

        let users = await userFetchService.findAllUsers().then(res => res.json());
        let newUser = [];
        newUser.push(users[users.length - 1]);
        newUser.forEach(user => {
            let table = $('#mainTableWithUsers tbody');
            let userRoles = ""
            user.roles.forEach(role => {
                userRoles+= role.roleName + " "
            });
            let tableFilling = `$(
                        <tr id="${user.id}">   
                            <td id="${user.id}">${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.login}</td>
                            <td>${userRoles}</td>
                
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
            console.log(JSON.stringify(data))
        })
        await tableButtons();
    })
}
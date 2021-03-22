let buttonUserName = document.getElementById('id-button-username')
let userName = document.getElementById('userName')
let repeatUserName = document.getElementById('repeatUserName')
let oldPasswordUserName = document.getElementById('oldPasswordUserName')
let errorNewUserName = document.getElementById('errorNewUserName')
let errorRepeatUserName = document.getElementById('errorRepeatUserName')
let errorPasswordNotFound = document.getElementById('errorPasswordNotFound')
let userNameCredential = document.querySelectorAll('.user_name_credential')

buttonUserName.addEventListener('click', checkUserName)

function checkUserName() {
	if(repeatUserName.value!="" && userName.value!="") {
		if(userName.value!=repeatUserName.value) {
			errorNewUserName.innerText=''
			errorRepeatUserName.innerText='Repita correctamente su nuevo usuario'
			errorPasswordNotFound.innerText=''
		} else {
			errorRepeatUserName.innerText=''
			if(oldPasswordUserName.value!="") {
				errorPasswordNotFound.innerText=''
				changeUserName()
			} else {
				errorNewUserName.innerText=''
				errorRepeatUserName.innerText=''
				errorPasswordNotFound.innerText='El campo no debe estar vacio'
			}
		}
	}
}

function changeUserName() {
	let formData = new FormData()
	formData.append("userName",userName.value)
	formData.append("oldPasswordUserName",oldPasswordUserName.value)
	fetch(`/api/user/updateUserName`, {
		method : 'POST',
		body : formData
	}).then(response => response.json())
	.catch(error => console.error('Error: '+error))
	.then(response => {
		userName.value=''
		repeatUserName.value=''
		oldPasswordUserName.value=''
		errorNewUserName.innerText=''
		errorRepeatUserName.innerText=''
		errorPasswordNotFound.innerText=''
		alert(response.message)
	})
}
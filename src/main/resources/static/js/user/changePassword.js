let newPassword = document.getElementById('newPassword')
let repeatPassword = document.getElementById('repeatPassword')
let oldPassword = document.getElementById('oldPassword')
let errorPassword = document.getElementById('errorPassword');
let errorPasswordNotEqual = document.getElementById('errorPasswordNotEqual')
let verify = document.getElementById('id-button')
let errorNewPassord = document.getElementById('errorNewPassord')

verify.addEventListener('click', check)

function check() {
	if(repeatPassword.value!="" && newPassword.value!="") {
		if(newPassword.value!=repeatPassword.value) {
			errorNewPassord.innerText=''
			errorPassword.innerText='Repita correctamente la nueva contraseña'
			errorPasswordNotEqual.innerText=''
		} else {
			errorPassword.innerText=''
			if(oldPassword.value!="") {
				errorPasswordNotEqual.innerText=''
				changePassword()
			} else {
				errorNewPassord.innerText=''
				errorPassword.innerText=''
				errorPasswordNotEqual.innerText='El campo no debe estar vacio'
			}
		}
	}
	
}

function changePassword() {
	let form = new FormData()
	form.append("newPassword",newPassword.value)
	form.append("oldPassword", oldPassword.value)
	
	fetch(`/api/user/updatePassword`, {
		method : 'POST',
		body : form,
	}).then(response => response.json())
	.catch(error => console.error('Error: '+error))
	.then(response => {
		newPassword.value=''
		oldPassword.value=''
		repeatPassword.value=''
		errorNewPassord.innerText=''
		errorPassword.innerText=''
		errorPasswordNotEqual.innerText=''
		alert(response.message)
	})
}
let fileInput = document.getElementById('file-input');
let upFIle = document.getElementById('upFile')
let formAdjunto = document.getElementById('form-adjunto')
let nameFile = null

const url = formAdjunto.attributes[1].nodeValue.replace('/','');

fileInput.addEventListener('change', function(event) {
	nameFile = event.target.files[0];
});

upFile.addEventListener('click', function() {
	if(fileInput.value=="") {
		alert("No ha seleccionado ningún archivo")
	} else {
		const endpoint = `/api/${url}`;
		let formData = new FormData();
		formData.append("file", nameFile);
		fetch(endpoint, {
			method: "POST",
			body: formData,
		})
		.then((response) => response.json())
		.catch((error) => console.error("Error:", error))
		.then(response => {
			if(response!=null) {
				alert("Archivo subido correctamente");
				fileInput.value=""		
			}
		})
	}		
});
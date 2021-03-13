let dateJustify = document.getElementById("info-date");

let options = {
	weekday: "long",
	day: "numeric",
	month: "long",
	year: "numeric"
}

dateJustify.innerText = new Intl.DateTimeFormat('es-ES', options).format(new Date());
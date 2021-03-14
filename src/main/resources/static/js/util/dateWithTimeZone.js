let dateTimeZone = document.getElementById("info-date");

let options = {
	weekday: "long",
	day: "numeric",
	month: "long",
	year: "numeric"
}

dateTimeZone.innerText = new Intl.DateTimeFormat('es-ES', options).format(new Date(dateTimeZone.textContent));
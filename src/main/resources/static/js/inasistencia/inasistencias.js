import templateCard from "./template-card.js";
import templateJustified from "./templateJustified.js";

const absences = document.querySelector('#absences');
const absencesJustified = document.querySelector('#absencesJustified');
var stompClient = null;

const getInasistencias = async function() {
	const response = await fetch('/api/inasistencias/unjustified')
	return response.json()
}

const getInasistenciasJustificada = async function() {
	const response = await fetch('/api/inasistencias/justified')
	return response.json()
}

window.addEventListener('load', async function() {
	const inasistencias = await getInasistencias()
	absences.innerHtml = ''
	inasistencias.map(inasistencia => {
		absences.insertAdjacentHTML('beforeend', templateCard(inasistencia))
	});
	
	const inasistenciasJustified = await getInasistenciasJustificada()
	absencesJustified.innerHTML = ''
	inasistenciasJustified.map(inasistenciaJustified => {
		absencesJustified.insertAdjacentHTML('beforeend', templateJustified(inasistenciaJustified))
	});
	
	connect()
	
	
	
})

async function getLastInasistencia() {
	const response = await fetch('/api/inasistencias/last')
	return response.json()
}

async function getLastInasistenciaJustified() {
	const response = await fetch('/api/inasistenciasJustified/last')
	return response.json()
}

function connect() {
	var socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		stompClient.subscribe('/broker/absence', async function(data) {
			var dataInasistencia = JSON.parse(data.body);
			if (dataInasistencia.length > 0) {
				const lastInasistencia = await getLastInasistencia()
				console.log(lastInasistencia)
				if (lastInasistencia != undefined && lastInasistencia != null && lastInasistencia != '') {
					absences.insertAdjacentHTML('afterbegin', templateCard(lastInasistencia))
				}
			}
		});
		
		stompClient.subscribe('/broker/absenceJustified', async function(data) {
			console.log(data);
			var dataInasistenciaJustified = JSON.parse(data.body);
			if (dataInasistenciaJustified.length > 0) {
				const lastInasistenciaJustified = await getLastInasistenciaJustified()
				console.log(lastInasistenciaJustified)
				if (lastInasistenciaJustified != undefined && lastInasistenciaJustified != null && lastInasistenciaJustified != '') {
					absencesJustified.insertAdjacentHTML('afterbegin', templateJustified(lastInasistenciaJustified))
				}
			}
		});
		
	});
}

function connectJustified() {
	let socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		stompClient.subscribe('/broker/absenceJustified', async function(data) {
			console.log(data);
			var dataInasistenciaJustified = JSON.parse(data.body);
			if (dataInasistenciaJustified.length > 0) {
				const lastInasistenciaJustified = await getLastInasistenciaJustified()
				console.log(lastInasistenciaJustified)
				if (lastInasistenciaJustified != undefined && lastInasistenciaJustified != null && lastInasistenciaJustified != '') {
					absencesJustified.insertAdjacentHTML('afterbegin', templateJustified(lastInasistenciaJustified))
				}
			}
		});

	});
}
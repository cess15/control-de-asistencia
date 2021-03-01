import templateCard from "./template-card.js";

const absences = document.querySelector('#absences');
var stompClient = null;

const getInasistencias = async function () {
	const response = await fetch('/api/inasistencias/unjustified')
	return response.json()
}

window.addEventListener ('load', async function () {
	const inasistencias = await getInasistencias()
	absences.innerHtml = ''
	inasistencias.map(inasistencia => {
		absences.insertAdjacentHTML('beforeend', templateCard(inasistencia))
	})
	
	connect()
})

async function getLastInasistencia () {
	const response = await fetch('/api/inasistencias/last')
    return response.json()
}

function connect() {
   var socket = new SockJS('/ws');
   stompClient = Stomp.over(socket);
   stompClient.connect({}, function (frame) {
      stompClient.subscribe('/broker/absence', async function (data) {
      	 var dataInasistencia = JSON.parse(data.body);
      	 if (dataInasistencia.length > 0) {
      	 	const lastInasistencia = await getLastInasistencia()
      	 	console.log(lastInasistencia)
      	 	if (lastInasistencia != undefined && lastInasistencia != null && lastInasistencia != '') {
	      	 	absences.insertAdjacentHTML('afterbegin', templateCard(lastInasistencia))
      	 	}
      	 }
      });
   });
}
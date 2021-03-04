export default function templateCard(inasistencia) {
	let elem = `<div class="card">
	  <div class="card-header">
	    Faltó el <a href="#!">${inasistencia.fecha}</a>
	  </div>
	  <div class="card-body">
	    <h5 class="card-title">Por justificar</h5>
	    <p class="card-text">Recuerde que mientras no justifique sus faltas se le seguirá emitiendo avisos a su correo electrónico personal.</p>
	    <br>
	    <a href="/justificar-inasistencia/${inasistencia.id}" id="generate-justify-${inasistencia.id}" class="btn btn-primary">Generar Justificativo</a>
	  </div>
	</div>`
	
	return elem
}

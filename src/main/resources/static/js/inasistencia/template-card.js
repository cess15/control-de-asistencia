export default function templateCard(inasistencia) {
	let elem = `<div class="card">
	  <div class="card-header bg-dark text-light">
	    Faltó el <span>${inasistencia.fecha}</span>
	  </div>
	  <div class="card-body text-light bg-danger">
	    <h5 class="card-title text-light">Por justificar</h5>
	    <p class="card-text">Recuerde que mientras no justifique sus faltas se le seguirá emitiendo avisos a su correo electrónico personal.</p>
	    <br>
	    <a href="/justificar-inasistencia/${inasistencia.id}" id="generate-justify-${inasistencia.id}" class="btn btn-primary">Justificar Inasistencia</a>
	  </div>
	</div>`
	
	return elem
}

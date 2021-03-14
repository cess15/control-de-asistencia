export default function templateJustified(inasistencia) {
	let elem = `<div class="card">
	  <div class="card-header bg-dark text-light">
	    Faltó el <span>${inasistencia.fecha}</span>
	  </div>
	  <div class="card-body bg-warning">
	    <h5 class="card-title">Por entregar</h5>
	    <p class="card-text">Recuerde que mientras no entregue este archivo en físico se le seguirá emitiendo avisos a su correo electrónico personal.</p>
	    <br>
	    <a href="/generar-inasistencia/${inasistencia.id}" id="generate-justified-${inasistencia.id}" class="btn btn-primary">Generar justificación</a>
	  </div>
	</div>`
	
	return elem
}

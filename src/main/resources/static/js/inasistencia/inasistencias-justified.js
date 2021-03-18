$(document).ready(function() {
	$('#inasistencias-justified-table').DataTable({
		"processing": true,
    	"serverSide": true,
    	"ordering": false,
		"ajax": {
			"url": "/api/inasistenciasJustified"
		},
		"columns": [
			{ data : "profesor.cedula" },
			{ data : "profesor.nombres" },
			{ data : "profesor.apellidos" },
			{ 
				data : "profesor.correo",
				render: function (data, type, row) {
					return `<span class="block-email">${data}</span>`;
				}
			},
			{ data : "fecha" },
			{
		        data: "id",
		        render:function (data, type, row) {
		          return `
		          	<a href="#" class="item" data-toggle="modal" data-target="#staticModal_${data}">
						<i class="fa fa-check"></i>
		          	</a>
		          	<!-- MODAL-->
					<div class="modal fade" id="staticModal_${data}" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel" aria-hidden="true" data-backdrop="static">
						<div class="modal-dialog modal-sm" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="staticModalLabel">Justificar Inasistencia</h5>
									<button type="button" class="close"
										data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<p>¿Esta seguro que desea justificar la inasistencia?</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">Cancelar
									</button>
									<a href="/inasistencia/${data}/justificar">
										<button type="button" class="btn btn-primary">Confirmar</button>
									</a>
								</div>
							</div>
						</div>
					</div>
					<!-- END MODAL-->
		          	`;
	        	},
      		},
		],
		"order": [[ 0, 'desc' ]],
		"language": {
	      "sLengthMenu":     "Mostrar _MENU_ registros",
	      "sZeroRecords":    "No se encontraron resultados",
	      "sEmptyTable":     "No se han encontrado datos.",
	      "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
	      "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
	      "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
	      "sInfoPostFix":    "",
	      "sSearch":         "Buscar por fecha:",
	      "sUrl":            "",
	      "sInfoThousands":  ",",
	      "sLoadingRecords": "Cargando...",
	      "oPaginate": {
	        "sFirst":    "Primero",
	        "sLast":     "Último",
	        "sNext":     "Siguiente",
	        "sPrevious": "Anterior"
      	}
    }
	});

});
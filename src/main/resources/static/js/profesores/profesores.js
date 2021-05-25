$(document).ready(function() {
	$('#table-profesores').DataTable({
		"processing": true,
		"serverSide": true,
		"ordering": false,
		"ajax": {
			"url": "/api/data-profesores"
		},
		"columns": [
			{ data : "usuario.nombreUsuario" },
			{ data : "cedula" },
			{ data : "apellidos" },
			{ data : "nombres" },
			{ 
				data : "correo",
				render: function (data, type, row) {
					return `<span class="block-email">${data}</span>`;
				}
			},
			{ data : "telefono" },
			{
				data : "usuario.fechaCreacion",
				render:function(data, type, row){
					return new Date(data).toLocaleDateString();
				}
			},
			{ 
				data : "id", 
				render:function (data, type, row) {
					return `
						<a href="/editar-profesor/${data}">
							<button class="item mr-1 btn btn-warning" 
								data-toggle="tooltip"
								data-placement="top" title="Editar">
								<i class="fas fa-pencil-alt"></i>
							</button>
						</a>
					`;
				}
			},
			{ 
				data : "id", 
				render:function (data, type, row) {
					return `
						<a href="#" class="item" data-toggle="modal" data-target="#staticModal_${data}">
							<button class="item mr-1 btn btn-danger" 
								data-toggle="tooltip"
								data-placement="top" title="Eliminar">
								<i class="fas fa-trash-alt"></i>
							</button>
						</a>
						<!-- MODAL -->
						<div class="modal fade" id="staticModal_${data}" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel" aria-hidden="true" data-backdrop="static">
							<div class="modal-dialog modal-sm" role="document">
								<div class="modal-content">
									<div class="modal-header">
									<h5 class="modal-title" id="staticModalLabel">Eliminar Docente</h5>
										<button type="button" class="close"
											data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<p>¿Esta seguro que desea eliminar?</p>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
										<a href="/eliminar-profesor/${data}">
											<button type="button" class="btn btn-primary">Confirmar</button>
										</a>
									</div>
								</div>
							</div>
						</div>
						<!-- END MODAL -->
					`;
				}
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
		      "sSearch":         "Buscar por cédula:",
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
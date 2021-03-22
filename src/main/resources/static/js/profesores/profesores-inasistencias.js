$(document).ready(function() {
	$('#profesor-permiso-table').DataTable({
		"processing": true,
		"serverSide": true,
		"ordering": false,
		"ajax": {
			"url": "/api/data-profesores"
		},
		"columns": [
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
				data : "id", 
				render:function (data, type, row) {
					return `
						<a href="/ver-permisos/${data}">
							<button class="item mr-1 btn btn-primary" 
								data-toggle="tooltip"
								data-placement="top" title="Ver">
								<i class="fa fa-eye"></i>
							</button>
						</a>
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
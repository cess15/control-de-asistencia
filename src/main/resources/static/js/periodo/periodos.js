$(document).ready(function() {
  $('#periodos-table').DataTable({
    processing: true,
    serverSide: true,
    ordering: false,
    "ajax": {
		"url": "/api/periodos"
	},
    columns: [
      { data : "id" },
      { data : "fechaInicio"},
      { data : "fechaFinal" },
      { 
      	data : "vigente",
      	render:function (data, type, row) {
          return data ? '<span class="badge badge-success">Vigente</span>' : '<span class="badge badge-danger">No vigente</span>';
        },
      },
      {
        data: "id",
        title: "Editar",
        render:function (data, type, row) {
          return `<a href="/periodos/${data}/editar" class="btn btn-info"><i class="fa fa-pencil-alt"></i></a>`;
        },
      },
      {
        data: "id",
        title: "Borrar",
        render:function (data, type, row) {
          return `<a href="/periodos/${data}/borrar" onclick="event.preventDefault(); (confirm('¿ ESTAS SEGURO QUE DESEAS ELIMINAR ESTE PERIODO ?')) ? document.getElementById('delete-form-${data}').submit() : false;" class="btn btn-danger"><i class="fa fa-trash"></i></a>
          <form id="delete-form-${data}" action="/periodos/${data}/borrar" method="POST" style="display: none;"></form>`;
        },
      }
   ],
   order: [[ 0, 'desc' ]],
    "language": {
      "sLengthMenu":     "Mostrar _MENU_ registros",
      "sZeroRecords":    "No se encontraron resultados",
      "sEmptyTable":     "No se han encontrado datos.",
      "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
      "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
      "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
      "sInfoPostFix":    "",
      "sSearch":         "Buscar por año:",
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
  })
})
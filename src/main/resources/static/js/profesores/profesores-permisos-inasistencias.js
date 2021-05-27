$(document).ready(function() {
	let profesorId = document.getElementById('profesorId').value;
	let table = $('#profesor-permiso-inasistencia-table').DataTable({
		"processing": true,
		"serverSide": true,
		"ordering": false,
		"ajax": {
			"url": `/api/ver-permisos/${profesorId}`
		},
		"searching":false,
		"order": [[ 0, 'desc' ]],
		"columns": [
			{ data : "fechaGeneracion" },
			{ data : "fechaRecepcion" },
			{ data : "fechaInicio" },
			{ data : "fechaFinal" },
			{ data : "horaInicio" },
			{ data : "horaFinal" },
			// {
			// 	data : "valorDescontar",
			// 	render : function(data, type, row) {
			// 		return `<i class="fa fa-dollar-sign"></i><span class="money-descontar">${data}</span>`;
			// 	}
			// },
			{	data : "dayDiference" }
		],
		footerCallback: function(row, data, start, end, display)
        {
        	// let totalDescontar = this.api().column(6).data().reduce(function(start, end){
            //    return parseFloat(start)+parseFloat(end);
            // }, 0)
            
            let totalDayDiference = this.api().column(6).data().reduce(function(start, end){
               return parseInt(start)+parseInt(end);
            }, 0)
            
            // $(this.api().column(6).footer()).html('Total: $'+totalDescontar);
            $(this.api().column(6).footer()).html('Total: '+totalDayDiference);
        },
		"language": {
			"sLengthMenu":     "Mostrar _MENU_ registros",
	      	"sZeroRecords":    "No se encontraron resultados",
		      "sEmptyTable":     "No se han encontrado datos.",
		      "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
		      "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
		      "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
		      "sInfoPostFix":    "",
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


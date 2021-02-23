export default function templateProfesor(profesor, inasistenciasHoy) {
  let assist = ''

  if (profesor.inasistencias.length > 0) {
  	assist = '<span class="badge badge-danger">Faltó</span>'
  } else {
  	if (inasistenciasHoy.length > 0) {
  	  assist = '<span class="badge badge-info">Asistió</span>'
  	} else {
	  	assist = `<div class="custom-control custom-checkbox">
		    <input hidden type="text" value="${profesor.id}">
		    <input
		      type="checkbox" id="user_${profesor.id}"
		      class="custom-control-input check-mark"
		    />
		    <label for="user_${profesor.id}"
		      class="custom-control-label"
		      >Listar
		      </label
		    >
		  </div>`
   	}
  }
  
  return `
    <tr class="tr-shadow">
      <td>
        ${assist}
      </td>
      <td>${profesor.cedula}</td>
      <td>${profesor.nombres}</td>
      <td>${profesor.apellidos}</td>
      <td class="desc" >${
        profesor.telefono != null
          ? profesor.telefono
          : ""
      }</td>
      <td><span class="block-email">${
        profesor.correo
      }</span></td>
      <td>${profesor.inasistencias.length}</td>
    </tr>
    `;
}

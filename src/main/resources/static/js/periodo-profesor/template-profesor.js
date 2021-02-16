export default function templateProfesor(profesorPeriodo) {
  return `
    <tr class="tr-shadow">
        <td>
          <div class="custom-control custom-checkbox">
            <input hidden type="text" value="${profesorPeriodo.id}">
            <input
              type="checkbox" id="user_${profesorPeriodo.id}"
              class="custom-control-input check-mark"
            />
            <label for="user_${profesorPeriodo.id}"
              class="custom-control-label"
              >Listar
              </label
            >
          </div>
        </td>
        <td>${profesorPeriodo.profesor.cedula}</td>
        <td>${profesorPeriodo.profesor.nombres}</td>
        <td>${profesorPeriodo.profesor.apellidos}</td>
        <td class="desc" >${
          profesorPeriodo.profesor.telefono != null
            ? profesorPeriodo.profesor.telefono
            : ""
        }</td>
        <td><span class="block-email">${
          profesorPeriodo.profesor.correo
        }</span></td>
        <td>${profesorPeriodo.diasFaltados}</td>
    </tr>
    `;
}

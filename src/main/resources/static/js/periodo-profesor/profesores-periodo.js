import templateProfesor from "./template-profesor.js";

var profesorPeriodoVigente = $("#profesoresPeriodoVigente");
let checkGeneral = document.getElementById("checkGeneral");
let check = Object;
let arrayCheck = [];
let tomarAsistencia = document.getElementById("tomarAsistencia");

async function obtenerProfesoresPorPeriodoVigente() {
  const data = await fetch("/periodo-profesores");
  return data.json();
}

window.addEventListener(
  "load",
  async function () {
    let response = await obtenerProfesoresPorPeriodoVigente();
    response.map((profesor) => {
      profesorPeriodoVigente.append(templateProfesor(profesor));
    });
    check = document.querySelectorAll(".check-mark");
    checkGeneral.addEventListener("change", checkAll);
    tomarAsistencia.addEventListener("click", enviarAsistencia);
  },
  false
);

const enviarAsistencia = () => {
  arrayCheck=[];
  [...check].map((c) => {
    if (c.checked == true) {
      arrayCheck.push(c.previousElementSibling.value);
    }
  });
  console.log(arrayCheck);
};

const checkAll = (event) => {
  for (let index = 0; index < check.length; index++) {
    let element = check[index];
    if (checkGeneral.checked == true) {
      element.checked = true;
    } else {
      element.checked = false;
    }
  }
};

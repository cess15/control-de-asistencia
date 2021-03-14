let typeMotive = document.getElementById('typeMotive');

let formPermiso = document.getElementById('formPermiso');

let url = formPermiso.action;

let infoColumnPermiso = document.querySelector('.info-column-permiso');
let infoColumnLicencia = document.querySelector('.info-column-licencia');
let infoColumnOtros = document.querySelector('.info-column-otros');
let typeDescriptionMotive = document.getElementById('typeDescriptionMotive');
let descriptionMotive = document.getElementById('descriptionMotive');

let radioPermisos = document.querySelectorAll('[name=permisos]');
let radioLicencias = document.querySelectorAll('[name=licencias]');
let radioOtros = document.querySelectorAll('[name=otros]');

infoColumnPermiso.style="display:none";
infoColumnLicencia.style="display:none";
infoColumnOtros.style="display:none";

typeMotive.addEventListener('change', function(){
	[...typeMotive].forEach(element=>{
		if(element.selected==true){
			if(element.value=='PERMISO'){
				unSelectLicencia();
				unSelectOtros();
				infoColumnPermiso.style="";
				infoColumnLicencia.style="display:none";
				infoColumnOtros.style="display:none";
				showTextByPermisos()
				
			} else if(element.value=='LICENCIA') {
				unSelectPermiso();
				unSelectOtros();
				infoColumnLicencia.style="";
				infoColumnOtros.style="display:none";
				infoColumnPermiso.style="display:none";
				showTextByLicencias();
			}
			else if(element.value=='OTROS'){
				unSelectLicencia();
				unSelectPermiso();
				infoColumnOtros.style="";
				infoColumnPermiso.style="display:none";
				infoColumnLicencia.style="display:none";
				showTextByOtros();
			}	
		}
	})
});

async function getMotiveById(id){
	const data = await fetch(`/api/motivo/${id}`)
		.then(response=>response.json())
		.catch(error=>console.log('Error'+error))
		.then(response=>{
			typeDescriptionMotive.innerHTML = response.nombre;
			descriptionMotive.innerHTML = response.descripcion;
			formPermiso.action=`${url}/motivo/${id}`;
		})
}

function showTextByLicencias() {
	[...radioLicencias].forEach(element=>{
		element.addEventListener('click', function(){
			if(element.checked==true){
				getMotiveById(parseInt(element.id));
			}
		})
	})
}

function showTextByPermisos() {
	[...radioPermisos].forEach(element=>{
		element.addEventListener('click', function(){
			if(element.checked==true){
				getMotiveById(parseInt(element.id));
			}
		})
	})
}

function showTextByOtros() {
	[...radioOtros].forEach(element=>{
		element.addEventListener('click', function(){
			if(element.checked==true){
				getMotiveById(parseInt(element.id));
			}
		})
	})
}

function unSelectPermiso() {
    radioPermisos.forEach((x) => {
    	x.checked = false
		typeDescriptionMotive.innerHTML="";
		descriptionMotive.innerHTML="";
	});
}

function unSelectLicencia() {
    radioLicencias.forEach((x) => {
    	x.checked = false
    	typeDescriptionMotive.innerHTML="";
		descriptionMotive.innerHTML="";
	});
}

function unSelectOtros() {
    radioOtros.forEach((x) => {
    	x.checked = false
    	typeDescriptionMotive.innerHTML="";
		descriptionMotive.innerHTML="";	
	});
}
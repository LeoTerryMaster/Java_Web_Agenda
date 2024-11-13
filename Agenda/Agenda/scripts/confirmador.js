/**
 * Confirmar a exclusão de um contatos
 * @TerryMaster
 * @param  idcon
 */

function confirmar(idcon) {
	let resposta = confirm("Confirmar Exclusão")
	if (resposta === true) {
		window.location.href = "delete?idcon=" + idcon
	}
}
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<title>Editar contatos</title>
	<link rel="icon" href="img/fone.png" > 
	<link rel="stylesheet" href="css/estilos.css" media="all" >
</head>
<body>
	<h1>Editar Contatos</h1>

		<form name="frmContato" action="update">
		<table > 
			<tr>		
				<td><input type="text" name="idcon" id="caixa3" readonly value="<%out.print(request.getAttribute("idcon")); %>"></td>
			</tr>
			<tr>		
				<td><input type="text" name="nome" class="caixa1" value="<%out.print(request.getAttribute("nome")); %>"></td>
			</tr>
			<tr>		
				<td><input type="text" name="fone"class="caixa2"value="<%out.print(request.getAttribute("fone")); %>"></td>
			</tr>
			<tr>		
				<td><input type="text" name="email"  class="caixa1" value="<%out.print(request.getAttribute("email")); %>"></td>
			</tr>			
		</table>
			<input type="button" value="Salvar" class="botao1" onclick="validar()">
		</form>
		
<script src="scripts/validador.js"></script>
</body>
</html>
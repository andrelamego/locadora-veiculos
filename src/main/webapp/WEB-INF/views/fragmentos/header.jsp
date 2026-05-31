<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Locadora de Veículos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="navbar">
    <a href="${pageContext.request.contextPath}/" class="brand">🚗 Locadora <span>Aeroporto</span></a>
    <nav>
        <a href="${pageContext.request.contextPath}/categorias">Categorias</a>
        <a href="${pageContext.request.contextPath}/veiculos">Veículos</a>
        <a href="${pageContext.request.contextPath}/veiculos/disponiveis">Disponíveis</a>
        <a href="${pageContext.request.contextPath}/locatarios">Locatários</a>
        <a href="${pageContext.request.contextPath}/locacoes">Locações</a>
        <a href="${pageContext.request.contextPath}/devolucoes">Devoluções</a>
        <a href="${pageContext.request.contextPath}/reparos">Reparos</a>
        <a href="${pageContext.request.contextPath}/relatorios">Relatórios</a>
    </nav>
</header>

<div class="container">

    <c:if test="${not empty sucesso}">
        <div class="alert alert-success">${sucesso}</div>
    </c:if>
    <c:if test="${not empty erro}">
        <div class="alert alert-error">${erro}</div>
    </c:if>

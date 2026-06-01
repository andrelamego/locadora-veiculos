<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Locadora de Veiculos</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="navbar">
    <a href="${pageContext.request.contextPath}/" class="brand">
        <i class="bi bi-car-front" aria-hidden="true"></i> Locadora <span>Aeroporto</span>
    </a>
    <nav>
        <a class="nav-primary" href="${pageContext.request.contextPath}/locacoes/nova">
            <i class="bi bi-key" aria-hidden="true"></i> Nova locacao
        </a>

        <div class="nav-dropdown">
            <button type="button" class="nav-dropdown-toggle">
                <i class="bi bi-car-front" aria-hidden="true"></i> Frota <i class="bi bi-chevron-down" aria-hidden="true"></i>
            </button>
            <div class="nav-dropdown-menu">
                <a href="${pageContext.request.contextPath}/veiculos">Veiculos</a>
                <a href="${pageContext.request.contextPath}/veiculos/disponiveis">Disponiveis por categoria</a>
                <a href="${pageContext.request.contextPath}/categorias">Categorias</a>
                <a href="${pageContext.request.contextPath}/reparos">Reparos</a>
            </div>
        </div>

        <div class="nav-dropdown">
            <button type="button" class="nav-dropdown-toggle">
                <i class="bi bi-clipboard-check" aria-hidden="true"></i> Operacao <i class="bi bi-chevron-down" aria-hidden="true"></i>
            </button>
            <div class="nav-dropdown-menu">
                <a href="${pageContext.request.contextPath}/locacoes">Locacoes</a>
                <a href="${pageContext.request.contextPath}/devolucoes">Devolucoes</a>
                <a href="${pageContext.request.contextPath}/locatarios">Locatarios</a>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/relatorios">
            <i class="bi bi-bar-chart" aria-hidden="true"></i> Relatorios
        </a>
    </nav>
</header>

<div class="container">

    <c:if test="${not empty sucesso}">
        <div class="alert alert-success">${sucesso}</div>
    </c:if>
    <c:if test="${not empty erro}">
        <div class="alert alert-error">${erro}</div>
    </c:if>

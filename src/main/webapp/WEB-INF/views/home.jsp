<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="fragmentos/header.jsp" %>

<div class="page-title"><i class="bi bi-house-door" aria-hidden="true"></i>Painel Inicial</div>

<div class="dashboard-grid">
    <div class="card dashboard-stat">
        <div class="label">Veículos</div>
        <div class="value">${totalVeiculos}</div>
    </div>
    <div class="card dashboard-stat">
        <div class="label">Disponíveis</div>
        <div class="value">${veiculosDisponiveis}</div>
    </div>
    <div class="card dashboard-stat">
        <div class="label">Locatários</div>
        <div class="value">${totalLocatarios}</div>
    </div>
    <div class="card dashboard-stat">
        <div class="label">Locações Ativas</div>
        <div class="value">${locacoesAtivas}</div>
    </div>
</div>

<div class="card">
    <div class="card-header">Atalhos</div>
    <div class="card-body">
        <div class="shortcut-grid">
            <a class="shortcut-link" href="${pageContext.request.contextPath}/veiculos">
                <i class="bi bi-car-front" aria-hidden="true"></i>
                <span><strong>Veículos</strong>Cadastro, status e categorias da frota.</span>
            </a>
            <a class="shortcut-link" href="${pageContext.request.contextPath}/veiculos/disponiveis">
                <i class="bi bi-check-circle" aria-hidden="true"></i>
                <span><strong>Disponíveis</strong>Consulta de veículos livres por categoria.</span>
            </a>
            <a class="shortcut-link" href="${pageContext.request.contextPath}/locacoes/nova">
                <i class="bi bi-plus-circle" aria-hidden="true"></i>
                <span><strong>Nova Locação</strong>Registro rápido de uma nova retirada.</span>
            </a>
            <a class="shortcut-link" href="${pageContext.request.contextPath}/locatarios">
                <i class="bi bi-person" aria-hidden="true"></i>
                <span><strong>Locatários</strong>Clientes, dados de habilitação e histórico.</span>
            </a>
            <a class="shortcut-link" href="${pageContext.request.contextPath}/reparos">
                <i class="bi bi-tools" aria-hidden="true"></i>
                <span><strong>Reparos</strong>Acompanhamento de veículos em manutenção.</span>
            </a>
            <a class="shortcut-link" href="${pageContext.request.contextPath}/relatorios">
                <i class="bi bi-bar-chart" aria-hidden="true"></i>
                <span><strong>Relatórios</strong>PDFs operacionais da locadora.</span>
            </a>
        </div>
    </div>
</div>

</div></body></html>

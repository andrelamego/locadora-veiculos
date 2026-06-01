<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="fragmentos/header.jsp" %>

<section class="home-hero">
    <div class="home-hero-content">
        <div class="hero-kicker">Atendimento principal</div>
        <h1>Nova locação de veículo</h1>
        <p>Escolha um carro disponível, consulte o CPF do cliente e confirme a locação em um fluxo único.</p>
        <div class="hero-actions">
            <a class="btn btn-accent btn-lg" href="${pageContext.request.contextPath}/locacoes/nova">
                <i class="bi bi-key" aria-hidden="true"></i> Iniciar locação
            </a>
            <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/veiculos/disponiveis">
                <i class="bi bi-check-circle" aria-hidden="true"></i> Ver disponíveis por categoria
            </a>
        </div>
    </div>
    <div class="home-hero-panel">
        <div class="hero-stat">
            <span>Disponíveis</span>
            <strong>${veiculosDisponiveis}</strong>
        </div>
        <div class="hero-stat">
            <span>Locações ativas</span>
            <strong>${locacoesAtivas}</strong>
        </div>
    </div>
</section>

<div class="dashboard-grid compact-dashboard">
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
        <div class="label">Locações ativas</div>
        <div class="value">${locacoesAtivas}</div>
    </div>
</div>

<div class="home-sections">
    <section class="card">
        <div class="card-header">Fluxos frequentes</div>
        <div class="card-body">
            <div class="shortcut-grid two-columns">
                <a class="shortcut-link shortcut-featured" href="${pageContext.request.contextPath}/locacoes/nova">
                    <i class="bi bi-key" aria-hidden="true"></i>
                    <span><strong>Nova locação</strong>Vitrine de carros disponíveis, busca por CPF e confirmação da retirada.</span>
                </a>
                <a class="shortcut-link" href="${pageContext.request.contextPath}/devolucoes">
                    <i class="bi bi-arrow-return-left" aria-hidden="true"></i>
                    <span><strong>Devoluções</strong>Cálculo de valores e encerramento das locações ativas.</span>
                </a>
                <a class="shortcut-link" href="${pageContext.request.contextPath}/reparos">
                    <i class="bi bi-tools" aria-hidden="true"></i>
                    <span><strong>Reparos</strong>Registro de oficina e controle de veículos indisponíveis.</span>
                </a>
                <a class="shortcut-link" href="${pageContext.request.contextPath}/relatorios">
                    <i class="bi bi-bar-chart" aria-hidden="true"></i>
                    <span><strong>Relatórios</strong>PDFs operacionais para locações, clientes e reparos.</span>
                </a>
            </div>
        </div>
    </section>
</div>

</div></body></html>

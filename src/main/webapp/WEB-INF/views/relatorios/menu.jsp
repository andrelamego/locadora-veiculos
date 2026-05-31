<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title"><i class="bi bi-bar-chart" aria-hidden="true"></i>Relatórios</div>

<div style="display:grid;grid-template-columns:1fr 1fr 1fr;gap:1.5rem">

    <%-- Relatório 1: Veículos alugados no dia --%>
    <div class="card">
        <div class="card-header"><span class="card-title"><i class="bi bi-car-front" aria-hidden="true"></i>Veículos Alugados no Dia</span></div>
        <div class="card-body">
            <p style="color:#6b7280;font-size:0.9rem;margin-bottom:1rem">
                Gera PDF com dados do veículo, locatário e dias fora.
            </p>
            <form method="get" action="${pageContext.request.contextPath}/relatorios/veiculos-alugados-dia">
                <div class="form-group" style="margin-bottom:1rem">
                    <label for="dataAlugados">Data</label>
                    <input type="date" id="dataAlugados" name="data" value="${hoje}" required>
                </div>
                <button type="submit" class="btn btn-primary"><i class="bi bi-file-earmark-pdf" aria-hidden="true"></i>Gerar PDF</button>
            </form>
        </div>
    </div>

    <%-- Relatório 2: Histórico do cliente --%>
    <div class="card">
        <div class="card-header"><span class="card-title"><i class="bi bi-person" aria-hidden="true"></i>Histórico de Cliente</span></div>
        <div class="card-body">
            <p style="color:#6b7280;font-size:0.9rem;margin-bottom:1rem">
                Gera PDF com dados do cliente e todas as suas locações.
            </p>
            <form method="get" action="${pageContext.request.contextPath}/relatorios/historico-cliente">
                <div class="form-group" style="margin-bottom:1rem">
                    <label for="cpfCliente">CPF do Cliente</label>
                    <select id="cpfCliente" name="cpf" required>
                        <option value="">Selecione o cliente</option>
                        <c:forEach var="loc" items="${locatarios}">
                            <option value="${loc.cpf}">${loc.nome} (${loc.cpf})</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary"><i class="bi bi-file-earmark-pdf" aria-hidden="true"></i>Gerar PDF</button>
            </form>
        </div>
    </div>

    <%-- Relatório 3: Veículos em reparo no dia --%>
    <div class="card">
        <div class="card-header"><span class="card-title"><i class="bi bi-tools" aria-hidden="true"></i>Veículos em Reparo no Dia</span></div>
        <div class="card-body">
            <p style="color:#6b7280;font-size:0.9rem;margin-bottom:1rem">
                Gera PDF com veículos em reparo na data informada.
            </p>
            <form method="get" action="${pageContext.request.contextPath}/relatorios/reparos-dia">
                <div class="form-group" style="margin-bottom:1rem">
                    <label for="dataReparos">Data</label>
                    <input type="date" id="dataReparos" name="data" value="${hoje}" required>
                </div>
                <button type="submit" class="btn btn-primary"><i class="bi bi-file-earmark-pdf" aria-hidden="true"></i>Gerar PDF</button>
            </form>
        </div>
    </div>

</div>

</div></body></html>

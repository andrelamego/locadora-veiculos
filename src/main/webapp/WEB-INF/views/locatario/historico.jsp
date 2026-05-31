<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title">📋 Histórico de Locações — ${locatario.nome}</div>

<div class="info-box" style="margin-bottom:1.5rem">
    <h4>Dados do Cliente</h4>
    <dl>
        <dt>CPF</dt>        <dd>${locatario.cpf}</dd>
        <dt>Habilitação</dt><dd>${locatario.numeroHabilitacao}</dd>
        <dt>Nascimento</dt> <dd>${locatario.dataNascimento}</dd>
        <dt>Endereço</dt>   <dd>${locatario.enderecoResumo}</dd>
    </dl>
</div>

<div class="card">
    <div class="card-header">
        Locações Realizadas
        <a href="${pageContext.request.contextPath}/relatorios/historico-cliente/${locatario.cpf}"
           class="btn btn-accent btn-sm">📄 Gerar PDF</a>
    </div>
    <div class="card-body">
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Veículo</th>
                        <th>Retirada</th>
                        <th>Dias</th>
                        <th>Prev. Devolução</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="loc" items="${historico}">
                        <tr>
                            <td>${loc.id}</td>
                            <td>${loc.veiculoPlaca} — ${loc.veiculoModelo}</td>
                            <td>${loc.dataRetirada}</td>
                            <td>${loc.quantidadeDias}</td>
                            <td>${loc.dataPrevistaDevolucao}</td>
                            <td><span class="badge badge-${loc.status.toString().toLowerCase()}">${loc.status}</span></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty historico}">
                        <tr><td colspan="6" style="text-align:center;color:#6b7280;padding:2rem">Nenhuma locação encontrada.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div style="margin-top:1rem">
    <a href="${pageContext.request.contextPath}/locatarios" class="btn btn-outline">← Voltar</a>
</div>

</div></body></html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title"><i class="bi bi-file-earmark-text" aria-hidden="true"></i>Locações</div>

<div class="card">
    <div class="card-header">
        Lista de Locações
        <div class="action-row">
            <a href="${pageContext.request.contextPath}/locacoes/nova" class="btn btn-accent btn-sm"><i class="bi bi-key" aria-hidden="true"></i>Nova Locacao</a>
            <a href="${pageContext.request.contextPath}/relatorios/veiculos-alugados-dia" class="btn btn-primary btn-sm"><i class="bi bi-file-earmark-pdf" aria-hidden="true"></i>Alugados Hoje</a>
        </div>
    </div>
    <div class="card-body">
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Veículo</th>
                        <th>Locatário</th>
                        <th>Retirada</th>
                        <th>Dias</th>
                        <th>Prev. Devolução</th>
                        <th>Status</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="loc" items="${locacoes}">
                        <tr>
                            <td>${loc.id}</td>
                            <td>${loc.veiculoPlaca} — ${loc.veiculoModelo}</td>
                            <td>${loc.locatarioNome}</td>
                            <td>${loc.dataRetirada}</td>
                            <td>${loc.quantidadeDias}</td>
                            <td>${loc.dataPrevistaDevolucao}</td>
                            <td><span class="badge badge-${loc.status.toString().toLowerCase()}">${loc.status}</span></td>
                            <td>
                                <div class="action-row">
                                    <c:if test="${loc.status.toString() == 'ATIVA'}">
                                        <a href="${pageContext.request.contextPath}/devolucoes/nova/${loc.id}" class="btn btn-accent btn-sm">Devolver</a>
                                    </c:if>
                                    <a href="${pageContext.request.contextPath}/locacoes/excluir/${loc.id}"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Excluir esta locação?')">Excluir</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty locacoes}">
                        <tr><td colspan="8" style="text-align:center;color:#6b7280;padding:2rem">Nenhuma locação registrada.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

</div></body></html>

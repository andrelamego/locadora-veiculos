<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../header.jsp" %>

<div class="page-title">🔧 Reparos</div>

<div class="card">
    <div class="card-header">
        Lista de Reparos
        <div class="action-row">
            <a href="${pageContext.request.contextPath}/reparos/novo" class="btn btn-accent btn-sm">+ Novo Reparo</a>
            <a href="${pageContext.request.contextPath}/relatorios/reparos-dia" class="btn btn-primary btn-sm">📄 Reparos Hoje</a>
        </div>
    </div>
    <div class="card-body">
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Veículo</th>
                        <th>Entrada</th>
                        <th>Dias</th>
                        <th>Prev. Saída</th>
                        <th>Problema</th>
                        <th>Valor</th>
                        <th>Status</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="rep" items="${reparos}">
                        <tr>
                            <td>${rep.id}</td>
                            <td>${rep.veiculoPlaca} — ${rep.veiculoModelo}</td>
                            <td>${rep.dataEntrada}</td>
                            <td>${rep.quantidadeDias}</td>
                            <td>${rep.dataPrevistaSaida}</td>
                            <td title="${rep.descricaoProblema}">
                                <c:choose>
                                    <c:when test="${rep.descricaoProblema.length() > 40}">
                                        ${rep.descricaoProblema.substring(0, 40)}...
                                    </c:when>
                                    <c:otherwise>${rep.descricaoProblema}</c:otherwise>
                                </c:choose>
                            </td>
                            <td><fmt:formatNumber value="${rep.valorReparo}" type="currency" currencySymbol="R$"/></td>
                            <td><span class="badge badge-${rep.status.toString().toLowerCase()}">${rep.status}</span></td>
                            <td>
                                <div class="action-row">
                                    <a href="${pageContext.request.contextPath}/reparos/editar/${rep.id}" class="btn btn-outline btn-sm">Editar</a>
                                    <a href="${pageContext.request.contextPath}/reparos/excluir/${rep.id}"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Excluir este reparo?')">Excluir</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty reparos}">
                        <tr><td colspan="9" style="text-align:center;color:#6b7280;padding:2rem">Nenhum reparo registrado.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

</div></body></html>

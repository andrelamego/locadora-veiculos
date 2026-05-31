<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title">🔄 Devoluções</div>

<div class="card">
    <div class="card-header">Lista de Devoluções</div>
    <div class="card-body">
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Locação</th>
                        <th>Veículo</th>
                        <th>Locatário</th>
                        <th>Devolução</th>
                        <th>Litros Falt.</th>
                        <th>Combustível</th>
                        <th>Locação</th>
                        <th>Total</th>
                        <th>Detalhes</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dev" items="${devolucoes}">
                        <tr>
                            <td>${dev.id}</td>
                            <td>#${dev.locacaoId}</td>
                            <td>${dev.veiculoPlaca} — ${dev.veiculoModelo}</td>
                            <td>${dev.locatarioNome}</td>
                            <td>${dev.dataDevolucao}</td>
                            <td>${dev.litrosFaltantes}L</td>
                            <td><fmt:formatNumber value="${dev.valorCombustivel}" type="currency" currencySymbol="R$"/></td>
                            <td><fmt:formatNumber value="${dev.valorLocacao}" type="currency" currencySymbol="R$"/></td>
                            <td><strong><fmt:formatNumber value="${dev.valorTotal}" type="currency" currencySymbol="R$"/></strong></td>
                            <td><a href="${pageContext.request.contextPath}/devolucoes/${dev.id}" class="btn btn-outline btn-sm">Ver</a></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty devolucoes}">
                        <tr><td colspan="10" style="text-align:center;color:#6b7280;padding:2rem">Nenhuma devolução registrada.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

</div></body></html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title"><i class="bi bi-car-front" aria-hidden="true"></i>Veículos</div>

<div class="card">
    <div class="card-header">
        Lista de Veículos
        <a href="${pageContext.request.contextPath}/veiculos/novo" class="btn btn-accent btn-sm">+ Novo Veículo</a>
    </div>
    <div class="card-body">
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr>
                        <th>Placa</th>
                        <th>Marca/Modelo</th>
                        <th>Cor</th>
                        <th>Ano</th>
                        <th>Combustível</th>
                        <th>Câmbio</th>
                        <th>Km</th>
                        <th>Categoria</th>
                        <th>Diária</th>
                        <th>Status</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="v" items="${veiculos}">
                        <tr>
                            <td><strong>${v.placa}</strong></td>
                            <td>${v.marca} ${v.modelo}</td>
                            <td>${v.cor}</td>
                            <td>${v.ano}</td>
                            <td>${v.tipoCombustivel}</td>
                            <td>${v.tipoCambio}</td>
                            <td>${v.quilometragem}</td>
                            <td>${v.categoriaNome}</td>
                            <td><fmt:formatNumber value="${v.valorDiaria}" type="currency" currencySymbol="R$"/></td>
                            <td><span class="badge badge-${v.status.toString().toLowerCase()}">${v.status}</span></td>
                            <td>
                                <div class="action-row">
                                    <a href="${pageContext.request.contextPath}/veiculos/editar/${v.placa}" class="btn btn-outline btn-sm">Editar</a>
                                    <a href="${pageContext.request.contextPath}/veiculos/excluir/${v.placa}"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Excluir este veículo?')">Excluir</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty veiculos}">
                        <tr><td colspan="11" style="text-align:center;color:#6b7280;padding:2rem">Nenhum veículo cadastrado.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

</div></body></html>

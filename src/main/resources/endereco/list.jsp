<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.jsp" %>

<div class="page-title">📍 Endereços</div>

<div class="card">
    <div class="card-header">
        Lista de Endereços
        <a href="${pageContext.request.contextPath}/enderecos/novo" class="btn btn-accent btn-sm">+ Novo Endereço</a>
    </div>
    <div class="card-body">
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Logradouro</th>
                        <th>Número</th>
                        <th>CEP</th>
                        <th>Cidade</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="end" items="${enderecos}">
                        <tr>
                            <td>${end.id}</td>
                            <td>${end.logradouro}</td>
                            <td>${end.numero}</td>
                            <td>${end.cep}</td>
                            <td>${end.cidade}</td>
                            <td>
                                <div class="action-row">
                                    <a href="${pageContext.request.contextPath}/enderecos/editar/${end.id}" class="btn btn-outline btn-sm">Editar</a>
                                    <a href="${pageContext.request.contextPath}/enderecos/excluir/${end.id}"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Excluir este endereço?')">Excluir</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty enderecos}">
                        <tr><td colspan="6" style="text-align:center;color:#6b7280;padding:2rem">Nenhum endereço cadastrado.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

</div></body></html>

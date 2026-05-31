<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title">👤 Locatários</div>

<div class="card">
    <div class="card-header">
        Lista de Locatários
        <a href="${pageContext.request.contextPath}/locatarios/novo" class="btn btn-accent btn-sm">+ Novo Locatário</a>
    </div>
    <div class="card-body">
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr>
                        <th>CPF</th>
                        <th>Nome</th>
                        <th>Habilitação</th>
                        <th>Nascimento</th>
                        <th>Endereço</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="loc" items="${locatarios}">
                        <tr>
                            <td>${loc.cpf}</td>
                            <td>${loc.nome}</td>
                            <td>${loc.numeroHabilitacao}</td>
                            <td>${loc.dataNascimento}</td>
                            <td>${loc.enderecoResumo}</td>
                            <td>
                                <div class="action-row">
                                    <a href="${pageContext.request.contextPath}/locatarios/editar/${loc.cpf}" class="btn btn-outline btn-sm">Editar</a>
                                    <a href="${pageContext.request.contextPath}/locatarios/${loc.cpf}/historico" class="btn btn-primary btn-sm">Histórico</a>
                                    <a href="${pageContext.request.contextPath}/locatarios/excluir/${loc.cpf}"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Excluir este locatário?')">Excluir</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty locatarios}">
                        <tr><td colspan="6" style="text-align:center;color:#6b7280;padding:2rem">Nenhum locatário cadastrado.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

</div></body></html>

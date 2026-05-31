<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title">📂 Categorias</div>

<div class="card">
    <div class="card-header">
        Lista de Categorias
        <a href="${pageContext.request.contextPath}/categorias/nova" class="btn btn-accent btn-sm">+ Nova Categoria</a>
    </div>
    <div class="card-body">
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Nome</th>
                        <th>Descrição</th>
                        <th>Valor Diária</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cat" items="${categorias}">
                        <tr>
                            <td>${cat.id}</td>
                            <td>${cat.nome}</td>
                            <td>${not empty cat.descricao ? cat.descricao : '—'}</td>
                            <td><fmt:formatNumber value="${cat.valorDiaria}" type="currency" currencySymbol="R$"/></td>
                            <td>
                                <div class="action-row">
                                    <a href="${pageContext.request.contextPath}/categorias/editar/${cat.id}" class="btn btn-outline btn-sm">Editar</a>
                                    <a href="${pageContext.request.contextPath}/categorias/excluir/${cat.id}"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Excluir esta categoria?')">Excluir</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty categorias}">
                        <tr><td colspan="5" style="text-align:center;color:#6b7280;padding:2rem">Nenhuma categoria cadastrada.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

</div></body></html>

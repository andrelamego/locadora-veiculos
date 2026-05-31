<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.jsp" %>

<div class="page-title">${empty categoria.id ? '➕ Nova Categoria' : '✏️ Editar Categoria'}</div>

<div class="card">
    <div class="card-header">${empty categoria.id ? 'Cadastrar Categoria' : 'Atualizar Categoria'}</div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/categorias" method="post">
            <c:if test="${not empty categoria.id}">
                <input type="hidden" name="id" value="${categoria.id}">
            </c:if>
            <div class="form-grid">
                <div class="form-group">
                    <label for="nome">Nome *</label>
                    <input type="text" id="nome" name="nome" value="${categoria.nome}" required maxlength="80" placeholder="Ex: Econômico, SUV, Luxo">
                </div>
                <div class="form-group">
                    <label for="valorDiaria">Valor da Diária (R$) *</label>
                    <input type="number" id="valorDiaria" name="valorDiaria" value="${categoria.valorDiaria}" required min="0.01" step="0.01" placeholder="Ex: 150.00">
                </div>
                <div class="form-group full">
                    <label for="descricao">Descrição</label>
                    <input type="text" id="descricao" name="descricao" value="${categoria.descricao}" maxlength="255" placeholder="Descrição opcional">
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Salvar</button>
                <a href="${pageContext.request.contextPath}/categorias" class="btn btn-outline">Cancelar</a>
            </div>
        </form>
    </div>
</div>

</div></body></html>

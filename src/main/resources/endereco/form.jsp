<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.jsp" %>

<div class="page-title">${empty endereco.id ? '➕ Novo Endereço' : '✏️ Editar Endereço'}</div>

<div class="card">
    <div class="card-header">${empty endereco.id ? 'Cadastrar Endereço' : 'Atualizar Endereço'}</div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/enderecos" method="post">
            <c:if test="${not empty endereco.id}">
                <input type="hidden" name="id" value="${endereco.id}">
            </c:if>
            <div class="form-grid">
                <div class="form-group">
                    <label for="logradouro">Logradouro *</label>
                    <input type="text" id="logradouro" name="logradouro" value="${endereco.logradouro}" required maxlength="120" placeholder="Rua, Avenida...">
                </div>
                <div class="form-group">
                    <label for="numero">Número *</label>
                    <input type="text" id="numero" name="numero" value="${endereco.numero}" required maxlength="20">
                </div>
                <div class="form-group">
                    <label for="cep">CEP *</label>
                    <input type="text" id="cep" name="cep" value="${endereco.cep}" required maxlength="10" placeholder="00000-000">
                </div>
                <div class="form-group">
                    <label for="cidade">Cidade *</label>
                    <input type="text" id="cidade" name="cidade" value="${endereco.cidade}" required maxlength="80">
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Salvar</button>
                <a href="${pageContext.request.contextPath}/enderecos" class="btn btn-outline">Cancelar</a>
            </div>
        </form>
    </div>
</div>

</div></body></html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../header.jsp" %>

<div class="page-title">${empty locatario.cpf ? '➕ Novo Locatário' : '✏️ Editar Locatário'}</div>

<div class="card">
    <div class="card-header">${empty locatario.cpf ? 'Cadastrar Locatário' : 'Atualizar Locatário'}</div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/locatarios" method="post">
            <div class="form-grid">
                <div class="form-group">
                    <label for="cpf">CPF *</label>
                    <input type="text" id="cpf" name="cpf" value="${locatario.cpf}"
                           required maxlength="14" placeholder="000.000.000-00"
                           ${not empty locatario.cpf ? 'readonly' : ''}>
                </div>
                <div class="form-group">
                    <label for="nome">Nome Completo *</label>
                    <input type="text" id="nome" name="nome" value="${locatario.nome}" required maxlength="120">
                </div>
                <div class="form-group">
                    <label for="numeroHabilitacao">Nº Habilitação *</label>
                    <input type="text" id="numeroHabilitacao" name="numeroHabilitacao" value="${locatario.numeroHabilitacao}" required maxlength="30">
                </div>
                <div class="form-group">
                    <label for="dataNascimento">Data de Nascimento *</label>
                    <input type="date" id="dataNascimento" name="dataNascimento" value="${locatario.dataNascimento}" required>
                </div>
                <div class="form-group full">
                    <label for="enderecoId">Endereço *</label>
                    <select id="enderecoId" name="enderecoId" required>
                        <option value="">Selecione um endereço</option>
                        <c:forEach var="end" items="${enderecos}">
                            <option value="${end.id}" ${locatario.enderecoId == end.id ? 'selected' : ''}>
                                ${end.logradouro}, ${end.numero} — ${end.cidade} (${end.cep})
                            </option>
                        </c:forEach>
                    </select>
                    <small><a href="${pageContext.request.contextPath}/enderecos/novo" target="_blank">+ Cadastrar novo endereço</a></small>
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Salvar</button>
                <a href="${pageContext.request.contextPath}/locatarios" class="btn btn-outline">Cancelar</a>
            </div>
        </form>
    </div>
</div>

</div></body></html>

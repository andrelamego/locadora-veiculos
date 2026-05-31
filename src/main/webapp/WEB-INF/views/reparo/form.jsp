<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title">${empty reparo.id ? '➕ Novo Reparo' : '✏️ Editar Reparo'}</div>

<div class="card">
    <div class="card-header">${empty reparo.id ? 'Cadastrar Reparo' : 'Atualizar Reparo'}</div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/reparos" method="post">
            <c:if test="${not empty reparo.id}">
                <input type="hidden" name="id" value="${reparo.id}">
            </c:if>
            <div class="form-grid">
                <div class="form-group">
                    <label for="veiculoPlaca">Veículo *</label>
                    <select id="veiculoPlaca" name="veiculoPlaca" required>
                        <option value="">Selecione o veículo</option>
                        <c:forEach var="v" items="${veiculos}">
                            <option value="${v.placa}" ${reparo.veiculoPlaca == v.placa ? 'selected' : ''}>
                                ${v.placa} — ${v.marca} ${v.modelo}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="dataEntrada">Data de Entrada *</label>
                    <input type="date" id="dataEntrada" name="dataEntrada" value="${reparo.dataEntrada}" required>
                </div>
                <div class="form-group">
                    <label for="quantidadeDias">Dias para Reparo *</label>
                    <input type="number" id="quantidadeDias" name="quantidadeDias" value="${reparo.quantidadeDias}" required min="1">
                </div>
                <div class="form-group">
                    <label for="valorReparo">Valor do Reparo (R$) *</label>
                    <input type="number" id="valorReparo" name="valorReparo" value="${reparo.valorReparo}" required min="0" step="0.01">
                </div>
                <div class="form-group full">
                    <label for="descricaoProblema">Descrição do Problema *</label>
                    <textarea id="descricaoProblema" name="descricaoProblema" rows="4" required placeholder="Descreva o problema identificado...">${reparo.descricaoProblema}</textarea>
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Salvar</button>
                <a href="${pageContext.request.contextPath}/reparos" class="btn btn-outline">Cancelar</a>
            </div>
        </form>
    </div>
</div>

</div></body></html>

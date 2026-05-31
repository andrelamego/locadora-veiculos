<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title">${empty veiculo.placa ? '➕ Novo Veículo' : '✏️ Editar Veículo'}</div>

<div class="card">
    <div class="card-header">${empty veiculo.placa ? 'Cadastrar Veículo' : 'Atualizar Veículo'}</div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/veiculos" method="post">
            <div class="form-grid">
                <div class="form-group">
                    <label for="placa">Placa *</label>
                    <input type="text" id="placa" name="placa" value="${veiculo.placa}"
                           required maxlength="10" placeholder="ABC-1234"
                           ${not empty veiculo.placa ? 'readonly' : ''}>
                </div>
                <div class="form-group">
                    <label for="marca">Marca *</label>
                    <input type="text" id="marca" name="marca" value="${veiculo.marca}" required maxlength="80">
                </div>
                <div class="form-group">
                    <label for="modelo">Modelo *</label>
                    <input type="text" id="modelo" name="modelo" value="${veiculo.modelo}" required maxlength="80">
                </div>
                <div class="form-group">
                    <label for="cor">Cor *</label>
                    <input type="text" id="cor" name="cor" value="${veiculo.cor}" required maxlength="40">
                </div>
                <div class="form-group">
                    <label for="ano">Ano *</label>
                    <input type="number" id="ano" name="ano" value="${veiculo.ano}" required min="1980" max="2030">
                </div>
                <div class="form-group">
                    <label for="quilometragem">Quilometragem *</label>
                    <input type="number" id="quilometragem" name="quilometragem" value="${veiculo.quilometragem}" required min="0">
                </div>
                <div class="form-group">
                    <label for="capacidadeTanque">Capacidade Tanque (L) *</label>
                    <input type="number" id="capacidadeTanque" name="capacidadeTanque" value="${veiculo.capacidadeTanque}" required min="0.01" step="0.01">
                </div>
                <div class="form-group">
                    <label for="tipoCombustivel">Combustível *</label>
                    <select id="tipoCombustivel" name="tipoCombustivel" required>
                        <option value="">Selecione</option>
                        <c:forEach var="tc" items="${tiposCombustivel}">
                            <option value="${tc}" ${veiculo.tipoCombustivel == tc ? 'selected' : ''}>${tc}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="tipoCambio">Câmbio *</label>
                    <select id="tipoCambio" name="tipoCambio" required>
                        <option value="">Selecione</option>
                        <c:forEach var="tc" items="${tiposCambio}">
                            <option value="${tc}" ${veiculo.tipoCambio == tc ? 'selected' : ''}>${tc}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="status">Status *</label>
                    <select id="status" name="status" required>
                        <c:forEach var="s" items="${statusVeiculo}">
                            <option value="${s}" ${veiculo.status == s ? 'selected' : ''}>${s}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group full">
                    <label for="categoriaId">Categoria *</label>
                    <select id="categoriaId" name="categoriaId" required>
                        <option value="">Selecione a categoria</option>
                        <c:forEach var="cat" items="${categorias}">
                            <option value="${cat.id}" ${veiculo.categoriaId == cat.id ? 'selected' : ''}>
                                ${cat.nome} — R$ ${cat.valorDiaria}/dia
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Salvar</button>
                <a href="${pageContext.request.contextPath}/veiculos" class="btn btn-outline">Cancelar</a>
            </div>
        </form>
    </div>
</div>

</div></body></html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title"><i class="bi bi-arrow-repeat" aria-hidden="true"></i>Registrar Devolução</div>

<div class="info-box">
    <h4>Dados da Locação #${locacao.id}</h4>
    <dl>
        <dt>Veículo</dt>        <dd>${locacao.veiculoPlaca} — ${locacao.veiculoModelo}</dd>
        <dt>Locatário</dt>      <dd>${locacao.locatarioNome}</dd>
        <dt>Retirada</dt>       <dd>${locacao.dataRetirada}</dd>
        <dt>Dias</dt>           <dd>${locacao.quantidadeDias}</dd>
        <dt>Prev. Devolução</dt><dd>${locacao.dataPrevistaDevolucao}</dd>
    </dl>
</div>

<div class="card">
    <div class="card-header">Informar Devolução</div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/devolucoes" method="post">
            <input type="hidden" name="locacaoId" value="${locacao.id}">
            <div class="form-grid">
                <div class="form-group">
                    <label for="dataDevolucao">Data de Devolução *</label>
                    <input type="date" id="dataDevolucao" name="dataDevolucao" value="${dataDevolucao}" required>
                </div>
                <div class="form-group">
                    <label for="litrosFaltantes">Litros Faltantes no Tanque *</label>
                    <input type="number" id="litrosFaltantes" name="litrosFaltantes"
                           value="0" required min="0" step="0.01"
                           placeholder="0 = tanque cheio">
                    <small style="color:#6b7280">Gasolina: R$7,00/L · Álcool: R$5,50/L</small>
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Confirmar Devolução</button>
                <a href="${pageContext.request.contextPath}/locacoes" class="btn btn-outline">Cancelar</a>
            </div>
        </form>
    </div>
</div>

</div></body></html>

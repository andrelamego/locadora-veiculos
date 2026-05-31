<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title">➕ Nova Locação</div>

<div class="card">
    <div class="card-header">Registrar Locação</div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/locacoes" method="post">
            <div class="form-grid">
                <div class="form-group">
                    <label for="veiculoPlaca">Veículo *</label>
                    <select id="veiculoPlaca" name="veiculoPlaca" required>
                        <option value="">Selecione o veículo</option>
                        <c:forEach var="v" items="${veiculos}">
                            <option value="${v.placa}" ${locacao.veiculoPlaca == v.placa ? 'selected' : ''}>
                                ${v.placa} — ${v.marca} ${v.modelo} (${v.status})
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="locatarioCpf">Locatário *</label>
                    <select id="locatarioCpf" name="locatarioCpf" required>
                        <option value="">Selecione o locatário</option>
                        <c:forEach var="l" items="${locatarios}">
                            <option value="${l.cpf}" ${locacao.locatarioCpf == l.cpf ? 'selected' : ''}>
                                ${l.nome} (${l.cpf})
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="dataRetirada">Data de Retirada *</label>
                    <input type="date" id="dataRetirada" name="dataRetirada" value="${locacao.dataRetirada}" required>
                </div>
                <div class="form-group">
                    <label for="quantidadeDias">Quantidade de Dias *</label>
                    <input type="number" id="quantidadeDias" name="quantidadeDias" value="${locacao.quantidadeDias}" required min="1">
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Registrar Locação</button>
                <a href="${pageContext.request.contextPath}/locacoes" class="btn btn-outline">Cancelar</a>
            </div>
        </form>
    </div>
</div>

</div></body></html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title"><i class="bi bi-check-circle" aria-hidden="true"></i>Veículos Disponíveis</div>

<div class="card">
    <div class="card-header">
        Consulta por Categoria
    </div>
    <div class="card-body">
        <form method="get" action="${pageContext.request.contextPath}/veiculos/disponiveis">
            <div class="form-grid">
                <div class="form-group">
                    <label for="categoriaId">Categoria</label>
                    <select id="categoriaId" name="categoriaId" required>
                        <option value="">Selecione uma categoria</option>
                        <c:forEach var="categoria" items="${categorias}">
                            <option value="${categoria.id}" ${categoria.id == categoriaSelecionada ? 'selected' : ''}>
                                ${categoria.nome} -
                                <fmt:formatNumber value="${categoria.valorDiaria}" type="currency" currencySymbol="R$"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Buscar Disponíveis</button>
        </form>
    </div>
</div>

<c:if test="${categoriaSelecionada != null}">
    <div class="card">
        <div class="card-header">
            Veículos Encontrados
        </div>
        <div class="card-body">
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>Placa</th>
                            <th>Marca</th>
                            <th>Modelo</th>
                            <th>Cor</th>
                            <th>Ano</th>
                            <th>Combustível</th>
                            <th>Câmbio</th>
                            <th>Km</th>
                            <th>Diária</th>
                            <th>Ação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="v" items="${veiculos}">
                            <tr>
                                <td><strong>${v.placa}</strong></td>
                                <td>${v.marca}</td>
                                <td>${v.modelo}</td>
                                <td>${v.cor}</td>
                                <td>${v.ano}</td>
                                <td>${v.tipoCombustivel}</td>
                                <td>${v.tipoCambio}</td>
                                <td>${v.quilometragem}</td>
                                <td><fmt:formatNumber value="${v.valorDiaria}" type="currency" currencySymbol="R$"/></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/locacoes/nova/${v.placa}" class="btn btn-accent btn-sm">Nova Locacao</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty veiculos}">
                            <tr>
                                <td colspan="10" style="text-align:center;color:#6b7280;padding:2rem">
                                    Nenhum veículo disponível para esta categoria.
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</c:if>

</div></body></html>

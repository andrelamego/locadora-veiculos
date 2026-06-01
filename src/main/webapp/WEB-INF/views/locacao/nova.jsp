<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../fragmentos/header.jsp" %>

<div class="page-title"><i class="bi bi-key" aria-hidden="true"></i>Nova locacao</div>

<div class="rental-layout">
    <section class="card">
        <div class="card-header">Carros disponiveis</div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty veiculos}">
                    <div class="empty-state">Nenhum veiculo disponivel no momento.</div>
                </c:when>
                <c:otherwise>
                    <div class="vehicle-grid">
                        <c:forEach var="veiculo" items="${veiculos}">
                            <article class="vehicle-card ${not empty veiculoSelecionado && veiculoSelecionado.placa == veiculo.placa ? 'vehicle-card-selected' : ''}">
                                <div class="vehicle-card-header">
                                    <div>
                                        <strong>${veiculo.marca} ${veiculo.modelo}</strong>
                                        <span>${veiculo.cor} - ${veiculo.ano}</span>
                                    </div>
                                    <span class="plate">${veiculo.placa}</span>
                                </div>

                                <div class="vehicle-specs">
                                    <span><i class="bi bi-tags" aria-hidden="true"></i>${veiculo.categoriaNome}</span>
                                    <span><i class="bi bi-fuel-pump" aria-hidden="true"></i>${veiculo.tipoCombustivel}</span>
                                    <span><i class="bi bi-gear" aria-hidden="true"></i>${veiculo.tipoCambio}</span>
                                    <span><i class="bi bi-speedometer2" aria-hidden="true"></i>${veiculo.quilometragem} km</span>
                                </div>

                                <div class="vehicle-card-footer">
                                    <div class="vehicle-price">
                                        <span>Diaria</span>
                                        <strong>R$ <fmt:formatNumber value="${veiculo.valorDiaria}" minFractionDigits="2"/></strong>
                                    </div>
                                    <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/locacoes/nova/${veiculo.placa}">
                                        <i class="bi bi-check2-circle" aria-hidden="true"></i> Escolher
                                    </a>
                                </div>
                            </article>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </section>

    <section class="card">
        <div class="card-header">Dados da locacao</div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty veiculoSelecionado}">
                    <div class="empty-state">Escolha um carro disponivel para iniciar a locacao.</div>
                </c:when>
                <c:otherwise>
                    <div class="selected-vehicle">
                        <div>
                            <strong>${veiculoSelecionado.marca} ${veiculoSelecionado.modelo}</strong>
                            <span>${veiculoSelecionado.placa}</span>
                        </div>
                        <div>
                            <span>${veiculoSelecionado.categoriaNome}</span>
                            <strong>R$ <fmt:formatNumber value="${veiculoSelecionado.valorDiaria}" minFractionDigits="2"/></strong>
                        </div>
                    </div>

                    <form class="form-grid single-line-form" method="get" action="${pageContext.request.contextPath}/locacoes/nova/${veiculoSelecionado.placa}/cpf">
                        <label>
                            CPF
                            <input type="text" name="cpf" value="${cpf}" maxlength="14" placeholder="000.000.000-00" required>
                        </label>
                        <button class="btn btn-secondary" type="submit">
                            <i class="bi bi-search" aria-hidden="true"></i> Buscar
                        </button>
                    </form>

                    <c:if test="${cpfConsultado && not empty locatarioEncontrado}">
                        <div class="info-panel">
                            <div class="section-title">Locatario encontrado</div>
                            <p><strong>${locatarioEncontrado.nome}</strong></p>
                            <p>CPF: ${locatarioEncontrado.cpf}</p>
                            <p>CNH: ${locatarioEncontrado.numeroHabilitacao}</p>
                            <p>${locatarioEncontrado.enderecoResumo}</p>
                        </div>

                        <form class="form-grid" method="post" action="${pageContext.request.contextPath}/locacoes/nova/confirmar">
                            <input type="hidden" name="placa" value="${veiculoSelecionado.placa}">
                            <input type="hidden" name="cpf" value="${locatarioEncontrado.cpf}">
                            <label>
                                Data de retirada
                                <input type="date" name="dataRetirada" value="${dataRetirada}" required>
                            </label>
                            <label>
                                Dias de locacao
                                <input type="number" name="quantidadeDias" value="${quantidadeDias}" min="1" required>
                            </label>
                            <div class="form-actions">
                                <button class="btn btn-primary" type="submit">
                                    <i class="bi bi-check-lg" aria-hidden="true"></i> Confirmar locacao
                                </button>
                            </div>
                        </form>
                    </c:if>

                    <c:if test="${cpfConsultado && empty locatarioEncontrado}">
                        <div class="info-panel">
                            <div class="section-title">Novo locatario</div>
                            <p>CPF nao encontrado. Preencha o cadastro para concluir a locacao.</p>
                        </div>

                        <form class="form-grid" method="post" action="${pageContext.request.contextPath}/locacoes/nova/cadastrar-confirmar">
                            <input type="hidden" name="placa" value="${veiculoSelecionado.placa}">
                            <input type="hidden" name="cpf" value="${cpf}">

                            <label>
                                Nome
                                <input type="text" name="nome" required>
                            </label>
                            <label>
                                Numero da habilitacao
                                <input type="text" name="numeroHabilitacao" required>
                            </label>
                            <label>
                                Data de nascimento
                                <input type="date" name="dataNascimento" required>
                            </label>
                            <label>
                                Data de retirada
                                <input type="date" name="dataRetirada" value="${dataRetirada}" required>
                            </label>
                            <label>
                                Dias de locacao
                                <input type="number" name="quantidadeDias" value="${quantidadeDias}" min="1" required>
                            </label>
                            <label>
                                Logradouro
                                <input type="text" name="logradouro" required>
                            </label>
                            <label>
                                Numero
                                <input type="text" name="numero" required>
                            </label>
                            <label>
                                CEP
                                <input type="text" name="cep" required>
                            </label>
                            <label>
                                Cidade
                                <input type="text" name="cidade" required>
                            </label>

                            <div class="form-actions">
                                <button class="btn btn-primary" type="submit">
                                    <i class="bi bi-check-lg" aria-hidden="true"></i> Cadastrar e confirmar
                                </button>
                            </div>
                        </form>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</div>

</div></body></html>

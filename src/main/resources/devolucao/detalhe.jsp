<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../header.jsp" %>

<div class="page-title">🧾 Comprovante de Devolução #${devolucao.id}</div>

<div class="card">
    <div class="card-header">Detalhes da Devolução</div>
    <div class="card-body">
        <div class="info-box">
            <h4>Veículo e Locatário</h4>
            <dl>
                <dt>Veículo</dt>   <dd>${devolucao.veiculoPlaca} — ${devolucao.veiculoModelo}</dd>
                <dt>Locatário</dt> <dd>${devolucao.locatarioNome}</dd>
                <dt>Devolução</dt> <dd>${devolucao.dataDevolucao}</dd>
            </dl>
        </div>

        <table style="width:auto;margin-top:1rem">
            <tr>
                <td style="padding:0.5rem 1rem;font-weight:700;color:#6b7280">Litros faltantes</td>
                <td style="padding:0.5rem 1rem">${devolucao.litrosFaltantes} L</td>
            </tr>
            <tr>
                <td style="padding:0.5rem 1rem;font-weight:700;color:#6b7280">Valor combustível</td>
                <td style="padding:0.5rem 1rem"><fmt:formatNumber value="${devolucao.valorCombustivel}" type="currency" currencySymbol="R$"/></td>
            </tr>
            <tr>
                <td style="padding:0.5rem 1rem;font-weight:700;color:#6b7280">Valor locação</td>
                <td style="padding:0.5rem 1rem"><fmt:formatNumber value="${devolucao.valorLocacao}" type="currency" currencySymbol="R$"/></td>
            </tr>
            <tr style="border-top:2px solid #1a3c5e">
                <td style="padding:0.75rem 1rem;font-weight:800;color:#1a3c5e;font-size:1.1rem">TOTAL A PAGAR</td>
                <td style="padding:0.75rem 1rem">
                    <span class="valor-total"><fmt:formatNumber value="${devolucao.valorTotal}" type="currency" currencySymbol="R$"/></span>
                </td>
            </tr>
        </table>

        <div style="margin-top:1.5rem">
            <a href="${pageContext.request.contextPath}/devolucoes" class="btn btn-outline">← Voltar</a>
        </div>
    </div>
</div>

</div></body></html>

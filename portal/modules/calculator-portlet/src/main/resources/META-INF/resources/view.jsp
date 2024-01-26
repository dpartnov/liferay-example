<%@ page import="com.example.liferay.calculator.constants.CalculatorPortletKeys" %>

<%@ include file="./init.jsp" %>

<portlet:actionURL var="calculateUrl" name="<%= CalculatorPortletKeys.ACTION_CALCULATE %>">
</portlet:actionURL>

<p>
    <b><liferay-ui:message key="calculator.annualRateText"/> <c:out value="${annualRate}"/></b><br>
    <b><liferay-ui:message key="calculator.rpsnText"/> <c:out value="${rpsn}"/></b>
</p>

<fmt:setLocale value="en"/>
<fmt:formatNumber type="number"
                  pattern="0.00" value="${CalculatorPortletKeys.ATTR_AMOUNT}"
                  var="loanAmount"/>

<aui:form action="${calculateUrl}" method="post" name="calculateForm">
    <aui:input name="<%= CalculatorPortletKeys.ATTR_AMOUNT %>"
               type="number"
               required="true"
               label="calculator.firstInputText"
               value="${loanAmount}">
        <aui:validator name="min" errorMessage="calculator.firstInputError">10000</aui:validator>
        <aui:validator name="max" errorMessage="calculator.firstInputError">100000000</aui:validator>
        <aui:validator name="digits"/>
    </aui:input>
    <aui:input name="<%= CalculatorPortletKeys.ATTR_MONTHS %>"
               type="number"
               required="true"
               label="calculator.secondInputText"
               value="${months}">
        <aui:validator name="min" errorMessage="calculator.secondInputError">6</aui:validator>
        <aui:validator name="max" errorMessage="calculator.secondInputError">120</aui:validator>
        <aui:validator name="digits"/>
    </aui:input>
    <aui:button name="submitButton" type="submit" value="calculator.submit" />
    <b><liferay-ui:message key="calculator.resultText"/> <c:out value="${result}"/></b>

</aui:form>
<%@ include file="init.jsp" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>"
                           var="configurationActionURL"/>

<liferay-portlet:renderURL portletConfiguration="<%= true %>"
                           var="configurationRenderURL"/>

<div class="portlet-configuration">
    <aui:form action="<%= configurationActionURL %>" method="post" name="config-form">
        <div class="portlet-configuration-body-content">
            <div class="container-fluid-1280">
                <aui:fieldset markupView="lexicon">

                    <aui:fieldset collapsed="true" collapsible="true" label="Configuration">
                        <aui:input name="<%= Constants.CMD %>"
                                   type="hidden"
                                   value="<%= Constants.UPDATE %>"/>

                        <aui:input name="redirect" type="hidden"
                                   value="<%= configurationRenderURL %>"/>

                        <aui:input name="annualRate"
                                   label="calculator.annualRateText"
                                   value="${annualRate}"
                        />

                        <aui:input name="rpsn"
                                   label="calculator.rpsnText"
                                   value="${rpsn}"
                        />

                    </aui:fieldset>

                </aui:fieldset>
            </div>
        </div>

        <aui:button-row>
            <aui:button type="submit"/>
        </aui:button-row>

    </aui:form>
</div>

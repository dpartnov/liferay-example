package com.example.liferay.calculator.portlet;

import com.example.liferay.calculator.configuration.CalculatorConfiguration;
import com.example.liferay.calculator.constants.CalculatorPortletKeys;
import com.example.liferay.calculator.utils.Utils;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

/**
 * @author dpart
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.header-portlet-css=/css/main.scss",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=Calculator",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.config-template=/configuration.jsp",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=" + CalculatorPortletKeys.CALCULATOR,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class CalculatorPortlet extends MVCPortlet {

    @Reference
    private ConfigurationProvider configurationProvider;

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        //Get properties from session or default values:
        double annualRate = getAnnualRateFromConfig(renderRequest);
        double rpsn = getRpsnFromConfig(renderRequest);
        int loanAmmount = Utils.getLoanAmmount(renderRequest) == 0 ? 100000 : Utils.getLoanAmmount(renderRequest);
        int months = Utils.getMonths(renderRequest) == 0 ? 12 : Utils.getMonths(renderRequest);

        //Set properties to render request for FE view:
        renderRequest.setAttribute(CalculatorPortletKeys.ATTR_ANNUAL_RATE, annualRate);
        renderRequest.setAttribute(CalculatorPortletKeys.ATTR_RPSN, rpsn);
        renderRequest.setAttribute(CalculatorPortletKeys.ATTR_AMOUNT, loanAmmount);
        renderRequest.setAttribute(CalculatorPortletKeys.ATTR_MONTHS, months);
        renderRequest.setAttribute(CalculatorPortletKeys.ATTR_RESULT, Utils.getResult(renderRequest));

        super.doView(renderRequest, renderResponse);
    }

    private double getAnnualRateFromConfig(RenderRequest renderRequest) {
        return getConfiguration(renderRequest).annualRate();
    }

    private double getRpsnFromConfig(RenderRequest renderRequest) {
        return getConfiguration(renderRequest).rpsn();
    }

    private CalculatorConfiguration getConfiguration(RenderRequest renderRequest) {
        final ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        try {
            return configurationProvider.getPortletInstanceConfiguration(CalculatorConfiguration.class, themeDisplay);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

}
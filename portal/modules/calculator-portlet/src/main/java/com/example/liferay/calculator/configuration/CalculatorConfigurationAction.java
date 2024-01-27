package com.example.liferay.calculator.configuration;

import com.example.liferay.calculator.constants.CalculatorPortletKeys;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component(
        configurationPid = CalculatorPortletKeys.CONFIGURATION_PID,
        configurationPolicy = ConfigurationPolicy.OPTIONAL,
        immediate = true,
        property = {
                "javax.portlet.name=" + CalculatorPortletKeys.CALCULATOR
        },
        service = ConfigurationAction.class)
public class CalculatorConfigurationAction extends DefaultConfigurationAction {

    @Reference
    private ConfigurationProvider configurationProvider;

    @Override
    public void processAction(
            PortletConfig portletConfig, ActionRequest actionRequest,
            ActionResponse actionResponse)
            throws Exception {

        final String annualRate = ParamUtil.getString(actionRequest, CalculatorPortletKeys.ATTR_ANNUAL_RATE);
        final String rpsn = ParamUtil.getString(actionRequest, CalculatorPortletKeys.ATTR_RPSN);
        setPreference(actionRequest, CalculatorPortletKeys.ATTR_ANNUAL_RATE, annualRate);
        setPreference(actionRequest, CalculatorPortletKeys.ATTR_RPSN, rpsn);
        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    @Override
    public void include(
            PortletConfig portletConfig, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {

        final ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
        if (themeDisplay != null) {
            final CalculatorConfiguration configuration =
                    configurationProvider.getPortletInstanceConfiguration(CalculatorConfiguration.class, themeDisplay);

            httpServletRequest.setAttribute(CalculatorPortletKeys.ATTR_ANNUAL_RATE, configuration.annualRate());
            httpServletRequest.setAttribute(CalculatorPortletKeys.ATTR_RPSN, configuration.rpsn());
        }

        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }
}

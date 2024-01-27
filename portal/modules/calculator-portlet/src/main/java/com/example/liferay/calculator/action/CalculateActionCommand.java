package com.example.liferay.calculator.action;

import com.example.liferay.calculator.configuration.CalculatorConfiguration;
import com.example.liferay.calculator.configuration.CalculatorConfigurationAction;
import com.example.liferay.calculator.constants.CalculatorPortletKeys;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.liferay.calculator.constants.CalculatorPortletKeys.ATTR_AMOUNT;
import static com.example.liferay.calculator.constants.CalculatorPortletKeys.ATTR_MONTHS;
import static com.example.liferay.calculator.constants.CalculatorPortletKeys.ATTR_RESULT;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + CalculatorPortletKeys.CALCULATOR,
                "mvc.command.name=" + CalculatorPortletKeys.ACTION_CALCULATE
        },
        service = MVCActionCommand.class)
public class CalculateActionCommand extends BaseMVCActionCommand {

    private static final Logger LOG = LoggerFactory.getLogger(CalculateActionCommand.class);

    @Reference
    private ConfigurationProvider configurationProvider;

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        //Load properties from FE:
        int months = ParamUtil.get(actionRequest, ATTR_MONTHS, CalculatorPortletKeys.DEFAULT_MONTHS);
        int loanAmmount = ParamUtil.get(actionRequest, ATTR_AMOUNT, CalculatorPortletKeys.DEFAULT_AMOUNT);
        //Load property from config:
        double annualRate = getAnnualRateFromConfig(actionRequest);
        //Calculate monthly payments via REST API:
        LOG.info("Send calculation request to backend: months={}, loanAmmount={}, annualRate={}", months, loanAmmount, annualRate);
        int monthlyPayment = calculateMonthlyPayment(loanAmmount, months, annualRate);

        //Save results to session attribute:
        final PortletSession portletSession = actionRequest.getPortletSession();
        portletSession.setAttribute(ATTR_RESULT, monthlyPayment);
        portletSession.setAttribute(ATTR_AMOUNT, loanAmmount);
        portletSession.setAttribute(ATTR_MONTHS, months);
    }

    private double getAnnualRateFromConfig(ActionRequest actionRequest) {
        try {
            final ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
            if (themeDisplay != null) {
                final CalculatorConfiguration configuration = configurationProvider.getPortletInstanceConfiguration(CalculatorConfiguration.class, themeDisplay);
                return configuration.annualRate();
            }
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private int calculateMonthlyPayment(int loanAmmount, int months, double annualRate) {
        try {
            URL url = new URL("http://backend:8081/calculator?loanAmount=" + loanAmmount + "&months=" + months + "&annualRate=" + annualRate);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            int responseCode = httpURLConnection.getResponseCode();
            StringBuffer response = new StringBuffer();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }

            return Integer.valueOf(response.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

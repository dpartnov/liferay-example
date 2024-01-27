package com.example.liferay.calculator.utils;

import com.example.liferay.calculator.configuration.CalculatorConfiguration;
import com.example.liferay.calculator.constants.CalculatorPortletKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;

import static com.example.liferay.calculator.constants.CalculatorPortletKeys.ATTR_AMOUNT;
import static com.example.liferay.calculator.constants.CalculatorPortletKeys.ATTR_MONTHS;
import static com.example.liferay.calculator.constants.CalculatorPortletKeys.ATTR_RESULT;

public class Utils {

    public static int getLoanAmmount(PortletRequest request) {
        return getIntSessionAttribute(request, ATTR_AMOUNT, PortletSession.PORTLET_SCOPE);
    }

    public static int getMonths(PortletRequest request) {
        return getIntSessionAttribute(request, ATTR_MONTHS, PortletSession.PORTLET_SCOPE);
    }

    public static int getResult(PortletRequest request) {
        return getIntSessionAttribute(request, ATTR_RESULT, PortletSession.PORTLET_SCOPE);
    }

    private static int getIntSessionAttribute(PortletRequest request, String name, int scope) {
        final PortletSession portletSession = request.getPortletSession();
        int value = 0;
        final Integer valueAttr = (Integer) portletSession.getAttribute(name, scope);
        if (valueAttr != null) {
            value = valueAttr;
        }
        return value;
    }
}

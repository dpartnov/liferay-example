package com.example.liferay.calculator.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.example.liferay.calculator.constants.CalculatorPortletKeys;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
        scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE)
@Meta.OCD(
        id = CalculatorPortletKeys.CONFIGURATION_PID)
public interface CalculatorConfiguration {

    @Meta.AD(deflt = "6.9", required = false)
    double annualRate();

    @Meta.AD(deflt = "7.1", required = false)
    double rpsn();

}
package com.example.liferay.calculator.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.example.liferay.calculator.constants.CalculatorPortletKeys;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

// Use the @ExtendedObjectClassDefinition annotation to set your scope.
// https://learn.liferay.com/w/dxp/building-applications/core-frameworks/configuration-framework/scoping-configurations
@ExtendedObjectClassDefinition(
        scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE)
// Meta.OCD registers this class as a configuration with a specific ID.
// Note that the ID must be the fully qualified class name (FQCN) of the configuration interface.
// https://learn.liferay.com/w/dxp/building-applications/core-frameworks/configuration-framework/setting-and-accessing-configurations
@Meta.OCD(
        id = CalculatorPortletKeys.CONFIGURATION_PID)
public interface CalculatorConfiguration {

    // Meta.AD specifies optional metadata about the attribute such as a default value or whether the attribute is a required field.
    // Note that if an attribute value is required but a default is not set,
    // an administrator must set a value in settings for the application to work properly.
    @Meta.AD(deflt = "6.9", required = false)
    double annualRate();

    @Meta.AD(deflt = "7.1", required = false)
    double rpsn();

}
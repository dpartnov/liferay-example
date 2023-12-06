#!/usr/bin/env bash

# Standard Liferay settings
CATALINA_OPTS="$CATALINA_OPTS -Dfile.encoding=UTF8 -Djava.net.preferIPv4Stack=true -Dorg.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false -Duser.timezone=Europe/Prague"

# JVM memory configuration
CATALINA_OPTS="$CATALINA_OPTS -Xms2g -Xmx3g"

# Defines JAXBContextFactory which is placed in Liferay libs directory. It is needed to use JAXB on JDK 11.
CATALINA_OPTS="$CATALINA_OPTS -Djavax.xml.bind.JAXBContextFactory=com.sun.xml.bind.v2.ContextFactory"

# Enable debugging
CATALINA_OPTS="$CATALINA_OPTS -agentlib:jdwp=transport=dt_socket,address=*:8000,server=y,suspend=n"

# Enable remote JMX for management and monitoring
CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote"
CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote.port=19999"
CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote.rmi.port=19999"
CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote.ssl=false"
CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote.authenticate=false"
CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote.local.only=false"
CATALINA_OPTS="$CATALINA_OPTS -Djava.rmi.server.hostname='127.0.0.1'"

export CATALINA_OPTS
if [ -r "$CATALINA_BASE/bin/setsecret.sh" ]; then
  . "$CATALINA_BASE/bin/setsecret.sh"
fi

CATALINA_PID="$CATALINA_HOME/temp/catalina.pid"

# Define Tomcat system classloader - see https://tomcat.apache.org/tomcat-9.0-doc/class-loader-howto.html
CLASSPATH="$CATALINA_HOME/lib/tomcat-system/*"
echo "Tomcat System Classpath set using CLASSPATH=$CLASSPATH"

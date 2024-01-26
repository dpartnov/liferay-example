##
## Stage 1 - Build using Maven
##
FROM maven:3-openjdk-11 as BUILD

# Copy entire project and build it
# This layer is built when something in the project changes
COPY . /project/
RUN mkdir -p /project/deploy/
WORKDIR /project/
RUN mvn clean install


##
## Stage 2 - Create runnable image
##
FROM liferay-base:7.4.3.102

# Common configs/properties
COPY --chown=liferay:liferay configs/common/ ${LIFERAY_HOME}/
COPY --chown=liferay:liferay configs/local/ ${LIFERAY_HOME}/

# Deploy portlets
COPY --from=BUILD --chown=liferay:liferay /project/deploy/*.jar $LIFERAY_HOME/osgi/modules/

# java-debug
EXPOSE 8000

# Custom entrypoint which handles artifacts deployment
COPY --chown=liferay:liferay scripts/liferay-entrypoint.sh /opt/liferay/liferay-entrypoint.sh
RUN chmod +x /opt/liferay/liferay-entrypoint.sh
ENTRYPOINT ["/opt/liferay/liferay-entrypoint.sh"]

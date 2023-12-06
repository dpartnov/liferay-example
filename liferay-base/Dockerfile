##
## Ubuntu steps
##

FROM ubuntu:24.04

ENV JAVA_VERSION="11"
ENV JAVA_HOME="/usr/lib/jvm/java-${JAVA_VERSION}-openjdk-amd64"
ENV PATH="$JAVA_HOME/bin:$PATH"

RUN set -xe \
  && apt-get update

RUN set -xe \
  && apt-get -y install \
    zip \
    telnet \
    curl \
    openjdk-${JAVA_VERSION}-jdk

RUN set -xe \
  && apt-get -y install \
    inotify-tools

##
## Liferay base steps
##

ENV LIFERAY_HOME /opt/liferay
ENV TOMCAT_HOME ${LIFERAY_HOME}/tomcat

USER root

# Prepare Liferay user and home folders
RUN set -xe \
  && mkdir -p ${LIFERAY_HOME} ${LIFERAY_HOME}/deploy ${LIFERAY_HOME}/logs \
  && groupadd --gid 1001 liferay \
  && useradd --uid 1001 --gid liferay --shell /bin/bash --home-dir ${LIFERAY_HOME} -M liferay \
  && chown -R liferay:liferay ${LIFERAY_HOME}

USER liferay

WORKDIR /opt/liferay

##
## Liferay 7.4 steps
##

RUN set -xe \
  # Install Liferay
  && ARCHIVE_URL="https://releases-cdn.liferay.com/portal/7.4.3.102-ga102/liferay-ce-portal-tomcat-7.4.3.102-ga102-20231109165213885.tar.gz" \
  && curl -LfS --http1.1 -o /tmp/liferay.tar.gz ${ARCHIVE_URL} \
  && tar -xzf /tmp/liferay.tar.gz -C ${LIFERAY_HOME} --strip-components=1 \
  && rm /tmp/liferay.tar.gz \
  && mv ${LIFERAY_HOME}/tomcat-* ${TOMCAT_HOME} \
  && mkdir -p ${LIFERAY_HOME}/deploy ${LIFERAY_HOME}/logs ${LIFERAY_HOME}/osgi/modules

RUN set -xe \
  && rm -rf \
    ${LIFERAY_HOME}/data/elasticsearch* \
    ${LIFERAY_HOME}/data/hsql \
    ${LIFERAY_HOME}/data/hypersonic \
    ${LIFERAY_HOME}/osgi/state/ \
    ${LIFERAY_HOME}/logs/* \
    ${TOMCAT_HOME}/bin/*.bat \
    ${TOMCAT_HOME}/logs/* \
    ${TOMCAT_HOME}/temp/*

# Volumes
VOLUME ["/opt/liferay/data", "/opt/liferay/logs", "/opt/liferay/tomcat/logs"]

# Ports
EXPOSE 8009 8080

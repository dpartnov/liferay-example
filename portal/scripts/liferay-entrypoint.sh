#!/bin/bash

#All artifacts should be put into artifacts folder, since deploy folder is often overriden with empty folder when binding.
ARTIFACTS_DIR="$LIFERAY_HOME/artifacts"


# Copy artifacts into deploy folder
if [ -d $ARTIFACTS_DIR ]; then
    if [ -z "$(ls -A $ARTIFACTS_DIR)" ]; then
        echo "No files found in the $ARTIFACTS_DIR folder"
    else
        echo "Copy files from $ARTIFACTS_DIR folder into deploy folder"
        mv -v $ARTIFACTS_DIR/* $LIFERAY_HOME/deploy/
    fi
fi

# Process config templates
for TPL in "$LIFERAY_HOME"/*.properties.tpl "$LIFERAY_HOME"/osgi/configs/*.config.tpl "$TOMCAT_HOME"/bin/setenv.sh.tpl; do
    if [ -f "$TPL" ]; then
        echo "Found config template: $TPL"
        F="${TPL%.*}"
        process-template --from "$TPL" --target "$F"
    fi
done

# Run Liferay now
/opt/liferay/tomcat/bin/catalina.sh run

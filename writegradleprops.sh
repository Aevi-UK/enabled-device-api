#!/bin/bash

LOCAL_KEYSTORE_FOLDER="keystores"
GRADLE_PROPERTIES_FILE="gradle.properties"
OUTPUT_PROPS=${GRADLE_PROPERTIES_FILE}

mkdir -p "$LOCAL_KEYSTORE_FOLDER"

echo "Getting keystores from S3"
aws s3 cp "s3://upload.keystores.aevi/sdk-dev-keystore.jks" "$LOCAL_KEYSTORE_FOLDER" --region eu-west-1

echo "Create gradle properties file"
echo "dev_keystore_path=$PWD/$LOCAL_KEYSTORE_FOLDER/sdk-dev-keystore.jks" >> ${OUTPUT_PROPS}
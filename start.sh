#!/bin/bash

java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9999 -jar build/libs/mn-sandbox-0.1-all.jar

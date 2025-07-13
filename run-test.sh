#!/bin/bash

# Define the URL you want to test
URL="http://localhost:8080/hello"

# Loop to run curl n times
for i in {1..100}
do
  echo "Running curl request #$i"
  curl -X GET "$URL"
  echo -e "\n"
done
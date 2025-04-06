#!/bin/bash

# Function to kill background processes
cleanup() {
    echo -e "\nStopping all applications..."
    kill $FRONT_PID $BANK_PID $VIDSTREAM_PID
    wait
    exit
}

# Trap Ctrl + C to call the cleanup function
trap cleanup SIGINT

# Navigate to the Front directory and run the second command
clear
echo "Starting Front app..."
cd Front || exit
node server.js &
FRONT_PID=$!
cd ..

# Navigate to the bank_system directory and run the third command
echo "Starting Bank System app..."
cd bank_system || exit
python3 main.py &
BANK_PID=$!
cd ..

# Navigate to the vidstream directory and run the first command
echo "Starting Vidstream app..."
cd vidstream || exit
mvn spring-boot:run &
VIDSTREAM_PID=$!
cd ..

# Wait for all background processes to finish
wait
echo "All applications started!"
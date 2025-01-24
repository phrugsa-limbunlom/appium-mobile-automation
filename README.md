# appium-mobile-automation

# Appium Server Setup

This guide provides instructions on how to set up and run the Appium server for mobile test automation.

## Prerequisites

- Node.js and npm installed on machine
- Appium installed globally via npm

## Installation

1. **Install Node.js and npm**: Follow the instructions on the [Node.js official website](https://nodejs.org/) to download and install Node.js, which includes npm.

2. **Install Appium**: Run the following command to install Appium globally:

   ```sh
   npm install -g appium

## Running the Appium Server
Run the following command
 ```sh
appium
```

the server will start on http://127.0.0.1:4723

## How to solve chrome driver not found
 ```sh
   npm install -g appium@latest
   appium driver uninstall uiautomator2
   appium driver install uiautomator2
   appium --allow-insecure chromedriver_autodownload --log-level debug
```


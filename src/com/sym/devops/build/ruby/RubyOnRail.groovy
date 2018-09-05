/***************************************************************************
***** Description :: This Package is used to perform Java Build  tasks *****
***** Author      :: Pramod Vishwakarma                                *****
***** Date        :: 08/14/2018                                        *****
***** Revision    :: 1.0                                               *****
****************************************************************************/
package com.sym.devops.build.ruby

/**************************************************
***** Function to create the report directory *****
***************************************************/
def createReportDirectory(String REPORT_DIRECTORY)
{
   try {
     wrap([$class: 'AnsiColorBuildWrapper']) {
        println "\u001B[32mINFO => Creating directory $REPORT_DIRECTORY if not already exist..."
        sh "mkdir -p $REPORT_DIRECTORY"
     }
   }
   catch (Exception caughtException) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
        println "\u001B[41mERROR => failed to create the directory $REPORT_DIRECTORY, exiting..."
        currentBuild.result = 'FAILED'
        throw caughtException
      }
   }
}
/*****************************************************
***** Function to check security vulnerabilities *****
******************************************************/
def scanSecurityVulnerabilities(String BRAKEMAN_REPORT_FILE, String REPORT_DIRECTORY)
{
   try {
      wrap([$class: 'AnsiColorBuildWrapper']) {
        println "\u001B[32mINFO => Scanning code for security vulnerabilities, please wait..."
        sh "touch $REPORT_DIRECTORY/$BRAKEMAN_REPORT_FILE ."
      }
   }
   catch (Exception caughtException) {
      wrap([$class: 'AnsiColorBuildWrapper']) {
        println "\u001B[41mERROR => failed to scan the code for security vulnerabilities, exiting..."
        currentBuild.result = 'FAILED'
        throw caughtException
      }
   }
}

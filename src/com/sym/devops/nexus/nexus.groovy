package com.sym.devops.nexus

def DeployonNexus(String NEXUS_PATH)
{
    try {
wrap([$class: 'AnsiColorBuildWrapper']) {
          println "\u001B[32mINFO => Storing in nexus Artifactory ${NEXUS_PATH} ..."
                sh """
		   $MAVEN_HOME/bin/mvn $MAVEN_GOAL
		   """
      
    }
    catch (Exception error) {
        wrap([$class: 'AnsiColorBuildWrapper']) {
           println "\u001B[41m[ERROR] failed to store on Artifactory ${NEXUS_PATH}..."
		   currentBuild.result = 'FAILED'
           throw error
        }
    }
}



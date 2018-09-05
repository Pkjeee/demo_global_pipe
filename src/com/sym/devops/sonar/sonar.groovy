package com.sym.devops.sonar

def javaJSSonarAnalysis(String SONAR_PROPERTY)
{
    try {
      
        if ( "${SONAR_PROPERTY}" == "null" ) {
	       SONAR_PROPERTY = "sonar-project.properties"
             println " This code is running from first block of Sonar Pipeline code. The value of sonar property is ${SONAR_PROPERTY}"
	    }
	    if (fileExists("${SONAR_PROPERTY}"))
        {
            println " This code is running from second block of Sonar Pipeline code. The value of sonar property is ${SONAR_PROPERTY} "
            wrap([$class: 'AnsiColorBuildWrapper']) {
            println "\u001B[32m[INFO] running sonar analysis with file $SONAR_PROPERTY, please wait..."
             withSonarQubeEnv('Sonar_on_75') {
                sh "/var/lib/jenkins/tools/hudson.plugins.sonar.SonarRunnerInstallation/sonar/bin/sonar-scanner -Dproject.settings=${SONAR_PROPERTY}"
              }
			  currentBuild.result = 'SUCCESS'
            }
        }
        else
        {
         wrap([$class: 'AnsiColorBuildWrapper']) {
         println "\u001B[41m[ERROR] ${SONAR_PROPERTY} file does not exist..."
            }
        }
    }
    catch (Exception error) {
        wrap([$class: 'AnsiColorBuildWrapper']) {
           println "\u001B[41m[ERROR] failed to run sonar analysis using ${SONAR_PROPERTY}..."
		   currentBuild.result = 'FAILED'
           throw error
        }
    }
}

def javaJSSonarPreview(String SONAR_PROPERTY)
{
    try {
      
        if ( "${SONAR_PROPERTY}" == "null" ) {
	      SONAR_PROPERTY = "sonar-runner.properties"
	    }
	    if (fileExists("${SONAR_PROPERTY}"))
        {
            wrap([$class: 'AnsiColorBuildWrapper']) {
              println "\u001B[32m[INFO] running sonar analysis with file ${SONAR_PROPERTY}, please wait..."
              withSonarQubeEnv {
                 sh "sonar-scanner -Dsonar.analysis.mode=preview -Dsonar.dryRun=true -Dproject.settings=${SONAR_PROPERTY}"
              }
			  currentBuild.result = 'SUCCESS'
            }
        }
        else
        {
           wrap([$class: 'AnsiColorBuildWrapper']) {
              println "\u001B[41m[ERROR] ${SONAR_PROPERTY} file does not exist..."
           }
        }
    }
    catch (Exception error) {
        wrap([$class: 'AnsiColorBuildWrapper']) {
           println "\u001B[41m[ERROR] failed to run sonar analysis using ${SONAR_PROPERTY}..."
		   currentBuild.result = 'FAILED'
           throw error
        }
    }
}


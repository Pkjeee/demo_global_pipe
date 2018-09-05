/*************************************************************************
**** Description :: This groovy code is used to run the Java pipeline ****
**** Created By  :: Pramod Vishwakarma                                ****
**** Created On  :: 18/14/2018                                        ****
**** version     :: 1.0                                               ****
**************************************************************************/
import com.sym.devops.scm.*
import com.sym.devops.build.maven.*
import com.sym.devops.sonar.*
//import com.sym.devops.sonar.npm.*
//import com.sym.devops.deploy.*
//import com.sym.devops.notification.*

def call(body) 
{
   def config = [:]
   body.resolveStrategy = Closure.DELEGATE_FIRST
   body.delegate = config
   body()
   timestamps {
     try {
        currentBuild.result = "SUCCESS"
        NEXT_STAGE = "none"
        BRANCH = 'master'

          stage ('\u2776 Code Checkout') 
          {
          def g = new git()
          g.Checkout("${config.GIT_URL}","${BRANCH}","${config.GIT_CREDENTIALS}")
          NEXT_STAGE='Build'
          }
        }
        catch (Exception cE) {
        wrap([$class: 'AnsiColorBuildWrapper']) {
            print "\u001B[41mERROR => sym pipeline failed, check detailed logs..."
            currentBuild.result = "FAILURE"
            throw cE
        }
     }
     
     try {
        currentBuild.result = "SUCCESS"
        NEXT_STAGE = "none"
        BRANCH = 'master'
          stage ('\u2777 Build'){
          def b = new maven()
          b.mavenBuild("${config.MAVEN_HOME}","${config.MAVEN_GOAL}")
          }
       }
       catch (Exception caughtError) {
        wrap([$class: 'AnsiColorBuildWrapper']) {
            print "\u001B[41mERROR => sym pipeline failed, check detailed logs..."
            currentBuild.result = "FAILURE"
            throw caughtError
        }
     }
     try {
        currentBuild.result = "SUCCESS"
        NEXT_STAGE = "none"
        BRANCH = 'master'
          stage ('\u2778 Sonar Analysis'){
          def s = new sonar()
          s.javaJSSonarAnalysis("${config.SONAR_PROPERTY}")
          }
       }
       catch (Exception caughtError) {
        wrap([$class: 'AnsiColorBuildWrapper']) {
            print "\u001B[41mERROR => sym pipeline failed, check detailed logs..."
            currentBuild.result = "FAILURE"
            throw caughtError
        }
     }
   }
}


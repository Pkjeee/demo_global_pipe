/**********************************************************************
***** Description :: This Package is used to send the build email *****
***** Author      :: DevOps Team                                  *****
***** Date        :: 12/18/2017                                   *****
***** Revision    :: 1.0                                          *****
***********************************************************************/

package com.sym.devops.notification

/**************************************************
***** Function to send the Email notification *****
***************************************************/
def sendemail(String STATUS, String BODY, String RECIPIENT, String DEPLOYMENT_SERVERS)
{
  try {
    SUBJECT="${env.JOB_NAME} | $STATUS | $DEPLOYMENT_SERVERS"
    emailext body: "${BODY}", mimeType: "text/html", subject: "${SUBJECT}", to: "${RECIPIENT}"
  }
  catch (Exception caughtError) {
    wrap([$class: 'AnsiColorBuildWrapper']) {
        print "\u001B[41m[ERROR]: failed to send Email-Notification, check detailed logs..."
        currentBuild.result = "FAILURE"
        throw caughtError
    }
  }
}

/************************************************
***** Function to send the deployment email *****
*************************************************/
def sendDeployEmail(String BRAND, String DEPLOYMENT_SERVERS)
{
    try {
      wrap([$class: 'AnsiColorBuildWrapper']) {
        println "\u001B[32mINFO => sending deployment email notification, please wait..."
		String BODY="""
<HTML>
<STYLE>
  TH { background-color: GREEN; color: white; }
</STYLE>
<BODY BGCOLOR='#eafaf1'>
<PRE>
Hi,

Please find the details of the latest code deployed:

<TABLE width='300' cellspacing='0' cellpadding='0'>
<TR>
    <TH>BUILD NUMBER</TH>
    <TH>BRANCH NAME</TH>
    <TH>COMMIT ID </TH>
    <TH>DEPLOYMENT_SERVERS</TH>
    <TH>BRAND</TH>
</TR>
<TR>
    <TD>$BUILD_NUMBER</TD>
    <TD>$GIT_BRANCH</TD>
    <TD>$GIT_COMMIT</TD>
    <TD>$DEPLOYMENT_SERVERS</TD>
    <TD>$BRAND</TD>
</TR>
</TABLE>

In case of any issue please drop an email to <FONT COLOR='BLUE'><B><U>pramod.s.02@gmail.com</B></U></FONT>.

Thanks,
DevOps Team
</PRE>
</BODY>
</HTML>"""
        mail bcc: '', body: "${BODY}", cc: '', from: 'pramod.s.02@gmail.com', mimeType: 'text/html', replyTo: 'pramod.s.02@gmail.com', subject: "Build $BUILD_NUMBER Deployment Notification $DEPLOYMENT_SERVERS", to: "pramod.s.02@gmail.com,shashanktomar375@gmail.com"
	  }
    }
    catch (Exception caughtException){
	  wrap([$class: 'AnsiColorBuildWrapper']) {
        println "\u001B[41mERROR => failed to send the email for deployment, exiting..."
        currentBuild.result = 'FAILED'
        throw caughtException
      }
    }
}

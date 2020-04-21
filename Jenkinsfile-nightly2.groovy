node('edgeportals-node10') {
    notifyPulse('STARTED', 'SUCCESS', pulseNotifications)
    try {
        withEnv(['JAVA_HOME=/opt/oracle/java/jdk-11.0.3']) {
            sh 'env | sort'
            sh 'export JAVA_HOME=/opt/oracle/java/jdk-11.0.3'
            sh '$JAVA_HOME/bin/java -version'
            sh "/usr/bin/google-chrome --version"

            stage('Running Regression Tests') {
                ansiColor('xterm') {
                    try {
                        sh 'npm run e2e:reg'
                    } finally {
                        sh 'pwd'
                        sh 'mkdir test'
                        archive 'test/serverOutput.log'
                        archive 'frontend/regression/tests/reports/*.*'
                    }
                }
            }
        }

    } catch (Exception e) {        
        throw e
    }    
}

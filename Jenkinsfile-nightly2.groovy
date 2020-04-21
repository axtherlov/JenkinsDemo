properties([[$class: 'jenkins.model.BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '100']], disableConcurrentBuilds()])

def pulseNotifications = 'https://corelogic-project-monitor-production.cfapps.io/projects/70698193-1b36-4c6e-9b84-ca02ef1a9a0b/status'

node {
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

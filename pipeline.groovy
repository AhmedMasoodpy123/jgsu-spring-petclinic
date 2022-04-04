pipeline {
    agent any
    triggers { pollscm('* * * * *')}
    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git url: 'https://github.com/AhmedMasoodpy123/jgsu-spring-petclinic.git',branch: 'main'

                // Run Maven on a Unix agent.
                sh './mvnw clean package'

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
              }
           }
        }
    }
}
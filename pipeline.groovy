pipeline {
    agent any


    stages {
        stage('Checkout'){
            steps{
                // Get some code from a GitHub repository
                git branch: 'main', url: 'https://github.com/tejaswi-kommana/jgsu-spring-petclinic'
            }
        }

        stage('Build') {
            
            steps{
                sh './mvnw clean package'
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always{
                    junit '*target/surefire-reports/TEST-*.xml'
                }
                success {
                    archiveArtifacts artifacts: 'target/*.jar', followSymlinks: false
                }
            }
        }
    }
}


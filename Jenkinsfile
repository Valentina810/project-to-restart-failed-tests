pipeline {
    agent any

    environment {
        // переменная окружения для хранения пути к результатам Allure
        ALLURE_RESULTS = 'build/allure-results'
    }

    stages {
        stage('Checkout') {
            steps {
                //  клонирование репозитория из GitHub на ветке master
                git branch: 'master', url: 'https://github.com/Valentina810/project-to-restart-failed-tests'
            }
        }

        stage('Build & Test') {
            steps {
                withAllureUpload(
                    credentialsId: 'allure-credentials',
                    name: '${JOB_NAME} - #${BUILD_NUMBER}',
                    projectId: '34',
                    results: [[path: "${ALLURE_RESULTS}"]],
                    serverId: 'AllureServer',
                    ) { sh "./gradlew clean test"
                      }
            }
        }
    }

    post {
        always {
           // сгенерировать локальный отчет Allure с очисткой предыдущего
            sh './gradlew allureReport --clean'
            // заархивировать результаты тестов, чтобы их можно было скачать
            archiveArtifacts artifacts: 'build/allure-results/**', fingerprint: true
            //опубликовать отчёт в Jenkins
            allure([results: [[path: "${ALLURE_RESULTS}"]]])
        }
    }
}

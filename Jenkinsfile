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
                script {
                    // даем исполняемые права файлу gradlew (на случай, если их нет)
                    sh 'chmod +x gradlew'

                    // если тесты упадут, пайплайн продолжится, но статус будет "UNSTABLE"
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        sh './gradlew clean test'
                    }
                }
            }
        }
    }

    post {
        always {
            // генерируем отчет Allure на основе результатов тестов
            allure([
                results: [[path: "${ALLURE_RESULTS}"]],
                reportBuildPolicy: 'ALWAYS'
            ])

            // загружаем отчет в Allure TestOps
            script {
                allureTestOps([
                    projectId: '34', // ID проекта в Allure TestOps
                    serverId: 'AllureServer', // конфигурация сервера Allure
                    credentialsId: 'allure-credentials', // данные авторизации
                    results: [[path: "${ALLURE_RESULTS}"]]
                ])
            }

            // архивируем результаты Allure, даже если тесты упали
            archiveArtifacts artifacts: "${ALLURE_RESULTS}/**", allowEmptyArchive: true

            // сохраняем JUnit-отчеты, чтобы видеть статус тестов в Jenkins
            junit '**/build/test-results/**/*.xml'
        }
    }
}

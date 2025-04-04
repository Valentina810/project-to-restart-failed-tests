pipeline {
    agent any

    environment {
        // переменная окружения для хранения пути к результатам Allure
        ALLURE_RESULTS = 'build/allure-results'
    }

    stages {
        stage('Checkout') {
            steps {
                // Клонирование репозитория из GitHub на ветке master
                git branch: 'master', url: 'https://github.com/Valentina810/project-to-restart-failed-tests'
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    // даем исполняемые права файлу gradlew (на случай, если их нет)
                    sh 'chmod +x gradlew'

                    // запускаем тесты с Gradle
                    // если тесты упадут, пайплайн продолжится, но статус будет "UNSTABLE"
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        sh './gradlew clean test'
                    }
                }
            }
        }

        stage('Allure Report') {
            always {
                // генерируем отчет Allure на основе результатов тестов
                allure([
                    results: [[path: "${ALLURE_RESULTS}"]],
                    reportBuildPolicy: 'ALWAYS'
                ])
            }
        }

        stage('Upload to Allure TestOps') {
            always {
                script {
                    // загружаем отчет в Allure TestOps
                    allureTestOps([
                        projectId: '34', // ID проекта в Allure TestOps
                        serverId: 'AllureServer', // конфигурация сервера Allure
                        credentialsId: 'allure-credentials', // данные авторизации
                        results: [[path: "${ALLURE_RESULTS}"]]
                    ])
                }
            }
        }
    }

    post {
        always {
            // архивируем результаты Allure, даже если тесты упали
            archiveArtifacts artifacts: "${ALLURE_RESULTS}/**", allowEmptyArchive: true

            // сохраняем JUnit-отчеты, чтобы видеть статус тестов в Jenkins
            junit '**/build/test-results/**/*.xml'
        }
    }
}

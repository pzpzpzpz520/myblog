pipeline {
    agent any

    environment {
        DEPLOY_PATH = credentials('myblog-deploy-path')
        DEPLOY_HOST = credentials('myblog-deploy-host')
        DEPLOY_USER = credentials('myblog-deploy-user')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Frontend Verify') {
            steps {
                dir('myblog-frontend') {
                    sh 'npm ci'
                    sh 'npm run test'
                    sh 'npm run build'
                }
            }
        }

        stage('Backend Verify') {
            steps {
                dir('myblog-backend') {
                    sh './mvnw test'
                }
            }
        }

        stage('Deploy') {
            steps {
                sshagent(credentials: ['myblog-deploy-ssh']) {
                    sh '''
                      ssh -o StrictHostKeyChecking=no ${DEPLOY_USER}@${DEPLOY_HOST} "
                        cd ${DEPLOY_PATH} &&
                        git fetch origin main &&
                        git reset --hard origin/main &&
                        chmod +x scripts/deploy-prod.sh &&
                        ./scripts/deploy-prod.sh
                      "
                    '''
                }
            }
        }
    }
}

pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }
    agent {
        label 'pipeline-base-container'
    }

    stages {
        stage('Undeploy application') { steps { container(name: 'helm') { script {
            echo "Undeploying application from ${env.BRANCH_NAME}..."
            sh "helmfile -e ${env.BRANCH_NAME} destroy"
        } } } }

        stage('Undeploy ingress router') { steps { container(name: 'helm') { script {
            echo "Undeploying ingress from ${env.BRANCH_NAME}..."
            sh "kubectl -n demo-${env.BRANCH_NAME} delete ingress.yaml"
        } } } }
    }
}
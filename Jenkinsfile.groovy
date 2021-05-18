pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }
    agent {
        label 'pipeline-base-container'
    }

    // For a single pipline job the branch name has to be resolved manually.
    environment {
        BRANCH_NAME = "${env.GIT_BRANCH.split('/').size() > 1 ? env.GIT_BRANCH.split('/')[1..-1].join('/') : env.GIT_BRANCH}"
    }

    stages {
        stage('Deploy application') { steps { container(name: 'helm') { script {
            echo "Deploying application to ${env.BRANCH_NAME}..."
            echo "-> sync repositories..."
            sh "helmfile -e ${env.BRANCH_NAME} repos"
            echo "-> deploy charts..."
            sh "helmfile -e ${env.BRANCH_NAME} apply"
        } } } }

        stage('Deploy ingress router') { steps { container(name: 'helm') { script {
            echo "Deploying ingress to ${env.BRANCH_NAME}..."
            sh "kubectl -n demo-${env.BRANCH_NAME} apply ingress.yaml"
        } } } }
    }
}
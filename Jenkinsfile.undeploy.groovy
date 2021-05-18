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
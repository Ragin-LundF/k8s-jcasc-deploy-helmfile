pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }
    agent {
        label 'pipeline-base-container'
    }

    // For a single pipline job the branch name has to be resolved manually.
    environment {
        def BRANCH_NAME = scm.branches[0].name
        if (BRANCH_NAME.contains("*/")) {
            BRANCH_NAME = BRANCH_NAME.split("\\*/")[1]
        }
    }

    stages {
        stage('Deploy application') { steps { container(name: 'helm') { script {
            echo "Deploying application to ${env.BRANCH_NAME}..."
            echo "-> sync repositories..."
            sh "helmfile repos"
            echo "-> deploy charts..."
            sh "helmfile -e ${env.BRANCH_NAME} apply"
        } } } }

        stage('Deploy ingress router') { steps { container(name: 'helm') { script {
            echo "Deploying ingress to ${env.BRANCH_NAME}..."
            sh "kubectl -n demo-${env.BRANCH_NAME} apply ingress.yaml"
        } } } }
    }
}
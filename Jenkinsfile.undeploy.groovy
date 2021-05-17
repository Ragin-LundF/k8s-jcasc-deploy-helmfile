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
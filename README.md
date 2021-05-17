# Helmfile deployment #

This repository is used to show how to deploy applications with helmfile in a Kubernetes cluster.
It is part of the k8s-jcasc-app-example.

More information:
- Medium Article [Kubernetes and CI/CD — How to integrate in your development process](https://ragin.medium.com/kubernetes-and-ci-cd-how-to-integrate-in-your-development-process-9b483b194975)
- Github [k8s-JCasC-Mgmt tool](https://github.com/Ragin-LundF/k8s-jcasc-management-go)
- Github [k8s-JCasC-Mgmt example project](https://github.com/Ragin-LundF/k8s-jcasc-mgmt-example)
- Github [k8s-JCasC-app-example](https://github.com/Ragin-LundF/k8s-jcasc-app-example)
- Github [k8s-JCasC docker containers](https://github.com/Ragin-LundF/k8s-jenkins-docker)
- Github [k8s-JCasC helm charts example](https://github.com/Ragin-LundF/k8s-jcasc-app-helm-charts)
- Github [k8s-JCasC helmfile deployment example](https://github.com/Ragin-LundF/k8s-jcasc-deploy-helmfile-example)

# Structure

## /helmfile.yaml

The file for helmfile that describes the deployment.
This is a very generic one that can read the configuration from the `config` directory.
There are the definitions of the application Helm charts and the configurations.


# Deployment #

## Prerequisites

The following tools have to be installed on your system which executes the deployments:

- [kubectl](https://kubernetes.io/docs/tasks/tools/)
- [helm](https://github.com/helm/helm)
- [helmfile](https://github.com/roboll/helmfile)

## Deploy the applicaation

```bash
helmfile apply
```


This script is very generic and uses configurations from the environment to deploy each application.

Under the `/config/environment` directory you can find the configuration of the modules for the environments.

In `/config/values/{chartname}/` you can find one `values.yaml` which contains the default values and a `values.{environment}.yaml` file which contains environment specific overwrites.

The Ingress routing will be configured in the `ingress.yaml` file.

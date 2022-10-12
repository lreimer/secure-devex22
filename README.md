[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=lreimer_secure-devex22&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=lreimer_secure-devex22)

# Secure Developer Experience

Demo repository for my talk at the Heise Developer Experience 2022 conference.

## Usage

```bash
# build and run the service, or use Tilt
./gradlew assemble bootRun
tilt up

# call the service endpoints
http get localhost:8080/openapi/
http get localhost:8080/api/cves/CVE-2021-44228

http get localhost:8081/actuator
http get localhost:8081/actuator/health
```

## Google ErrorProne

Find common programming mistakes early during development as part of the Java compile phase.
See https://errorprone.info

```groovy
plugins {
    id 'java'
    id "net.ltgt.errorprone" version "2.0.2"
}

dependencies {
    // dependency for the javac compiler plugin
    errorprone "com.google.errorprone:error_prone_core:2.15.0"
}

tasks.named("compileJava").configure {
    options.errorprone.enabled = true
    // and many other options
}
```

## SonarCloud Security Analysis

Sonar can detect 54 security vulnerabilities and 38 security hotspots using static code analysis.
See https://rules.sonarsource.com/java/type/Vulnerability

```groovy
plugins {
    id "jacoco"
    id "org.sonarqube" version "3.4.0.2513"
}

jacocoTestReport {
    reports {
        xml.enabled true
    }
}

sonarqube {
  properties {
    property "sonar.projectKey", "lreimer_secure-devex22"
    property "sonar.organization", "lreimer"
    property "sonar.host.url", "https://sonarcloud.io"
  }
}
```

See https://sonarcloud.io/project/overview?id=lreimer_secure-devex22
Also, it can easily be integrated into your CI build as well as your IDE (e.g. VS Code) using SonarLint.

## Dependency Vulnerability Scanning

The compile time and runtime dependencies of your applications and services can be checked for CVEs regularly using the OWASP dependency check plugins for Gradle or Maven.

```groovy
plugins {
    id "org.owasp.dependencycheck" version "7.2.1"
}

dependencyCheck {
    cveValidForHours=24
    failOnError=true
}
```

## Docker Image Vulnerability Scanning

Several suitable tools can be used to scan your Docker images for vulnerable OS packages and
other software components.

```bash
# to manually build the Docker image use on of the following commands
./gradlew bootBuildImage
docker build -t secure-devex22:1.0.0 .

# Installation and usage instructions for Docker Lint
# https://github.com/projectatomic/dockerfile_lint
dockerfile_lint -f Dockerfile -r src/test/docker/basic_rules.yaml
dockerfile_lint -f Dockerfile -r src/test/docker/security_rules.yaml

# Installation and usage instructions for Trivy
# https://github.com/aquasecurity/trivy
trivy image -s HIGH,CRITICAL secure-devex22:1.0.0

# Installation and usage instructions for Snyk
# https://docs.snyk.io/snyk-cli/install-the-snyk-cli
snyk container test --file=Dockerfile secure-devex22:1.0.0
```

## Kubernetes Security Scanning

Many security misconfigurations are possible when deploying Kubernetes workloads.
Most can be found easily via static code analysis using different tools.

```bash
# see https://www.kubeval.com
kubeval k8s/base/microservice-deployment.yaml

# see https://github.com/yannh/kubeconform
kubeconform k8s/base/microservice-deployment.yaml

# see https://github.com/zegl/kube-score
kubectl score k8s/base/microservice-deployment.yaml

# Checkov, see https://github.com/bridgecrewio/checkov
checkov --directory k8s/base
checkov --directory k8s/overlays/int

# Snyk, see https://docs.snyk.io/snyk-cli/install-the-snyk-cli
snyk iac test k8s/base
snyk iac test k8s/overlays/int

# Trivy, see https://github.com/aquasecurity/trivy
trivy k8s -n default --report summary all
trivy k8s -n default --report all all
```

## Terraform Security Scanning

Many security misconfigurations of your cloud infrastructure are possible when working with Terraform.
Most can be found easily via static code analysis using different tools.

```bash
# TFLint und Rule Sets
# see https://github.com/terraform-linters/tflint
# see https://github.com/terraform-linters/tflint-ruleset-aws
terraform init
terraform plan
tflint

# Checkov, see https://github.com/bridgecrewio/checkov
checkov --directory aws

# Snyk, see https://docs.snyk.io/snyk-cli/install-the-snyk-cli
snyk iac test aws/
```

## Continuous Developer Experience

The linters and static analysis tools are ideally run before and with every Git commit and push.

```bash
# see https://github.com/pre-commit/pre-commit
brew install pre-commit

# see https://pre-commit.com/hooks.html
# see https://github.com/gruntwork-io/pre-commit
# see https://github.com/antonbabenko/pre-commit-terraform

# install the Git hook scripts
pre-commit install
pre-commit run --all-files
```

## Continuous Integration

GitHub and many other platforms provide CI and security integration functionality that can be used.

```bash
# see https://github.com/lreimer/secure-devex22/actions
# see https://github.com/lreimer/secure-devex22/actions/new?category=security
```

## Continuous Security Scanning

```bash
# installing the Starboard Operator and CLI
# see https://aquasecurity.github.io/starboard/
helm repo add aqua https://aquasecurity.github.io/helm-charts/
helm repo update

helm install starboard-operator aqua/starboard-operator \
  --namespace starboard-system \
  --create-namespace \
  --set="trivy.ignoreUnfixed=true" \
  --version 0.10.8

kubectl get vulnerabilityreports --all-namespaces -o wide

kubectl krew install starboard
kubectl starboard install
kubectl starboard scan vulnerabilityreports deployment.apps/nginx-deployment
kubectl starboard get vulnerabilityreports deployment/nginx-deployment -o yaml

# see https://github.com/lreimer/continuous-zapk8s
# see https://www.zaproxy.org/getting-started/
# see https://www.zaproxy.org/docs/docker/api-scan/
```

## Maintainer

M.-Leander Reimer (@lreimer), <mario-leander.reimer@qaware.de>

## License

This software is provided under the MIT open source license, read the `LICENSE`
file for details.

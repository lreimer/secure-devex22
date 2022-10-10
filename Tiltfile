# -*- mode: Python -*-
# allow_k8s_contexts('rancher-desktop')

local_resource('secure-devex22-build', './gradlew ass', dir='.', deps=['./build.gradle', './src/'], labels=['Spring'])

# to disable push with rancher desktop we need to use custom_build instead of docker_build
# docker_build('secure-devex22', '.', dockerfile='Dockerfile', only=['./Dockerfile', './build/libs/'])
custom_build('secure-devex22', 'docker build -t $EXPECTED_REF .', ['./Dockerfile', './build/libs/'], disable_push=True)

k8s_yaml(kustomize('./k8s/overlays/dev/'))
k8s_resource(workload='secure-devex22', port_forwards=[port_forward(18080, 8080, 'HTTP API'), port_forward(18081, 8081, 'Management API')], labels=['Spring'])

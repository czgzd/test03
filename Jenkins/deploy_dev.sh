#!/bin/bash
  ssh -p 3906 root@221.226.116.222 "cd ${1}/install/helm && \
    helm template app --values app/${4}.yaml > ${1}/${2}.${3}.yaml && \
    ln -sf ${1}/${2}.${3}.yaml  ${1}/${2}.yaml && \
    kubectl delete  -f    ${1}/${2}.yaml  && \
    sleep 15 && \
    kubectl apply  -f    ${1}/${2}.yaml" 

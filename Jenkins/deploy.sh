#!/bin/bash
 # 停止原来服务并删除镜像
 echo "hahhaS1"
 echo '11111$1'
  ssh -p 3906 root@221.226.116.222  "kubectl delete -f $1/$2.yaml" 
  #ssh -p 3906 root@221.226.116.222 'docker rmi -f `cat imgVerFile` '
 # 修改 k8s yaml  文件镜像版本
  sed -i "s/{{rev}}.{{tag}}/$3/g" Jenkins/$2.yaml
 # 更改 k8s yaml 文件名称
   mv Jenkins/$2.yaml   Jenkins/$2.$3.yaml                 
 # 远程传输文件以及执行脚本（端口号原因使用scp以及ssh命令，正常22请使用凭证）
 # scp  -P  3906   $  root@221.226.116.222:$1
  scp  -P  3906   Jenkins/$2.$3.yaml root@221.226.116.222:$1
  ssh -p 3906 root@221.226.116.222 "cd $1 && ln -sf $2.$3.yaml   $2.yaml" 
 # 启动服务
  ssh -p 3906 root@221.226.116.222 "kubectl apply -f $1/$2.yaml" 
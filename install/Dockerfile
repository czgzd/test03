FROM openjdk:8-jre

ARG jarname
ENV warname ${jarname}

RUN mkdir -p /opt/weblogs/   &&   chmod -R 777  /opt/weblogs/   
RUN mkdir -p /opt/webdata/   &&   chmod -R 777  /opt/webdata/   

#拷贝包文件
COPY ${jarname}  /usr/local/
       
#添加权限
RUN chmod -R 777  /usr/local/

ENTRYPOINT java -jar /usr/local/${warname}
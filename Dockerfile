# From java image, version : 8
FROM java:8

# 挂载tmp目录
VOLUME /tmp

# COPY or ADD to image
ADD netshop-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
RUN bash -c 'touch /app.jar'
EXPOSE 8085
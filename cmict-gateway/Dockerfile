FROM image.onecode.ict.cmcc:31104/library/java:8
COPY cmict-gateway-0.0.1-SNAPSHOT.jar  /
EXPOSE 8301
ENTRYPOINT [ "java" ,\
"-jar",\
"/cmict-gateway-0.0.1-SNAPSHOT.jar",\
"--spring.profiles.active=pro"]

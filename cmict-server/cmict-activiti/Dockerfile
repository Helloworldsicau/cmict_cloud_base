FROM image.onecode.ict.cmcc:31104/library/java:8
COPY cmict-activiti-1.0-SNAPSHOT.jar  /
EXPOSE 8301
ENTRYPOINT [ "java" ,\
"-jar",\
"/cmict-activiti-1.0-SNAPSHOT.jar",\
"--spring.profiles.active=pro"]

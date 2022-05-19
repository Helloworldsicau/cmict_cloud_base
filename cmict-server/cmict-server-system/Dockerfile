FROM image.onecode.ict.cmcc:31104/library/java:8
COPY cmict-server-system-1.0-SNAPSHOT.jar  /

EXPOSE 8201
ENTRYPOINT [ "java" ,\
"-jar",\
"/cmict-server-system-1.0-SNAPSHOT.jar",\
"--spring.profiles.active=pro"]

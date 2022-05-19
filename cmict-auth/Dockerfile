FROM image.onecode.ict.cmcc:31104/library/java:8
COPY cmict-auth-1.0-SNAPSHOT.jar  /
EXPOSE 8101
ENTRYPOINT [ "java" ,\
"-jar",\
"/cmict-auth-1.0-SNAPSHOT.jar",\
"--spring.profiles.active=pro"]

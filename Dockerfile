FROM maven:3.6.1-jdk-13-alpine AS build
RUN mkdir -p /workspace
WORKDIR /workspace

ENV PORT=80

COPY pom.xml .
COPY hr-nexos-ejb /workspace/hr-nexos-ejb
COPY hr-nexos-web /workspace/hr-nexos-web
COPY hr-nexos-ear /workspace/hr-nexos-ear
RUN mvn -f pom.xml clean package

FROM jboss/wildfly:24.0.0.Final
RUN /opt/jboss/wildfly/bin/add-user.sh admin secret --silent
COPY --from=build /workspace/hr-nexos-ear/target/hr-nexos-ear-1.0-SNAPSHOT.ear /opt/jboss/wildfly/standalone/deployments/
EXPOSE 80 9990 8009 $PORT
CMD /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -c standalone.xml -Djboss.http.port=$PORT
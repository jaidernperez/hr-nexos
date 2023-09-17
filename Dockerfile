FROM jboss/wildfly:24.0.0.Final
RUN /opt/jboss/wildfly/bin/add-user.sh admin secret --silent
EXPOSE 80 9990 8009 $PORT
CMD /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -c standalone.xml -Djboss.http.port=$PORT
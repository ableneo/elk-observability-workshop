# elk-observability-workshop
Materials for Observability with Elastic stack workshop

# Project structure
- frontend - java/spring-boot 2 project which simulates role of frontend - it makes calls to backend service - listens on 8302 port
  - call example: http://localhost:8302/space/launch/1200 + add Authorization header with value name:password - APM will enrich trace with such user info
- backend - java/spring-boot 2 project which acts as backend service - to simulate observation boundaries it calls public api for rocket launches - https://launchlibrary.net/ - listens on 8301 port
  - call example: http://localhost:8301/rocketlaunch/launch/
- docker-compose.yml - docker compose which starts elastic(:9200,:9300), kibana(:5601) and apm-server (:8200)
- elastic-observability.pdf - presentation



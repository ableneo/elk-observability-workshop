# elk-observability-workshop
Materials for Observability with Elastic stack workshop

# Project structure
- frontend - java/spring-boot 2 project which simulates role of frontend - it makes calls to backend service
- backend - java/spring-boot 2 project which acts as backend service - to simulate observation boundaries it calls public api for rocket launces - https://launchlibrary.net/
- docker-compose.yml - docker compose which starts elastic(:9200,:9300), kibana(:5601) and apm-server (:8200)
- elastic-observability.pdf - presentation



:: PATHS
SET MONGO_DB_PATH="C:/data/db-staticcodeanalysis"

:: mongodb
if not exist %MONGO_DB_PATH% mkdir %MONGO_DB_PATH%
start /D "C:/" mongod --dbpath %MONGO_DB_PATH%


:: support
start /D "microservices/support/discovery-server" mvn spring-boot:run -Dspring.profiles.active=local
start /D "microservices/support/edge-server" mvn spring-boot:run -Dspring.profiles.active=local

:: core
start /D "microservices/core/checkstyle-service" mvn spring-boot:run -Dspring.profiles.active=local
start /D "microservices/core/pmd-service" mvn spring-boot:run -Dspring.profiles.active=local
start /D "microservices/core/mongo-service" mvn spring-boot:run -Dspring.profiles.active=local
start /D "microservices/core/moodle-service" mvn spring-boot:run -Dspring.profiles.active=local

:: composite
start /D "microservices/composite/validator-service" mvn spring-boot:run -Dspring.profiles.active=local
start /D "microservices/composite/governance-service" mvn spring-boot:run -Dspring.profiles.active=local
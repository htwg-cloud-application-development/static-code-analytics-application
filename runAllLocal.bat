:: PATHS
SET MONGO_DB_PATH="C:/data/db-staticcodeanalysis"

:: mongodb
if not exist %MONGO_DB_PATH% mkdir %MONGO_DB_PATH%
start /D "C:/" mongod --dbpath %MONGO_DB_PATH%DOCUMENT% --port %MONGO_DB_PORT_DOCUMENT%


:: support
start /D "microservices/support/discovery-server" ./run.sh -l
start /D "microservices/support/edge-server" mvn spring-boot:run -Dspring.profiles=local

:: core
start /D "microservices/core/checkstyle-service" ./run.sh -l
start /D "microservices/core/mongo-service" ./run.sh -l

:: composite
start /D "microservices/composite/validator-service" mvn spring-boot:run -Dspring.profiles=local


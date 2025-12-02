Quick run:

cd publisher
mvn compile jib:dockerBuild



cd ./../consumer
mvn compile jib:dockerBuild

docker-compose up -d rabbit postgres

docker-compose run --rm consumer

docker-compose run --rm -it publisher



## Quick run

Requirements:  
1) **Docker** (latest as of 12.2025)  
2) **Maven** (3.6 or later or IDE bundled)  

In project root:  
```console
cd publisher
mvn compile jib:dockerBuild

cd ./../consumer
mvn compile jib:dockerBuild
```

```console
docker-compose up -d rabbit postgres
docker-compose run --rm -it publisher
```
In a **separate** console:  
```console
docker-compose run --rm consumer
```



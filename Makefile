run:
	./gradlew clean bootJar
	docker-compose down
	docker-compose build
	docker-compose up

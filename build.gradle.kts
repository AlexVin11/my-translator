plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("com.github.ben-manes.versions") version "0.49.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.vineberger"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Базовые страттеры Spring Boot
	implementation("org.bouncycastle:bcpkix-jdk15on:1.70")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.security:spring-security-config:6.2.8")
	implementation("org.springframework.security:spring-security-web")
	implementation("org.springframework.security:spring-security-config")

	// Дополнительные компоненты для удобства работы
	implementation("org.springframework.data:spring-data-rest-hal-explorer")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	implementation("io.github.cdimascio:dotenv-java:3.0.0")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")

	// Тестирование
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.assertj:assertj-core:3.26.3")
	testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.2")
	testImplementation("net.datafaker:datafaker:2.0.2")
	testImplementation("org.instancio:instancio-junit:3.3.1")
	testImplementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

	// Dev инструменты
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-logging")

	// Базы данных
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")

	// Аннотационный процессор для Lombok
	annotationProcessor("org.projectlombok:lombok")

	// Сборочные плагины
	implementation(enforcedPlatform("org.junit:junit-bom:5.10.0"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}
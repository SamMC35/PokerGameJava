plugins {
	java
	id("org.springframework.boot") version "4.0.0-M2"
	id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm")
}

group = "com.sambiswas"
version = "0.0.1-SNAPSHOT"
description = "Poker Game For Spring Boot"

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
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.springdoc:springdoc-openapi-ui:1.8.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

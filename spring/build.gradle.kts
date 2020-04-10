import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.0.M4"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("com.gorylenko.gradle-git-properties") version "2.0.0"
	id("com.google.cloud.tools.jib") version "1.8.0"
	kotlin("jvm") version "1.3.71"
	kotlin("plugin.spring") version "1.3.71"
}

group = "com.google.shinyay"
version = "latest"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

val project_id = if (hasProperty("project_id")) findProperty("project_id") as String else ""
val username= if (hasProperty("username")) findProperty("username") as String else ""
val password = if (hasProperty("password")) findProperty("password") as String else ""

jib {
	from {
		image = "shinyay/adoptopenjdk11-minimum"
	}
	to {
		image = "registry.hub.docker.com/${project_id}/spring-boot-kubernetes-gs:${version}"
//        auth.username = $username
//        auth.password = $password
	}
	container {
		jvmFlags = mutableListOf("-Xms512m", "-Xdebug")
		useCurrentTimestamp = true
	}
}
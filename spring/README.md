# Spring Boot Kubernetes Getting Started

Deploying Spring Boot Application on Kubernetes

## Description

## Demo

### Actuator
```yaml
management:
  endpoint:
    health:
      group:
        liveness:
          include: livenessProbe
  health:
    probes:
      enabled: true
  endpoints:
    web:
      exposure:
        include: '*'
```

#### LivenessProbe and ReadinessProbe
```
$ curl http://localhost:8080/actuator/health|jq .

{
  "status": "UP",
  "groups": [
    "liveness",
    "readiness"
  ]
}
```

```
$ curl http://localhost:8080/actuator/health/liveness|jq .

{
  "status": "UP"
}
```

```
$ curl http://localhost:8080/actuator/health/readiness|jq .

{
  "status": "UP"
}
```

### Graceful Shutdown
Web server will no longer permit new requests and will wait for up to the grace period for active requests to complete.
```yaml
server:
  shutdown:
    grace-period: 30s
```

### Container Build
#### Gradle Task `bootBuildImage`

- `project_id` is configured at gradle.properties

```
val project_id = if (hasProperty("project_id")) findProperty("project_id") as String else ""

tasks.getByName<BootBuildImage>("bootBuildImage") {
	builder = "cloudfoundry/cnb:bionic"
	imageName = "docker.io/${project_id}/${project.name}:${project.version}"
}
```

#### Jib

```
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
		creationTime = "USE_CURRENT_TIMESTAMP"
	}
```

##### Creation Time Configuration
```
container {
	creationTime = "USE_CURRENT_TIMESTAMP"
}
```

### Container Run
```
$ docker run --rm -d --name spring-boot-kubernetes-gs -p 8080:8080 shinyay/spring-boot-kubernetes-gs:0.0.1-SNAPSHOT
```
```
$ curl localhost:8080/actuator/health
```

### Deploy Application to Kubernetes

#### Container Push
```
$ docker push shinyay/spring-boot-kubernetes-gs:0.0.1-SNAPSHOT
```

#### Create Deployment YAML
```
$ kubectl create deployment spring-boot-kubernetes-gs --image=shinyay/spring-boot-kubernetes-gs:0.0.1-SNAPSHOT --dry-run -o=yaml > deployment.yml
```

#### Create Service YAML
```
$ echo --- >> deployment.yml
$ kubectl create service clusterip spring-boot-kubernetes-gs --tcp=8080:8080 --dry-run -o=yaml >> deployment.yaml
```

#### Deploy app to Kubernetes
```
$ kubectl apply -f deployment.yml
```

## Features

- feature:1
- feature:2

## Requirement

## Usage

## Installation

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/34c6fdd50d54aa8e23560c296424aeb61599aa71/LICENSE)

## Author

[shinyay](https://github.com/shinyay)

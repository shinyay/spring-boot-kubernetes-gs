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

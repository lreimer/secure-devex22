[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=lreimer_secure-devex22&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=lreimer_secure-devex22)

# Secure Developer Experience

Demo repository for my talk at the Heise Developer Experience 2022 conference.

## Usage

```bash
# build and run the service, or use Tilt
./gradlew assemble bootRun

# call the service endpoints
http get localhost:8080/openapi/
http get localhost:8080/api/cves/CVE-2021-44228

http get localhost:8081/actuator
http get localhost:8081/actuator/health

# to manually build the Docker image use on of the following commands
./gradlew bootBuildImage
docker build -t secure-devex22:1.0.0 .
```

## Maintainer

M.-Leander Reimer (@lreimer), <mario-leander.reimer@qaware.de>

## License

This software is provided under the MIT open source license, read the `LICENSE`
file for details.

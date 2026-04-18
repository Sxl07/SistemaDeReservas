# springboot

These rules always apply to this repository.

## Scope
- Apply these rules to every task and code change in this project.
- Prefer minimal, safe, and testable changes.

## Language and Platform
- Use Java 25 language features when they improve readability and safety.
- Build services with Spring Boot and Spring Data JPA.
- Keep code compatible with the project's Maven configuration.

## Naming Rules (Mandatory)
- Class names must be in English and use PascalCase.
- Method names must be in English and use camelCase.
- Attribute and variable names must be in English and use camelCase.
- Constants must use UPPER_SNAKE_CASE.
- Package names must be lowercase.

## Public API Documentation (Mandatory)
- Every public method must have updated Javadoc.
- Javadoc must include:
  - A concise responsibility summary.
  - `@param` for each parameter.
  - `@return` when applicable.
  - `@throws` for relevant exceptions.
- Javadoc must reflect current behavior after each change.

## DTO Rules (Mandatory)
- Use Java records for DTOs.
- DTO records should be immutable and focused on transport concerns.
- Validate input DTOs with Jakarta Validation annotations where appropriate.

## Spring Boot Service Best Practices
- Use constructor injection; avoid field injection.
- Keep controllers thin; place business logic in services.
- Use repositories only for data access responsibilities.
- Use `@ControllerAdvice` for centralized exception handling.
- Validate request payloads with `@Valid`.
- Use structured logging and avoid sensitive data in logs.

## JPA Best Practices
- Use correct JPA annotations for entities and relationships.
- Persist enums with `EnumType.STRING`.
- Define nullability and column constraints explicitly when needed.
- Do not expose entities directly in REST responses; map entities to DTOs.

## Quality and Testing
- Add or update unit tests for business logic changes.
- Add integration tests for critical flows.
- Keep tests deterministic and isolated.

## Project Structure
Maintain and evolve the project using this structure:

- reservationBackend/
- reservationBackend/pom.xml
- reservationBackend/src/main/java/com/Sxl07/reservation/
- reservationBackend/src/main/java/com/Sxl07/reservation/ReservationApplication.java
- reservationBackend/src/main/java/com/Sxl07/reservation/controller/
- reservationBackend/src/main/java/com/Sxl07/reservation/service/
- reservationBackend/src/main/java/com/Sxl07/reservation/repository/
- reservationBackend/src/main/java/com/Sxl07/reservation/entity/
- reservationBackend/src/main/java/com/Sxl07/reservation/dto/
- reservationBackend/src/main/java/com/Sxl07/reservation/mapper/
- reservationBackend/src/main/java/com/Sxl07/reservation/exception/
- reservationBackend/src/main/java/com/Sxl07/reservation/config/
- reservationBackend/src/main/resources/
- reservationBackend/src/main/resources/application.properties
- reservationBackend/src/test/java/com/Sxl07/reservation/
- reservationBackend/src/test/java/com/Sxl07/reservation/controller/
- reservationBackend/src/test/java/com/Sxl07/reservation/service/
- reservationBackend/src/test/java/com/Sxl07/reservation/repository/

## Security and Maintenance
- Never hardcode secrets or credentials.
- Use environment variables or externalized configuration for sensitive values.
- Keep dependencies updated and remove dead code.

# demo-ploep

[![CI](https://github.com/<your-username>/demo/actions/workflows/maven.yml/badge.svg)](https://github.com/<your-username>/demo/actions)

Demo project for Spring Boot (WebFlux) — simple example application.

## ⚙️ Requirements

- Java 17
- Maven (the project includes the Maven Wrapper — use `./mvnw`)

## 🚀 Quickstart

Build:
```
./mvnw -B -DskipTests package
```

Run tests:
```
./mvnw test
```

Run locally:
```
./mvnw spring-boot:run
# then visit http://localhost:8080
```

## 🧩 Project structure

- `src/main/java` — application sources
- `src/test/java` — tests
- `pom.xml` — Maven configuration

## 🛠 Development

- Make changes on a feature branch and open a Pull Request targeting `main`.
- Ensure all tests pass locally before pushing.
- Run static checks locally:

```bash
./mvnw checkstyle:check spotbugs:check pmd:check
```

CI runs the same checks automatically; fix violations locally before pushing.


## 📄 License

This project is licensed under the Apache-2.0 License — see the `LICENSE` file for details.

## ℹ️ Notes

- Please update the `pom.xml` `scm` and `url` values with your GitHub repository URL after creating the remote (e.g., `https://github.com/<your-username>/demo`).

## 📚 API documentation

This project exposes OpenAPI docs via `springdoc-openapi`. When running locally the Swagger UI is available at `/swagger-ui/index.html` (or `/swagger-ui.html` depending on version). Add the dependency `org.springdoc:springdoc-openapi-starter-webflux-ui` to `pom.xml` to enable it.

## 📊 Metrics and Observability

This application exposes Prometheus-compatible metrics through Spring Boot Actuator.

### Available Endpoints

When the application is running, the following actuator endpoints are available:

- **Health**: `http://localhost:8080/actuator/health` — Application health status
- **Info**: `http://localhost:8080/actuator/info` — Application information
- **Prometheus**: `http://localhost:8080/actuator/prometheus` — Prometheus-formatted metrics

### Scraping Metrics with Prometheus

To scrape metrics from this application, add the following job configuration to your `prometheus.yml`:

```yaml
scrape_configs:
  - job_name: 'demo-ploep'
    metrics_path: '/actuator/prometheus'
    scrape_interval: 15s
    static_configs:
      - targets: ['localhost:8080']
```

### Example Prometheus Setup

1. Download and extract Prometheus from [prometheus.io](https://prometheus.io/download/)

2. Edit the `prometheus.yml` configuration file to add the scrape configuration above

3. Start Prometheus:
   ```bash
   ./prometheus --config.file=prometheus.yml
   ```

4. Access Prometheus UI at `http://localhost:9090`

5. Query metrics such as:
   - `jvm_memory_used_bytes` — JVM memory usage
   - `http_server_requests_active_seconds` — HTTP request metrics
   - `application_started_time_seconds` — Application startup time

### Available Metrics

The application exposes standard Spring Boot and Micrometer metrics including:

- **JVM metrics**: Memory, garbage collection, threads, class loading
- **System metrics**: CPU usage, file descriptors, uptime
- **HTTP metrics**: Request counts, response times, status codes
- **Application metrics**: Startup time, readiness

For a complete list of metrics, visit the Prometheus endpoint when the application is running.

## 📤 Publish to GitHub

You can create the remote repository and push the current branch with either:

- Using GH CLI:
  ```
  gh repo create <your-username>/demo --public --source=. --remote=origin --push
  ```
- Or using the GitHub website: create a new repo named `demo`, then run:
  ```
  git remote add origin git@github.com:<your-username>/demo.git
  git push -u origin main
  ```

After pushing, enable branch protection on `main` requiring the CI `CI — Maven Build & Test` workflow to pass before merging.

---
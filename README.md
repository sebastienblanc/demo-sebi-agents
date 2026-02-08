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

## 📄 License

This project is licensed under the Apache-2.0 License — see the `LICENSE` file for details.

## ℹ️ Notes

- Please update the `pom.xml` `scm` and `url` values with your GitHub repository URL after creating the remote (e.g., `https://github.com/<your-username>/demo`).

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
# DevSync

> An AI-powered developer tool that reviews pull requests and visualizes architectural context as an interactive graph — bridging the gap between code changes and their broader impact.

[![Live](https://img.shields.io/badge/live-devsyncweb.site-2ea44f)](https://devsyncweb.site)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-6db33f)
![Microservices](https://img.shields.io/badge/architecture-microservices-blue)
![OpenAI](https://img.shields.io/badge/AI-OpenAI%20API-412991)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

DevSync hooks into your GitHub repositories via webhooks, runs every pull request through an AI review pipeline, and returns clear, natural-language feedback to your team. On top of code review, it builds an interactive architectural graph so you can see how a change ripples through the rest of the system.

---

## ✨ Features

- **AI pull-request review** — Analyzes diffs through the OpenAI API and posts natural-language feedback on each PR.
- **Architectural context graph** — Visualizes services, modules and their relationships as an interactive graph, so reviewers understand the broader impact of a change.
- **GitHub webhook integration** — Reacts to PR events automatically; no manual triggering.
- **OAuth2 authentication** — Secure sign-in and GitHub authorization.
- **Microservices architecture** — Independent, independently deployable services behind an API gateway.

---

## 🏗️ Architecture

DevSync is built as a set of Spring Boot microservices coordinated through service discovery and an API gateway:

| Service | Responsibility |
|---|---|
| `api-gateway` | Single entry point, routing and cross-cutting concerns |
| `eureka-server` | Service discovery / registry |
| `auth-service` | OAuth2 authentication and authorization |
| `git-service` | GitHub integration, webhooks and PR data |
| `analyze-service` | AI-powered pull-request analysis (OpenAI) |
| `context-graph-service` | Builds and serves the architectural context graph |

All services are containerized and orchestrated with Docker Compose.

```
GitHub  ──webhook──▶  api-gateway ──▶  git-service ──▶  analyze-service ──▶  OpenAI
                                 │                              │
                                 ▼                              ▼
                          auth-service                 context-graph-service ──▶  Frontend (graph UI)
```

---

## 🛠️ Tech Stack

**Backend:** Java, Spring Boot, Spring Cloud (Gateway, Eureka), OAuth2
**AI:** OpenAI API
**Frontend:** React, TypeScript
**Infra:** Docker, Docker Compose, Nginx (reverse proxy), GitHub Actions (CI/CD)

---

## 🚀 Getting Started

### Prerequisites
- Docker & Docker Compose
- A GitHub OAuth app (client id / secret)
- An OpenAI API key

### Run locally

```bash
# 1. Clone
git clone https://github.com/onrcanogul/devsync.git
cd devsync

# 2. Configure environment
#    Set your GitHub OAuth credentials and OpenAI API key
#    in the relevant service configuration / .env files.

# 3. Start the stack
docker compose up --build
```

The API gateway, service registry and all microservices will start together. Point your GitHub repository's webhook at the gateway's `/webhook` endpoint to begin receiving pull-request events.

---

## 📦 Project Structure

```
devsync/
├── api-gateway/            # Spring Cloud Gateway
├── eureka-server/          # Service discovery
├── auth-service/           # OAuth2 authentication
├── git-service/            # GitHub webhooks & PR data
├── analyze-service/        # AI PR analysis (OpenAI)
├── context-graph-service/  # Architectural context graph
└── docker-compose.yml
```

---

## 🗺️ Roadmap

- [ ] Support for additional Git providers (GitLab, Bitbucket)
- [ ] Configurable review rules and severity levels
- [ ] Inline suggestions committed directly to the PR
- [ ] Team analytics dashboard

---

## 👤 Author

**Onurcan Oğul** — Backend Engineer
[GitHub](https://github.com/onrcanogul) · [LinkedIn](https://linkedin.com/in/onurcanogul) · onrcanogul@gmail.com

---

## 📄 License

Released under the MIT License.

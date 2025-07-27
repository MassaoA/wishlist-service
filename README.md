# Wishlist Service - LuizaLabs Backend Test

Este projeto é uma API REST que implementa o serviço de **Wishlist** de um cliente, conforme o desafio técnico proposto pela **LuizaLabs**.

## ✅ Funcionalidades

- Adicionar produto à wishlist de um cliente
- Remover produto da wishlist
- Listar todos os produtos da wishlist
- Verificar se um produto está na wishlist
- Limite de 20 produtos por wishlist

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **MongoDB** (NoSQL)
- **MapStruct** (conversão DTO ↔ Entidade)
- **Lombok**
- **SpringDoc OpenAPI (Swagger)** 📘
- **Gradle**
- **Docker / Docker Compose**
- **JUnit 5 + Mockito**

---

## 📦 Como rodar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/wishlist-service.git
cd wishlist-service
```

2. Suba o container
```bash
docker-compose up -d
```

3. Rodar a aplicação
```bash
./gradlew bootRun
```
4. Acesse o Swagger
```bash
http://localhost:8080/swagger-ui.html
```
---

## Considerações
- Clean Architecture leve aplicada
- Uso de DTOs + MapStruct para separar modelo de domínio e interface pública
- Testes unitários e de controller com cobertura completa
- Integração com MongoDB via Spring Data
- Pronto para ser containerizado e escalado

---
## Autor
- Desenvolvido por Marcio M. Araki
- https://github.com/MassaoA

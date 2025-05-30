# Technical Test

A Java-based application designed to manage shopping carts, allowing for creation, retrieval, product addition, and deletion of carts.

## ✨ Features

- CRUD management of shopping carts
- Delete inactive carts (10 minutes)
- Custom error handling
- Input data validation
- API documentation with Swagger
- Unit tests

## 🧰 Technologies Used

- **Java 21**
- **Spring Boot 3.4.4**
- **Maven**
- **Mockito**
- **Swagger**
- **Postman**
- **Docker**

## 🚀 Run the App

**Clone the repository**:

   ```bash
   git clone https://github.com/Cerv1no/technical-test.git
   cd technical-test
   ```
**Run with Maven**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
**Run with Docker (Docker desktop needed)**
  ```bash
  docker compose up -d
  ```

## 📡 API Endpoints
The application exposes the following RESTful endpoints:

* Create a new cart\
`POST /api/v1/carts`

* Retrieve a cart by ID\
`GET /api/v1/carts/{cartId}`

* Add products to a cart\
`PUT /api/v1/carts/{cartId}/products`

* Delete a cart by ID\
`DELETE /api/v1/carts/{cartId}`

You can find all the api documentation:

http://localhost:8080/swagger-ui/index.html

## 🧪 Test the app

For detailed request and response structures, import the Postman collection:\
technical test.postman_collection.json


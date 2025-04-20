# Technical Test

A Java-based application designed to manage shopping carts, allowing for creation, retrieval, product addition, and deletion of carts.

## ✨ Features

- **Create Cart**: Initialize a new shopping cart.
- **Retrieve Cart**: Fetch details of an existing cart by its ID.
- **Add Products**: Add a list of products to a specific cart.
- **Delete Cart**: Remove a cart using its ID.

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
`POST /`

* Retrieve a cart by ID\
`GET /{cartId}`

* Add products to a cart\
`PUT /{cartId}/products`

* Delete a cart by ID\
`DELETE /{cartId}`

You can find all the api documentation:

http://localhost:8080/swagger-ui/index.html

## 🧪 Test the app

For detailed request and response structures, import the Postman collection:\
technical test.postman_collection.json


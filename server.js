const express = require("express");
const swaggerUi = require("swagger-ui-express");
const YAML = require("yamljs");
const path = require("path");

const app = express();
const port = 3000;

// Load the OpenAPI specification
const swaggerDocument = YAML.load(path.join(__dirname, "openapi.yaml"));

// Set up Swagger UI
app.use("/api-docs", swaggerUi.serve, swaggerUi.setup(swaggerDocument));

// Sample endpoint
app.get("/hello", (req, res) => {
    res.json({ message: "Hello, World!" });
});

// Start the server
app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});
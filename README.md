# Building a Maven Java Dependency Retrieval System with Python and Express.js

## Introduction

This Python-Java integration allows the client to easily resolve Maven dependencies by making a request to an Express.js API. The Python script takes Maven dependency coordinates as parameters and triggers the Java program, which acts as a Maven dependency resolver. The Java program downloads the requested dependencies to a local repository. Finally, Express.js fetches the downloaded dependency file and sends it to the Python script as a downloadable asset.

## Dependencies

- Python 3.x
- Java Development Kit (JDK)
- Maven
- Node

## Usage

1. Clone the repository.
2. Install the node dependencies in the `main` directory.

```bash
npm i
```

3. Go to the JAVA app and build the package using maven

```bash
cd '.\Java application\my-app\'
mvn clean install
mvn package
```

4. Move back to the main directory and run the node server

```bash
cd..
cd..
npm start
```

5. Go to the Python app and run the script

```bash
python script.py
```

## High-Level Architecture: Python-Java Integration for Maven Dependency Resolution

The integration involves several components that work together to resolve Maven dependencies and provide a downloadable asset. Here's how these components interact:

1. **Python Script:**
   - The Python script serves as the user's interface to the integration.
   - It takes input parameters, which are the Maven dependency coordinates (Group ID, Artifact ID, and Version).
   - The Python script sends an HTTP GET request to the Express.js API using the `requests` library.

2. **Express.js API:**
   - The Express.js API, built with Node.js and Express.js, exposes an API endpoint (e.g., `/maven`).
   - It listens for incoming GET requests from the Python script or other clients.
   - When a request is received, the API forwards the Maven coordinates to the Java program for dependency resolution.

3. **Java Program (Maven Dependency Resolver):**
   - The Java program is responsible for resolving Maven dependencies.
   - It listens for requests from the Express.js API and receives the Maven coordinates.
   - Using the Maven Dependency Resolver and Aether library, it first creates a local repository (local file system).
   - It then creates a Maven session and resolves the Maven coordinates.
   - It then downloads the specified Maven dependencies, saving them to a local repository (local file system).

4. **Express.js API (Continued):**
   - After the Java program resolves the dependencies and stores the JAR files locally, the Express.js API retrieves the downloaded JAR file from the local repository.

5. **Express.js API (Response to Python):**
   - The Express.js API sends the downloaded JAR file as a response to the Python script.
   - It sets the appropriate `Content-Disposition` header to specify the filename for the downloadable asset.

6. **Python Script (Continued):**
   - The Python script receives the JAR file as a response from the Express.js API.
   - It saves the downloaded JAR file with the specified filename to the local directory where the Python script is located.

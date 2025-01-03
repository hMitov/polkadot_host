# Polkadot REST API Server

## Description

This project is a **REST API
** server designed to interact with the Polkadot blockchain. It retrieves data via RPC calls and exposes endpoints to
access this information. This implementation uses
**Spring Boot** and the **EmeraldPay Polkaj library** for interaction with Polkadot nodes.
---

## Node Connections

This project uses both **HTTP** and **WebSocket** protocols to interact with the Polkadot blockchain.

- **HTTP Endpoint:** `https://rpc.polkadot.io`
- **WebSocket Endpoint:** `wss://polkadot.api.onfinality.io/public-ws`
  These URLs are hardcoded for simplicity but can be modified in the source code or configured via environment variables
  if required.

## Endpoints

### 1. **`GET /dot/rpc`**

- **Description:** Fetches available RPC methods from the Polkadot node.
- **RPC Call:** `rpc_methods`
- **Response Example:**
  ```json
  {
    "methods": ["method_1", "method_2", "..."]
  }
  ```

### 2. **`GET /dot/genesis`**

- **Description:** Retrieves the genesis hash of the Polkadot chain.
- **RPC Call:** `chainSpec_v1_genesisHash`
- **Response Example:**
  ```json
  "0x123abc..."
  ```

### 3. **`GET /dot/header`**

- **Description:** Retrieves and decodes the latest block header.
- **Workflow:**
    1. Fetches the latest block hash (`chain_getBlockHash`).
    2. Subscribes to block updates (`chainHead_v1_follow`).
    3. Retrieves the header for the block hash (`chainHead_v1_header`).
- **Response Example:**
  ```json
  {
    "header": "0xabc123..."
  }
  ```

## Prerequisites

Before setting up the project, ensure the following prerequisites are met:

1. **Java Development Kit (JDK):**  
   Java 11 is required to build and run the polkaj project.
2**Java Development Kit (JDK):**  
   Java 23 is required to build and run the polkadot host project.
3**Rust:**  
   The Rust programming language must be installed and configured for building Polkaj's Schnorrkel module.

- **Install Rust:**
  ```bash
  curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
  export PATH="$HOME/.cargo/bin:$PATH"
  ```

---

## Setup and usage

### PolkaJ configuration

#### 1. Clone the Repository of the PolkaJ (Polkadot Java Client)

```bash
git clone "https://github.com/splix/polkaj.git"
```

#### 2. Modify the root build.gradle file

To ensure proper dependency resolution, you need to modify the root `build.gradle` file. Follow these steps:

1. Open the `build.gradle` file located in the root of the project.
2. Add the following `repositories` block under the `buildscript` section to include the necessary repositories:

```build.gradle
buildscript {
    repositories {
        maven { url 'https://repo.grails.org/grails/core' }
        mavenCentral()
    }
    dependencies {
        // Gradle plugin for generating aggregated Javadocs
        classpath 'com.netflix.nebula:gradle-aggregate-javadocs-plugin:3.0.1'
    }
}
```

#### 3. Modify the polkaj-schnorrkel/build.gradle

To ensure proper compilation of rus, you need to modify the polkaj-schnorrkel/build.gradle file

1. Open the `build.gradle` file located in the polkaj-schnorrkel module of the project.
2. Modify the `compileRust` command to look like this:

```build.gradle
task compileRust(type:Exec) {
    workingDir 'src/rust'
    commandLine '/Users/<yourhomefolder>/.cargo/bin/cargo', 'build', '--release', '--target-dir=../../build/rust'
}
```

#### 4. Build and publish polkaj to maven local using [Java 11]
```bash
./gradlew build
./gradlew publishToMavenLocal    
```

### Polkadot host configuration

#### 2. Install Dependencies using [java 23]

Ensure all required dependencies are defined in `build.gradle` and are installed via Gradle. Run the following command
to install the dependencies:

```bash
./gradlew build
```

#### 2. Run the Server

Run the Spring Boot application:

```bash
./gradlew bootRun
```

#### 3. Test the Endpoints

Use a tool like Postman or cURL to test the endpoints:

- **Fetch RPC Methods:**
  ```bash
  curl -X GET http://localhost:8080/dot/rpc
  ```

- **Fetch Genesis Hash:**
  ```bash
  curl -X GET http://localhost:8080/dot/genesis
  ```

- **Fetch Latest Header:**
  ```bash
  curl -X GET http://localhost:8080/dot/header
  ```
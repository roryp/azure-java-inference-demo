# Basic Chat and Streaming Chat Sample

This project demonstrates two types of chat applications using Azure OpenAI services: a basic chat application and a streaming chat application. The basic chat application sends a user message to the OpenAI inference endpoint and prints the response. The streaming chat application streams responses from the OpenAI inference endpoint in real-time. These applications serve as simple examples of how to integrate Azure OpenAI inference endpoints into a Java application.

## Prerequisites

- Java Development Kit (JDK) 21 or later
- Maven
- Azure OpenAI account with an inference endpoint and API key

## Setup

1. Clone the repository:

```sh
 git clone <repository-url>
```

2. Navigate to the project directory:

```sh
 cd demo
```

3. Set up your Azure OpenAI credentials as environment variables:

```sh
 export AZURE_OPENAI_ENDPOINT="<your-endpoint>"
 export AZURE_OPENAI_API_KEY="<your-api-key>"
```

## Build and Run

1. Build the project using Maven:

```sh
 mvn clean install
```

2. Run the basic chat application:

```sh
 mvn exec:java -Dexec.mainClass="com.example.BasicChatSample"
```

3. Run the streaming chat application:

```sh
 mvn exec:java -Dexec.mainClass="com.example.BasicChatStreamSample"
```

## Usage

The application sends a predefined user message to the OpenAI model and prints the response to the console. This demonstrates how to make a request to the OpenAI model and handle its response.

## Creating an Inference on Azure

To create an inference on Azure, follow these steps:
[Azure documentation](https://learn.microsoft.com/en-us/azure/ai-foundry/model-inference/how-to/github/create-model-deployments?tabs=java).

## How the Project Uses Azure Inference

The project uses the Azure OpenAI inference by sending HTTP requests to the deployed model's endpoint. The endpoint URL and API key are set as environment variables (`AZURE_OPENAI_ENDPOINT` and `AZURE_OPENAI_API_KEY`). The application constructs a request with the user's message and sends it to the endpoint. The response from the model is then printed to the console, demonstrating the interaction with the OpenAI service.

## License

This project is licensed under the MIT License.

![Demo](demo.webp)

> To run these demos, you will need to create an inference endpoint on Azure, by following these steps:  
> [Setup](https://learn.microsoft.com/en-us/azure/ai-foundry/model-inference/how-to/github/create-model-deployments?tabs=java).
> [Models as a service  - Inferernce endpoints ](https://learn.microsoft.com/en-us/azure/ai-studio/how-to/model-catalog-overview#models-as-a-service-pay-as-you-go)

---

# Harnessing Azure Inference Endpoints in Java: Deep Insights with DeepSeek

In today’s fast-paced AI landscape, real-time inference is key to unlocking powerful applications—from chatbots to interactive systems. I’m excited to share my latest work showcased in the [azure-java-inference-demo](https://github.com/roryp/azure-java-inference-demo) repository, where I demonstrate how to integrate Azure OpenAI’s inference endpoints into Java applications. In this project, I’ve not only implemented basic and streaming chat applications but also integrated **DeepSeek**, a tool that provides visibility into the LLM’s internal chain-of-thought. 
DeepSeek-R1 excels at reasoning tasks using a step-by-step training process, such as language, scientific reasoning, and coding tasks. It features 671B total parameters with 37B active parameters, and 128k context length.

DeepSeek-R1 builds on the progress of earlier reasoning-focused models that improved performance by extending Chain-of-Thought (CoT) reasoning. DeepSeek-R1 takes things further by combining reinforcement learning (RL) with fine-tuning on carefully chosen datasets. It evolved from an earlier version, DeepSeek-R1-Zero, which relied solely on RL and showed strong reasoning skills but had issues like hard-to-read outputs and language inconsistencies. To address these limitations, DeepSeek-R1 incorporates a small amount of cold-start data and follows a refined training pipeline that blends reasoning-oriented RL with supervised fine-tuning on curated datasets, resulting in a model that achieves state-of-the-art performance on reasoning benchmarks.

---

## Getting Started

Follow these steps to run the samples:

1. **Clone the Repository**  
   Open a terminal and run:  
   ```bash
   git clone https://github.com/roryp/azure-java-inference-demo.git
   cd azure-java-inference-demo
   ```

2. **Set Up Environment Variables**  
   Ensure you have the following environment variables set:  
   ```bash
   export AZURE_OPENAI_ENDPOINT=<your_azure_openai_endpoint>
   export AZURE_OPENAI_API_KEY=<your_azure_openai_api_key>
   ```

3. **Build the Project**  
   Use Maven to build the project:  
   ```bash
   mvn clean install
   ```

4. **Run the Samples**  
   Execute the following commands to run the samples:  
   ```bash
   java -cp target/demo-1.0-SNAPSHOT.jar com.example.BasicChatSample
   java -cp target/demo-1.0-SNAPSHOT.jar com.example.BasicChatStreamSample
   ```

---

## 1. Understanding Azure Inference Endpoints

Azure inference endpoints are purpose-built to handle AI model predictions. Unlike typical REST endpoints, they are optimized for:

- **Low Latency & High Throughput:**  
  Leveraging specialized hardware (GPUs/TPUs) and optimized protocols (HTTP/2, chunked transfer encoding), these endpoints deliver rapid inference results.

- **Streaming Capabilities:**  
  Instead of waiting for an entire response, the endpoints support asynchronous streaming, allowing applications to begin processing as soon as the first token is generated—ideal for chat and real-time systems.

- **Dynamic Resource Management:**  
  Auto-scaling infrastructures ensure that inference requests are handled efficiently, even during peak loads.

- **Enhanced Security:**  
  By managing credentials via environment variables (e.g., `AZURE_OPENAI_ENDPOINT` and `AZURE_OPENAI_API_KEY`), sensitive information remains secure and separate from application logic.

---

## 2. DeepSeek: Unraveling the LLM's Chain-of-Thought

One of the standout features of this project is its integration with **DeepSeek**. Traditional inference endpoints return the final output without insight into the model’s reasoning. DeepSeek changes that by:

- **Visualizing the Chain-of-Thought:**  
  DeepSeek analyzes the intermediate processing steps of the model, effectively exposing the “thought process” behind each inference. This helps developers understand why the model arrived at a particular answer.

- **Debugging and Optimization:**  
  By revealing internal steps, you can pinpoint potential issues or biases in the model’s reasoning. This transparency is crucial for fine-tuning performance and ensuring ethical AI behavior.

- **Enhanced Interpretability:**  
  For applications requiring transparency—such as those in regulated industries—being able to inspect the LLM's internal decision-making adds an extra layer of accountability.

## Conclusion

The [azure-java-inference-demo](https://github.com/roryp/azure-java-inference-demo) repository exemplifies how to harness the power of Azure inference endpoints within a Java ecosystem. Whether through a basic synchronous request or a streaming real-time chat interface, these endpoints offer the performance and scalability needed for modern AI applications. By integrating DeepSeek, we add another layer—uncovering the LLM’s chain-of-thought to promote transparency and improve debugging.
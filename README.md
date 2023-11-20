_**Implementation of Web Server - A Comparative Study between Rust and Java**_
                                       
**Problem Statement:**

This project seeks to conduct a thorough comparative analysis of concurrent web servers implemented in Rust and Java, prioritizing assessments of performance, reliability, and user-friendliness. The outcomes of this evaluation are intended to inform future decisions pertaining to the selection of programming languages for web server development.

The exploration of utilizing Rust as a programming language for web server implementation was prompted by the principles imparted in Principles of Programming Languages (PoPL) classes. Noteworthy among these principles are:

1. The ownership system and borrow checker, which rigorously enforce rules for memory safety without relying on a garbage collector.

2. The provision of fine-grained control over concurrency, mitigating common pitfalls associated with shared mutable state.

3. Rust's reputation for emphasizing zero-cost abstractions and offering control over system resources.

4. The language's general propensity for high performance, attributed to its low-level control and assurances of memory safety.

The aforementioned attributes of Rust, brought to our notice during our classes, has instigated our exploration to ascertain whether Rust is a suitable programming language for the development of high-performance web server systems.

Uniqueness:-
While similar comparative studies might exist, our unique observations aim to provide practical insights into the application of POPL principles in the context of web server development. The specific focus on Rust, its ownership system, and concurrency control sets this study apart.

**Software Architecture:**

The architecture is a basic form of a multi threaded server, demonstrating a form of the thread per client model.The server listens for incoming client connections and delegates the handling of each client to a separate thread.
Each thread (Client Handler) performs some simulated processing, sends a response to the client, and calculates and prints the response time.

Client Connection Tracking:
  - The server tracks the start times of each client connection, allowing it to calculate response times.
  - This is a useful metric for analyzing system performance.

Simulated Processing:
  - The server simulates processing with a `Thread.sleep(1000)` to mimic real-world scenarios.
  - Simulating processing is a common practice in POPL for performance analysis.

Component Reuse:
1.The code uses standard Java libraries for networking (ServerSocket, Socket), multi threading (ExecutorService, Thread), and I/O (PrintWriter, BufferedReader).
2.The use of these standard libraries can be considered as component reuse
3.Rust implementation uses the `sysinfo` library for system information.

Developed Components:
1.The ClientHandler class and the logic within it are developed as part of this example
2.The servers main loop and client connection handling are also implemented.
3.Both implementations introduce custom logic for client handling (`ClientHandler` in Java, `handle_client` in Rust).

Testing Component:
- Both implementations include simulated processing to mimic real-world scenarios.
- Testing is performed locally during development, but scalability testing involves a varying number of client connections.

Database:
- No database is involved in the current implementations.

**PoPL Aspects:**

1. Concurrency:
   - The server uses a thread pool (`ExecutorService`) to handle multiple client connections concurrently.(Java Implementation)
   - This is a POPL aspect as it deals with the parallel execution of tasks.
   - The server handles each client connection in a separate thread using `thread::spawn`.(Rust implementation)
   - Concurrency is a significant POPL aspect for improving system responsiveness


2. Synchronization:  
- Synchronization is applied when printing the response time for each client.
- This ensures that the output is consistent when multiple threads are accessing shared resources.
- Proper synchronization is a key POPL concept to avoid data race conditions.

3. Exception Handling:
   - Exception handling is implemented for input/output and thread-related exceptions.
   - Proper exception handling is a good practice in POPL, ensuring the robustness of the system.

4. Concurrency Control (Java):
   - [Java Code: Lines 24-33]
   - Explanation: The `ExecutorService` is created with a fixed-size thread pool using `Executors.newFixedThreadPool(10)`. This pool can handle up to 10 concurrent tasks. Each incoming client connection is accepted in the main loop (`while (true)`) and a new `ClientHandler` is created and submitted to the thread pool using `executor.execute(new ClientHandler(clientSocket))`. This allows multiple client requests to be processed concurrently.

5. Ownership System (Rust):
- Rust Code (in the server.rs file):- the `get_memory_info` function returns a formatted string, and ownership of that string is transferred to the `memory_info` variable when it's assigned in the `handle_client` function. The ownership of `memory_info` then goes out of scope at the end of the `handle_client` function, and Rust's ownership system ensures proper memory management.
   - Explanation: Demonstrates Rust's ownership system in memory management

6. Memory Utilization (Rust):
- Rust Code (in the server.rs file):- `let memory = system.total_memory();` calculates the total memory, contributing to the memory utilization information.
`let free_memory = system.free_memory();` calculates the free memory, contributing to the memory utilization information.
These lines are part of the `get_memory_info` function and are responsible for gathering memory-related information for the subsequent memory utilization report.
   - Explanation: Monitors memory usage for each client connection.

7. Error Handling (Java):
   - [Java Code: Lines 72-74]
   - Explanation: Implements proper exception handling for robustness.


Difficulties Faced:

1. Java Implementation Advantage:
   - The Java code was easier to implement because several team members had taken a Network Programming course last semester, providing familiarity with the language and its libraries.

2. Rust Learning Curve:
   - Rust posed challenges due to its newer concept for the team. The learning curve was steeper compared to Java.

3. Variable Drop in Rust:
   - Rust's automatic dropping of variables when they go out of scope posed a challenge. Strategies had to be devised to handle this by keeping other functions to return variables separately.

4. Security Considerations in Rust:
   - Rust is not secure by default, necessitating extra efforts to make variables and functions public only as needed.

5. Handling Mutex Data Types:
   - Dealing with mutex data types in Rust was difficult. Understanding and implementing proper synchronization using mutexes added complexity to the code.

These difficulties highlight the team's learning journey with Rust and the intricacies involved in adapting to its ownership system and concurrency control.


**Results:**

Tests Conducted:
- Performance tests with varying numbers of clients (1, 3, 5, 10, 100, 500, 1000).
- Response time and memory utilization metrics collected.

Graphs:
- Line graphs illustrating average response time and average free memory were plotted for both Rust and Java web servers (Given in the Github repository).

Validation:
- Results align with the initial problem statement by showcasing the impact of client load on response time and memory utilization.
- Data-driven proof points demonstrate system behavior under different scenarios.

Convincing Evidence:
- The data highlights the trade-offs between Rust and Java in terms of performance and resource utilization.
- The crash of Rust servers beyond 500 clients indicates system limitations.


Potential for future work:

- Performance Optimization:
  - Explore ways to optimize the server's performance further, considering factors like thread pool size, processing time, and resource utilization.

- Protocol Enhancements:
  - Consider enhancing the communication protocol between the client and server for more complex interactions.

- Security Considerations:
  - Address security aspects, such as input validation, to ensure the server is robust against malicious inputs.

- Extended POPL Exploration:
  - Investigate deeper into Rust's ownership system and concurrency control for advanced application scenarios.





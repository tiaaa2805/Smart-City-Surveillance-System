Smart City Surveillance System
1. System Components and Roles
   The system is built on an Event-Driven Architecture (EDA) using a Publish-Subscribe model. It consists of:

Publishers: Speed Cameras (detect speeding), Traffic Cameras (monitor congestion), and Public Cameras (detect littering).

Subscribers: Traffic Police (issue speeding fines), Local Police (issue littering fines), Navigation Apps (update traffic maps), and the City Dashboard (tracks statistics and dangerous areas).

2. Data and Events
   Each event carries specific data:

Speeding Event: Car ID, speed, and neighborhood.

Traffic Event: Street name and congestion level.

Garbage Event: Person ID and neighborhood.

3. Technical Implementation
   The simulation is implemented in Java using an in-process event bus for synchronous delivery. To ensure realism, each camera operates as a Publisher running in its own Thread (concurrency), allowing multiple events to be detected and processed simultaneously across the city.

4. Speeding Incident Logic
   When a speed camera detects a violation, it creates a SpeedingEvent and posts it to the Event Bus. The Bus immediately notifies all registered subscribers: the Traffic Police to record the fine and the City Dashboard to update the neighborhood's safety metrics.

In order to make the simulation more realistic, all surveillance cameras work concurrently, each running in its own thread.

In addition to the in-process version of the system, implement a second version, having all participants running in separate processes and use a messaging infrastructure for distributed Publish-Subscribe communication.

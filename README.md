Spring Boot based client/server application.
with  H2 persistence layer.

Application provides REST Api to consuming and updating a sensor state.
The REST Api can be used by mobile clients to initiate a change of sensor state. 
When sensor changes its internal state then the sensor notifies this application by the provided REST Api, 
the application sends push notifications to registered clients.   

![diagram2](https://user-images.githubusercontent.com/7153849/114751172-b71ae300-9d4c-11eb-9cdd-4672a9f126c8.png)

### UML Diagram
![diagram1](https://user-images.githubusercontent.com/7153849/114751177-b8e4a680-9d4c-11eb-90bc-9236710b7719.jpg)

### Sequence Diagram
![diagram2](https://user-images.githubusercontent.com/7153849/114751172-b71ae300-9d4c-11eb-9cdd-4672a9f126c8.png)

![diagram3](https://user-images.githubusercontent.com/7153849/114751178-b97d3d00-9d4c-11eb-9938-bdeb15e016c0.png)

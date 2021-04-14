Spring Boot based client/server application.
with  H2 persistence layer.

Application provides REST Api to consuming and updating a sensor state.
The REST Api can be used by mobile clients to initiate a change of sensor state. 
When sensor changes its internal state then the sensor notifies this application by the provided REST Api, 
the application sends push notifications to registered clients.   

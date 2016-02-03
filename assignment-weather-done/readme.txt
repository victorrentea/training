1) The computation of the radius histogram was incorrect. As far as I could tell, the point was to report the distribution of the radius requested as a histogram.
The developer used % there where I believe it actually meant DIV (/). 
This means a chart with ranges of km on the horizontal axis and hit count on vertical. 
For example, if two requests were for 501 KM and 502 KM, if you have intervals of 10 KM, then in the interval [500..510) you would have a hit count of 2.
- Having intervals only 1 KM in size is less useful, as the radiuses will almost never overlap to sum up. So you'll only see recurring radiuses, basically. A lot more useful would be to have ranges a bit broader (e.g. 10 km).

That's what I implemented: the returned array shows the hit count for radiuses in intervals of 10 km. 
For example: all the radiuses between [0..10) will be reported in hist[0], [10-20) will be hist[1], ... and so on

Although the functionality was incorrect, chances are that no one noticed it because probably the reporting functionality was scarcely used ->thus is safe to fix.
Moreover, the data is for human interpretation, as a javadoc explains. 
Therefore, it's almost impossible that an external system will fail in some way after this fix in the logic.


2) I left the REST API as it was, in terms of URI scheme and JSON contents of the responses and expected requests, in order not to break any existing clients.

3) Had to tweek the lifecycle management in WeatherServer.MyApplicationBinder, so that the Repository is singleton
4) The default "0" value for radius in the REST Endpoint implem is incorrect. According to JAX-RS, the way the @Path was written required for the request URI to carry a radius !

5)GSON is useless. Can use Jackson already in Glassfish JAX-RS implem (Jersey). Removed gson maven dependency

6) Added generic exception interceptor for REST endpoints --> no mode log.error in code!

7) You won't encounter comments in code: please let us discuss this point in the call we'll have.

8) Fixed bug: RestEndpointCollectorEndpoint.java:217         ad.setLatitude(latitude);        ad.setLatitude(longitude);


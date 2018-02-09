# ArcAngel- tamuHACK ESRI sponser challange winner 

We were inspired to create this mobile and web application after the recent effects of Hurrican Harvey. During the event, many were in need of medical attention and relocation services. When people tried to call 9-1-1 they would be left with unanswered calls because the lines were backed up. One of the main things we noticed during this time was that people began to resort to twitter to post their current situation along with their location and that they were in need of help. Becuase people were able to retweet their tweets,  volunteers and EMT were able to assist these families in need. 

## How it works 

In the event of a natural disaster or catastrophic event, users can go to their twitter accounts and tweet their current situation and that they are in need of help. Our program will then filter tweets in that area that indicate a person is in need and will place their location on our map interface. The map will present the information in the form of a heat map in which volunteers, paramedics, or EMT can log onto our application and find the location where they can help the most people at once. Our web application also indicates the locations of the nearest hospitals that they can then be sent to. 

## Application Stack 

The mobile app was build for Android devices using JAVA, Android Studio, and the ESRI API interface to display our data in real time. 
The data was extracted from the Twitter API and Hospitals API using Matlab to parse and manipulate the data. 
The ArcGIS platform was build to create our map interface and use our API's to display real-time locations and updates. 

## Challenges we ran into 

We had difficulty getting real-time twitter information into the ESRI cloud where all our data is stored. We initially used R to parse our data but then had to use Matlab instead.  
We also had to learn how to use the ESRI cloud services and gis features. 

## Whats next for our product

With further implementation we would want to have the hospital's display information regarding the current capacity of their hospital and if they had available spots for new patients. This would cut the time down for EMT and Paramedics as our application would suggest the fastest route to hospitals that weren't at full capacity. 


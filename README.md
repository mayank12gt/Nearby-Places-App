# Nearby-Places-App
Nearby Places is an android app that allows users to view nearby places of interest according to user's location and their details. The user can also apply filters for search radius and establishment type to get refined results.<br>
Implemented Location Permission Access, marked user location on the map, marked nearby areas of interest on the map, implemented bottom sheet to display basic information about nearby places, get Directions on Google Maps for a place, followed MVVM architecture, used Google Maps SDK and Places Api. <br>


## Demo Video



https://github.com/mayank12gt/Nearby-Places-App/assets/96809211/e0fcd5d9-4895-4715-aa49-5eb62484d674


## ScreenShots:

![merge_from_ofoct (4)](https://github.com/mayank12gt/Nearby-Places-App/assets/96809211/5deccbf1-6981-4b8a-8879-c8982a24cf9c)




## Features
 1.	Location Access: The app asks for user location at runtime using a dialog box. Only when the location access is provided, the app performs any operation.

2.	User Location and nearby places: User location and nearby places of interest are marked on the map screen. Initially 20 results are shown, the search radius is 1 Km, and there is no filter applied for the place type. The “SHOW MORE PLACES” button fetches more places for the current filter.

3.	Filter Screen:<br> 
a.	Filters can be applied to the search using the FILTER button which displays a Filter Screen implemented as an alert dialog box.<br> 
b.	It has a spinner for selecting establishment type from over 12 categories (Hospital, School etc.). Initially it is empty. It also has a slider for selecting search radius between 1Km to 5 Km. Initially the slider is set at 1 Km.<br> 
c.	There is an APPLY button which applies the filters on the search results and shows relevant places on the map screen.<br> 
d.	The RESET button can be used to reset the filter values to their default values.<br> 

4.	Place Details:<br> 
a.	Tapping the marker of any nearby place on the map opens the Details Screen implemented as a Bottom Sheet. It specifies the name of the place, its rating displayed as stars along with the number of ratings.<br> 
b.	The “Get Directions” button launches the navigation screen in Google Maps app from the user’s location to the place.<br> 
c.	The pictures of the place are displayed in an automatic image slider. The full address, phone number and website of the place are also displayed.<br> 
d.	Clicking the website or phone number launches the website in the browser and the phone number in the default Contacts app respectively.<br> 






















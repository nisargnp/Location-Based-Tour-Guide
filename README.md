# Location-Based-Tour-Guide

a.k.a. Tourodactyl (pTOURodactyl?)

[Demo Video](https://www.youtube.com/watch?v=kX6Sl0rBEI8)

System and framework where an author can create tours for a set of locations. Allows users to link audio and video content to each stop on a tour. Users use the app to follow the tour and to automatically view linked content when they are arrive at a particular location on the tour. Users could then go to these places and see the information.

## Development Notes

### Firebase account
- Need a firebase account with proper schema set up, and access credentials linked to the project

### Google Maps API

- Shared Maps API key stored in `app/src/debug/res/values/google_maps_api.xml` and `app/src/release/res/values/google_maps_api.xml`
- May need to enable Google Play Services SDK to work: 
Tools > SDK Manager > SDK Tools > Check `Google Play Services` > Apply > Resync + Rebuild Project

### Google Directions API

- Shared Directions API key stored as a static variable in the `DirectionsTaskParameter` class.
- `DirectionsAsyncTask` will query the Google Directions API, given a `DirectionsTaskParameter` object which will have a Map, and Lat/Lng Locations representing Tour Stops. The `DirectionsAsyncTask` will use the Directions API to map an route between all these stops, and draw the route on the given Map.
- Unfortunetly, this API costs $$$. `$0.005` per request. We have `$100` credit right now, so max <b>20,000 requests</b>. Test this sparingly.

### Location Services API

- If using an AVD emulator, on emulator start up, you need to set your location Lat/Lng. From the emulator screen's side bar: `...` > `Location` > Enter your Lat/Lng.

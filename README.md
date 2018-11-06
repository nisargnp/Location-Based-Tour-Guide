# Location-Based-Tour-Guide

a.k.a. Tourodactyl (pTOURodactyl?)

## Development Notes

### Google Maps API

- Shared Maps API key stored in `app/src/debug/res/values/google_maps_api.xml` and `app/src/release/res/values/google_maps_api.xml`
- May need to enable Google Play Services SDK to work: 
Tools > SDK Manager > SDK Tools > Check `Google Play Services` > Apply > Resync + Rebuild Project

### Google Directions API

- Shared Directions API key stored as a static variable in the `DirectionsTaskParameter` class.
- `DirectionsAsyncTask` will query the Google Directions API, given a `DirectionsTaskParameter` object which will have a Map, and Lat/Lng Locations representing Tour Stops. The `DirectionsAsyncTask` will use the Directions API to map an route between all these stops, and draw the route on the given Map.
- Unfortunetly, this API costs $$$. `$0.005` per request. We have `$100` credit right now, so max <b>20,000 requests</b>. Test this sparingly.

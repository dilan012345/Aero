Aero

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white">
  <img src="https://img.shields.io/badge/Kotlin-2.2.10-7F52FF?logo=kotlin&logoColor=white">
  <img src="https://img.shields.io/badge/Jetpack-Compose-4285F4">
</p>

<p align="center">
 A centralised app connecting:
- Samsung health
- Google fit / Google Health
- Strava

</p>



**readme update forthcoming**

Current stage:
Redeveloped UI (see screenshots)
Permission requests for:
Steps
Calories
(via health connect)

Samsung Health does not write data to health connect regarding:
"Total calories burned, distance, power, speed, and VO2max"

So relying on Samsung health SDK directly to request calorie data is necessary:
  Status: Approved for use, using debug SHA-256 and SHealth developer mode
  Initial release planned: 1st September
  Package name to be changed


Architecture

Acknowledgements

Aero makes use of the following dependencies:
Koin (Dependency injection)
Material 3 Components
Voyager (Navigation)



## Screenshots
  <p align="center">
  <img src="https://github.com/user-attachments/assets/b7a07ee2-a12f-455d-b59b-c9b55570589c" width="250" alt="Home Screen"/>
  <img src="https://github.com/user-attachments/assets/bfe25060-095f-4f98-8249-c0f026234fe7" width="250" alt="Dashboard"/>
</p>

# CST 438 Project 02: MealPlan and Recipe (ANDROID APP REPO)

[API Repository](https://github.com/Solian21/CST438_P2_API)

Our Android App made it so that our users are able to sign in with Google using OAtuh2. The users were then able to view and create recipes, manage ingredients, and create personalized weekly meal plans. Our Administrator accounts were able to manage user roles and delete users through an admin panel.

## Our Team

#### DOYOUNG YANG - Yooze0x4c8

**Worked on**

- Helped set up the Android application
- Helped connect the Android application to the REST API
- Helped develop the recipe and ingredient functionality
- Helped test application features and API integration

#### Lesly Villanueva - leslyVillanueva1020

**Worked on**

- Helped develop the user, ingredient, and recipe functionality
- Helped create the data models used to communicate with the API
- Helped implement recipe and ingredient screens logic
- Helped test recipe creation and ingredient selection

#### Carlos Solian - Solian21

[Issues](https://github.com/Solian21/CST438_P2_Andrioid_App/issues?q=is%3Aissue+state%3Aclosed+assignee%3ASolian21)

[Pull Requests](https://github.com/Solian21/CST438_P2_Andrioid_App/pulls?q=is%3Apr+state%3Aclosed+author%3ASolian21)

**Worked on**

- Built the Android application interface using Jetpack Compose
- Created the login screen and Google OAuth sign in flow
- Designed the home screen and navigation menu
- Added the recipe screens, recipe details, and recipe creation functionality
- Added the meal plan screens and navigation
- Connected the Android application to the REST API

#### Annabelle Baltes - annabelleb20

**Worked on**

- Helped develop the meal plan screens logic
- Added meal plan creation, updating, viewing, and deletion
- Helped implement the admin user panel logic
- Added user role management and user deletion functionality
- Helped test authentication and user account features

## Application Features

- Google OAuth login
- User account management
- Create and view recipes
- Add ingredients to recipes
- Browse available ingredients
- Create weekly meal plans
- View meal plan details
- Update and delete meal plans
- Admin panel for managing users
- Change user roles between User and Admin
- Delete user accounts
- REST API integration using Retrofit

## How To Set Up Locally

Clone the repository and open the `Andrioid App` folder in Android Studio.

Make sure Android Studio has the required Android SDK installed. The application uses:

- Android SDK 37
- Minimum Android SDK 24
- Java 11
- Kotlin and Jetpack Compose

The application is already configured to use the deployed API:

[API URL](https://cst438-p2-api.onrender.com/)

To build the application from the terminal, make sure you are in the `Andrioid App` directory and run:

```bash
./gradlew build
# Welcome to Neorama 

![Welcome image](https://github.com/Vierco/Neorama/blob/main/app/src/main/res/drawable/introducing.png)


### Last update 07/07/2023

Hi!

Neorama is an Android application that combines my passion for programming and astronomy. It serves several important purposes:

1.  **Testing new features:** This project serves as a laboratory where I can experiment with the latest features and functionalities of the Android operating system, as well as the updates and enhancements of the programming language.

2.  **Work showcase:** This application serves as a demonstration of my work and mobile app development skills. Through this project, I can showcase my design approach, code structure, and ability to integrate third-party technologies.

3.  **Astronomical exploration:** The application focuses on astronomy and provides information about near-Earth objects on the current and previous dates. It utilizes NASA's open APIs to obtain accurate and up-to-date data. These objects are graphically listed and plotted on a coordinate map, allowing users to visually explore space.

_Currently, the project is in an early stage of development so some functions are still WIP tagged._

## How This Project Is Built

In developing this project, I have followed several principles and employed various technologies to ensure a robust and maintainable codebase. Here are some of the key components and methodologies used:

-   **SOLID Principles:** The project adheres to the SOLID principles, which promote clean and modular code design. By following these principles, the codebase becomes more flexible, easier to understand, and less prone to errors.
    
-   **Dependency Injection:** I have implemented dependency injection to improve code reusability and maintainability. By decoupling dependencies and injecting them into classes, we achieve better modularity and testability.
    
-   **Testing:** Testing is an integral part of the development process. I have incorporated unit tests and integration tests, to ensure the reliability and stability of the application. This helps catch bugs early on and facilitates future enhancements.
    
-   **Version Catalog for Dependencies:** To manage dependencies effectively, I have utilized a version catalog approach. This allows for centralized version management, ensuring consistency and simplifying dependency updates.
    
-   **MVVM (Model-View-ViewModel):** The project follows the MVVM architectural pattern, separating the user interface (View) from the underlying data (Model) and using ViewModels as an intermediary layer. This promotes separation of concerns and enhances testability.
    
-   **Flows:** I have leveraged Flows, a reactive stream-based approach introduced in Kotlin, to handle asynchronous data streams in a more streamlined manner. Flows enable efficient handling of data updates and improve responsiveness.
    
-   **DataBinding (Considering alternatives):** DataBinding has been utilized in this project to establish a connection between UI components and data models. It simplifies UI updates and reduces boilerplate code. However, it's worth noting that as of today, there are newer and more powerful alternatives available in the Android ecosystem. I acknowledge that exploring and adopting these alternatives is on my to-do list for future improvements.
    
-   **Room Database:** For efficient and persistent data storage, I have employed the Room database, a powerful ORM (Object-Relational Mapping) library provided by Android. Room simplifies data access and management, enhancing the overall performance of the application.
    
-   **Clean Architecture with Layer Separation:** Following the principles of Clean Architecture, the project is structured into distinct layers, such as presentation, domain, and data. This separation enables a clear and scalable codebase, with each layer having well-defined responsibilities.
    
-   **Navigation:** The project utilizes the Navigation component provided by the Android Jetpack library. Navigation simplifies the implementation of in-app navigation flows, ensuring a smooth and intuitive user experience.
    
-   **(And many more...):** In addition to the mentioned technologies and methodologies, there are various other tools and frameworks that have been employed to enhance the project. These include libraries for network communication, logging, error handling, and performance optimization, among others.
    

By employing these technologies and adhering to best practices, this project is being built to deliver a high-quality, maintainable, and enjoyable user experience.


## Planned Features (Based on Available Free Time)

As I embark on this project, there are several planned features that I intend to work on in the future, based on the available time and resources. These additions will further enhance the functionality and user experience of the application. Here are the planned features by now:

### Specific Enhancements:

-   **Automatic Database Update every 24 hours:** Implementing an automatic update mechanism for the database will ensure that the application always presents users with the most up-to-date information. This feature will enhance the accuracy and relevance of the data.
    
-   **Review Connection Error Handling:** I plan to revisit the current handling of connection errors. This will enhance the user experience by offering clearer guidance when there are connectivity issues.
    
-   **Enhance User Interface and Experience:** I will focus on refining the user interface and experience to make the application more visually appealing, intuitive, and user-friendly. This includes improving layout designs, optimizing navigation flows, and enhancing the overall aesthetics.
    
-   **Multilanguage Support (currently English only)**
    
-   **Dark Mode:** Users will have the option to switch between light and dark themes.
    
-   **Review Race Conditions:** I will thoroughly review and address any potential race conditions that may exist within the application. By resolving these issues, we can ensure a smoother and more reliable user experience.
    
-   **And many more exciting features!** There are countless other ideas and improvements that I plan to explore and implement as the project progresses. The goal is to continuously enhance the application and provide an exceptional experience for all users.
    

Please note that the implementation of these features is subject to prioritization and available resources

## Resources

Nasa Open Apis: https://api.nasa.gov

Neos example response: https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=**DEMO_KEY

**API Key Usage Notice**

This application uses a demo API key (DEMO_KEY) to access the necessary APIs for retrieving data. However, please be aware that the demo API key may have certain restrictions, such as rate limits or limited access.

-   Hourly Limit: 30 requests per IP address per hour
-   Daily Limit: 50 requests per IP address per day

For a seamless and optimal experience, it is highly recommended to obtain a personal API key. By acquiring your own API key, you can enjoy unrestricted access to the API services and unlock the full potential of the application.

To obtain a personal API key, please refer to the documentation or website of the API provider [HERE](https://api.nasa.gov).

**Thank you for visiting this project!** If you have any questions or suggestions, feel free to contact me. I hope this application provides an interesting and educational way to explore the fascinating world of astronomy.

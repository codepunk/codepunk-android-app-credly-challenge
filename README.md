# Scott Slater's Credly Challenge

This is an app that displays a list of (some of my favorite) shows. The user can click on any show to see a detail page about the given show. The detail page contains both a list of episodes and a cast list.

## Libraries used

This challenge app makes use of the following libraries:

- Android Lifecycle
- ViewPager2
- Hilt dependency injection
- Kotlin coroutines
- Retrofit
- Kotlin serialization
- Jetpack Navigation
- Room
- Coil image downloader

## Goals

I had several goals in mind when creating this app:

First and foremost, I wanted to make use of clean architecture. As such, you will notice packages for `data`, `domain` and `presentation`. `domain` contains implementation-independent interfaces and classes for the application's data models, data repository methods, and use cases. The `data` package contains implementations of the above, as well as remote and local versions of the data models found in `domain`. For a simple app like this, it might seem like overkill to have remote, local and domain versions of data models that contain more similarities than differences, but the benefits become apparent for more complex models (like `CastEntry` for example). Finally, the `presentation` package contains the `Fragment`s and `ViewModel`s needed to present the data in a way that makes sense for the user.

Another goal was to make use of dependency injection (in this case **Hilt**) to keep the code more manageable as well as to aid in testing.

I made extensive use of kotlin **Flow**s to handle data and allow for fragments to observe changes in data and state (loading status and errors, for example).

I challenged myself in the show detail screen by including a tab control with two sets of data: Episodes and Cast. Additionally, I added the nice touch of a collapsing header containing the primary show image, which collapses as you scroll down through the data.

Related to clean architecture, I make use of a "**S**ingle **S**ource **O**f **T**ruth" when it comes to data. All data is saved to a local database and that database is used as the SSOT. Once the list of shows has been downloaded as well as a show's episodes and cast lists, for example, that data should be readily available the next time you open the app, even if there is no internet connectivity. (There may be unpredictable results when it comes to images, however, as I didn't spend any time managing Coil's approach to storing/purging image data.)

## Thoughts

Here are a few things I was aware of while creating this app. Call them thoughts, or areas for improvement.

1. Normally I am a proponent of multi-module architecture, in which each feature can be found in its own module, with its own `data`, `domain` and `presentation` packages. However, such an architecture would have been overkill for this project so there is a single app module containing the entire application.
2. The build.gradle.kts file is not optimized to centralize library names and versions. Normally I would maintain a central TOML file containing build information.
3. The various `Fragment`s and `ViewModel`s contain a fair amount of repeated code. Normally I would put this code in an ancestor class or perhaps an Extensions file, but in a challenge app such as this I find it's more helpful that such code isn't "hidden" away but is all present, which makes for an easier read-through.
4. I believe new apps should make use of Jetpack Compose. However, as I am still very new to Compose, in the interest of time I used the more traditional XML view approach.
5. There is some very rudimentary error handling. Errors are indeed caught but in order to save time, a simple Toast with a generic error message is displayed. (If you turn on airplane mode and attempt to click into a show you haven't yet visited, for example, you'll see such an error message appear for a second or two.)
6. In a list like the episodes list, I would normally break that list up by season and add headers for each season. This is not only an Android 101 task but actually a bit time-consuming, so I left it out for now, opting for displaying the season and episode numbers in each episode in the list.

# Thank you
Thanks for a fun and challenging idea for a code challenge! I hope you enjoyed playing around with it.

Scott Slater
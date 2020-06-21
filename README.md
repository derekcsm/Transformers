# Transformers
## A Demo project by @derekcsm

Hey there review team! Please consider this project a reasonable approximation of my skills following a mix of new patterns and some old-school K.I.S.S. methodology. 

Given that I'm quite busy these days, and that I'm confident you'll see my qualifications from this project, I felt it was best to just focus on the *core* of the application, and not build the fighting feature - I hope you can understand. Doing this gave me more time to refactor and consider things as I normally would while working.

### Technologies/libraries used:

- MVVM + Repository Architecture
- Kotlin
- Hilt (Dagger)
- Okhttp3 + Retrofit2
- Coroutines
- AndroidX Lifecycle
- Gson
- Room DB
- Stetho for debugging
- Espresso (using robot builder pattern)

### Features:

- Automatic authorization system using OkHttp Authenticator + RequestInterceptor (that could easily be adapter to automatically refresh an expired token)
- Create a Transformer
- Edit existing Transformers
- Delete Transformers from the list directly
- Pull to refresh transformers list, with a zero state implementation
- Request and network error handling throughout

Looking forward to talking with you :) - Derek

## Recycler ELE

We often use the RecyclerView and the RecyclerView.Adapter for our material design apps. Going from the ListView to RecyclerView, I really missed the `setEmptyView()` method.

So, I extended the original RecyclerView Adapter to help manage different states in the application.
The Adapter, apart from the standard list, supports three different views:
* Loading View (While Data is being fetched)
* Empty View (When there is not data)
* Error View (In case of an error while fetching data)

This way, `RecyclerELE` helps us give different output to user depending on the current state of data, without having to mess a lot with the code.

#### Installation

The installation is pretty easy.

In your project's `build.gradle` at the end of the repositories, add:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

In your app module's `build.gradle` add dependency:
```
    compile 'com.github.the-cybersapien:RecyclerELE:-SNAPSHOT'
```

I'll try and shift this to jCentral, that'll remove the need to add jitpack.io to maven. Until then, bear with me please.


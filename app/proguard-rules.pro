# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Firebase Firestore
-keepattributes Signature,RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations
-keepattributes *Annotation*
-keep class com.google.firebase.** { *; }
-keep class com.google.firestore.** { *; }

# Crashlytics
-keep class com.google.firebase.crashlytics.** { *; }


-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Keep all Retrofit interfaces and their method signatures
-keep interface com.satya.smartmealplanner.data.remote.** { *; }

# Keep model classes used in Retrofit/Gson
-keep class com.satya.smartmealplanner.domain.model.** { *; }
-keep class com.satya.smartmealplanner.data.model.** { *; }

# Gson specific: keep fields annotated with @SerializedName
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# --- Keep Gson TypeToken helper ---
-keep class com.google.gson.reflect.TypeToken
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep all model classes and their fields
-keepclassmembers class com.satya.smartmealplanner.data.model.** {
    <fields>;
    <methods>;
}
-keepclassmembers class com.satya.smartmealplanner.domain.model.** {
    <fields>;
    <methods>;
}

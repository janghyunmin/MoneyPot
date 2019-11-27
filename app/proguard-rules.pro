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
-dontwarn com.roughike.**
-dontwarn com.zhihu.**
-dontwarn dagger.android.**
-dontwarn okhttp3.internal.**
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8

-keep class androidx.appcompat.widget.** { *; }

-keepattributes Signature -dontwarn com.dream.magic.** -keep class com.dream.magic.** {*;}
-dontwarn com.dreamsecurity.** -keep class com.dreamsecurity.** {*;}
-dontwarn org.apache.http.** -keep class org.apache.http.** {*;}
-keepattributes InnerClasses -keep class **.R -keep class **.R$* {
<fields>;


}
buildscript {
    val agp_version by extra("8.1.4")
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
val defaultTargetSdkVersion by extra(34)
val defaultTargetSdkVersion1 by extra(defaultTargetSdkVersion)

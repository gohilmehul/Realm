# Realm
android realm tutorial

realm for android
https://news.realm.io/news/realm-for-android/

Add the class path dependency to the project level build.gradle file.


buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.1'
        classpath "io.realm:realm-gradle-plugin:3.4.0"    // https://realm.io/docs/java/latest/   //find latest version of realm 
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}


 Apply the realm-android plugin to the top of the application level build.gradle file.

apply plugin: 'realm-android'

App build.gradle file

apply plugin: 'com.android.application'
apply plugin: 'realm-android'   //add

android {
    compileSdkVersion 25
    buildToolsVersion "25.0.3"
    defaultConfig {
        applicationId "com.mg.realmdemo"
        minSdkVersion 15
        targetSdkVersion 25
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
    dexOptions {
        incremental false
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })

    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support:design:25.3.1'
    testCompile 'junit:junit:4.12'
}







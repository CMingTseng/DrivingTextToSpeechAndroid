# DrivingTextToSpeechAndroid
Turns notifications to speech notification when you're driving

<img src="https://s3.postimg.org/7js7hxtir/notify.png" alt="notify" width="216" height="384">

##Introduction
This is a native Android application which uses <a href="http://www.theneura.com/">Neura</a> in order to announce notification messages using Text-To-Speech, so you won't need to look at your screen while you're driving. 
<br/>Go to <a href="https://dev.theneura.com/docs/getstarted">getting started with Neura</a> for more details.

##Requirements 
1. Basic android knowledge.
2. Android studio installed.

##Before you start
1. Go over the <a href="https://dev.theneura.com/docs/guide/android/sdk">android sdk guide</a>.
2. Neura sdk has fully methods and classes reference, <a href ="http://docs.theneura.com/android/com/neura/standalonesdk/service/NeuraApiClient.html">check it out</a>

##Installing the apk
1. Pull this git project to your Android Studio environment.
2. Build and install on a phone(which has 3g internet access, and location services turned on).
3. When the application is installed, press 'Login with Neura'.
4. Enter your phone followed by a confirmation code.
5. After the phone verification is completed, you'll be back to the main screen.
6. Now, whenever you start driving, the notification will be announced using 'Text-To-Speech'.
<img src="https://s21.postimg.org/trahwgvif/driving_state.png" alt="started_driving" width="216" height="40">
<img src="https://s13.postimg.org/ar0ul1vpz/not_driving_state.png" alt="finised_driving" width="216" height="40">
   <br/>Plus, you'll see in the main screen the specific event and the time it occurred.
7. That's it, you're good to go!

##Setting this application to be your own

If you wish to take MuteAtWork project, and integrate your own credentials, here are some basic steps that will help you during integration :

1. <a href ="https://dev.theneura.com/console/new">Add an application</a>(If you haven't registers to Neura, you'll have to create a new account).
  * Make sure that under 'Tech Info' (2nd section) you're specifying your own 'Application Package Name'. 
  * Under 'Permissions' select 'Whenever a user leaves work' and 'Whenever a user arrives to work'.
2. Apply your own definitions to MuteAtWork
  - Replace all occurrences of ```com.microapps.muteatwork``` with your own 'Application Package Name' :
    <br/>a.&nbsp;&nbsp;&nbsp;Application's ```build.gradle``` file.
    <br/>b.&nbsp;&nbsp;&nbsp;```AndroidManifest.xml``` file.
    <br/>c.&nbsp;&nbsp;&nbsp;All classes that have ```package com.microapps.muteatwork```.
  - Open ```strings.xml``` file, and update ```app_uid``` and ```app_secret``` with your own values.
    <br/>Your values can be received from <a href="https://dev.theneura.com/console/">Applications console</a>, just copy your uid and secret : <br/>
    ![uid_secret](https://s21.postimg.org/3qpj2gurr/uid_secret.png)
  - Follow our <a href="https://dev.theneura.com/docs/guide/android/pushnotification"> push notification guide</a> in order to generate 'Project Number' and 'server key'.
    <br/>a.&nbsp;&nbsp;&nbsp;<a href ="https://dev.theneura.com/console">Open your project</a> and set 'server key' to 'Android Push Credentials' under 'Tech Info' section.
    <br/>b.&nbsp;&nbsp;&nbsp;Open ```strings.xml``` file and set your own 'Project Number' to ```google_api_project_number``` resource.
3. That's it, you have your own application detecting when a user leaves and arrives home.

##Testing while developing
Obviously, it's not very convenient for a developer to receive start and finish driving on realtime, so, Neura has generated an events simulation, and you can connect it with your application by calling : ```mNeuraApiClient.simulateAnEvent();``` and for example, generate the event : 'UserStartedDriving'<br/>
FYI, you need to be logged in to Neura in order to simulate an event.<br/> 
You can read about it more on <a href ="http://docs.theneura.com/android/com/neura/standalonesdk/service/NeuraApiClient.html#simulateAnEvent--">Simulate event method</a>.

##Support
1. Go to <a href="https://dev.theneura.com/docs/getstarted">getting started with Neura</a> for more details.
2. You can read classes and api methods at <a href ="http://docs.theneura.com/android/com/neura/standalonesdk/service/NeuraApiClient.html">Neura Sdk Reference</a>.
3. You can ask question and view existing questions with the Neura tag on <a href="https://stackoverflow.com/questions/tagged/neura?sort=newest&pageSize=30">StackOverflow</a>.


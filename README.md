android-headset-blocker
=======================

App for blocking the headset's media button.

Why This Doesn't Support ICS+
=============================

On older versions of Android, the MEDIA_BUTTON intent was sent as a chained broadcast that one could interrupt early.  It was easy to implement a blocker and life was good.

On ICS+, things have changed dramatically.  Instead of defaulting to a chained broadcast, some applications can claim sole control over the MEDIA_BUTTON broadcast.  This means our app never gets the call.

I've looked into some hacky solutions but I feel they're too hacky for my liking.  What worries me in particular is that the solution I've seen registers itself as the sole receiver in reaction to someone else registering themselves as the sole receiver; what happens when there are two Services both listening and fighting each other at the same time?

So for the time being I feel it's safer to keep this as a helpful tool for previous versions of Android.  I'm limiting the maxSdkVersion so that people aren't misled into thinking this will work on ICS+.
This project includes an Android and iOS app to demonstrate [StateX](https://github.com/lilac/statex), a cross platform native application architecture.

All the logic of this app is written in [events.js](events.js), which is mainly made up by two event handlers. This javascript file is shared between the Android and iOS app, thus code reuse and consistent behavior is achieved.

The view part is written in native languages, in other words Objective-C in the iOS app and Java in the Android counterpart. A view is reactive, since it refreshes itself whenever states change, and never mutates the states. User interactions are all converted to actions, and sent to the state machine.

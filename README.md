ng-lift-chat
============

A slightly enhanced version of the basic ng-lift chat example.

For the sake of demonstrating the power of lift-ng binders an additional `ChatInputBinding` pipes up changes on the client chatInput field via the `UserIsTypingBinder` to the server. 
On the server all typing users are prefixed with an asterisk * and the new list of users is piped down through `UsersBinding` to the client via `UserBinder`.

How to run
----------
* git pull
* sbt
* container:start
* Point your browser to http://localhost:8080

Issue with lift-ng Version 0.5.6
--------------------------------
This sample works with three different Binders on the same page. When the AngularJS scope variable names are unique, such as:

* `chat` for `ChatBinder` 
* `chat1` for `UserIsTypingBinder`
* `chat2` for `UserBinder`

everything is OK.

Reusing the name `chat` for more than one binder leads to unpredictable behaviour on the client.
The reason for this is, that when a model binder (Subclass of `SimpleNgModelBinder`) updates the client, lift-ng assigns the scope variable it is assigned to.
Hence a chat update will set `$scope.chat` and wipe out any other member fields of `chat`. When you give it the name `chat` to bind to, it assumes it has dominion over that entire name.
The full discussion is in the thread [lift-ng-chat and AngularJS scope variable names](https://groups.google.com/forum/#!searchin/liftweb/lift-ng-chat/liftweb/AiAQkaiH81w/eE0aeqIioDUJ "") in the liftweb Group.

If you use such a setup, the best practice for lift-ng Version 0.5.6 is to keep the variable names unique. 
The behaviour can be experienced in this example by using the `SameChat` Page and changing the corresponding vars in LiftChat.js and in the `Binder*` Classes to `chat`.

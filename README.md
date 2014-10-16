ng-lift-chat
============

This is an slightly enhanced version of the basic ng-lift chat example

For the sake of demonstration an additional ChatInputBinding pipes up changes on the client chatInput field via the UserIsTypingBinder to the server. 
On the server all typing users are prefixed with a * and the new list of users is piped down through UsersBinding to the client via UserBinder.

How to run:
git pull
sbt
container:start
Point your browser to http://localhost:8080

What I noticed:
I started having "strange effects" as soon as the Binders are sharing the same "AngularJS Namespace" (= chat).
This can be experienced using the SameChat Page:
- Change the corresponding vars in LiftChat.js and in the Binder* Classes to "chat"

ng-lift-chat
============

A slightly enhanced version of the basic ng-lift chat example.

For the sake of demonstration an additional `ChatInputBinding` pipes up changes on the client chatInput field via the `UserIsTypingBinder` to the server. 
On the server all typing users are prefixed with an asterisk * and the new list of users is piped down through `UsersBinding` to the client via `UserBinder`.

How to run
----------
* git pull
* sbt
* container:start
* Point your browser to http://localhost:8080

Issues to play with
-------------------
It works with three different Binders on the same page. When the AngularJS scope variable names are unique:

* `chat` for `ChatBinder` 
* `chat1` for `UserIsTypingBinder`
* `chat2` for `UserBinder`

everything is OK.

As soon as I try to use the name "chat" for more than one Binder, I see in the logback log exceptions like this:

```
13:21:09.070 DEBUG o.e.chat.comet.UserIsTypingBinder - Received string: {"users":[]}
13:21:09.075 ERROR net.liftweb.http.LiftRules - Exception being returned to browser when processing /ajax_request/F1024831258386BCELLW-00/
net.liftweb.json.MappingException: No usable value for chatInput
Did not find value which can be converted into java.lang.String
...
```

There seems to be a mismatch. Somehow the UserIsTypingBinder does not find the matching AngularJS scope variable.

This can be experienced in the sample using the `SameChat` Page and changing the corresponding vars in LiftChat.js and in the `Binder*` Classes to `chat`

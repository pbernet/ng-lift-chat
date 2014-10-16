package org.example.chat.comet

import net.liftmodules.ng.Angular.NgModel
import net.liftmodules.ng.{BindingToClient, BindingToServer, SessionScope, SimpleNgModelBinder}
import net.liftweb.http.CometListener

//Note: The name msgs is by convention the corresponding AngularJS scope Var
case class ChatMessagesBinding(msgs: List[String] = List()) extends NgModel

class ChatBinder extends SimpleNgModelBinder[ChatMessagesBinding](
  "chat", /* Name of the $scope variable to bind to */
  ChatMessagesBinding(), /* Initial value for chat when the session is initialized */
  (m: ChatMessagesBinding) => {
    println("recieved from client through BindingToServer: " + m); m
  }, /* Called each time a client change is received */
  1000 /* Milliseconds for client-sync delay  */
) with BindingToClient with BindingToServer with SessionScope with CometListener {     /* optional: with BindingToServer */
    def registerWith = ChatServer
}

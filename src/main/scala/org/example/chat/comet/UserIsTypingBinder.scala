package org.example.chat.comet

import net.liftmodules.ng.Angular.NgModel
import net.liftmodules.ng.{BindingToServer, SessionScope, SimpleNgModelBinder}
import net.liftweb.http.S

case class ChatInputBinding(chatInput: String = "") extends NgModel

class UserIsTypingBinder extends SimpleNgModelBinder[ChatInputBinding](
  "chat1",
  ChatInputBinding(),
  (chatInput:ChatInputBinding ) => {
    //we are not interested in the chatInput, but want to detect who is typing
    var userIP = S.containerRequest.map(_.remoteAddress).openOr("")
    UserServer ! UserIsTyping(userIP)
    println("Recieved ChatInput: " + chatInput); chatInput
  }, /* Called each time a client change is received */
  10 /* Milliseconds for client-sync delay  */
) with BindingToServer with SessionScope

package org.example.chat.comet

import net.liftweb.actor._
import net.liftweb.http._

case class ChatMessage(msg: String, userIP: String) {
  def printString = userIP + " said: " + msg
}

object ChatServer extends LiftActor with ListenerManager {
  private var msgs = List[String]("hello chat earthling...")

  /**
   * This method is called when the <pre>updateListeners()</pre> method
   * needs a message to send to subscribed Actors. In particular, createUpdate
   * is used to create the first message that a newly subscribed CometListener
   * will receive.
   */
  override def createUpdate = {
    ChatMessagesBinding(msgs)
  }

  override def lowPriority = {
    case msg: ChatMessage => {
      msgs = (msgs :+ msg.printString).distinct takeRight 10
      UserServer ! UserPressedSendButton(msg.userIP)
      updateListeners()
    }
  }
}






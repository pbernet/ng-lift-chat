package org.example.chat.comet

import net.liftweb.actor.LiftActor
import net.liftweb.http.ListenerManager

case class UserIsTyping(userIP: String)
case class UserPressedSendButton(userIP: String)
case class UserJoined(userIP: String)
case class UserLeft(userIP: String)

object UserServer extends LiftActor with ListenerManager {
  var users = List[String]()

  private def replace[T](x: T, y: T) = (i: T) => if (i == x) y else i

  /**
   * This method is called when the <pre>updateListeners()</pre> method
   * needs a message to send to subscribed Actors. In particular, createUpdate
   * is used to create the first message that a newly subscribed CometListener
   * will receive.
   */
  override def createUpdate = {
    UsersBinding(users)
  }

  override def lowPriority = {
    case usr: UserIsTyping => {
      def isAlreadyTyping(userIP: String) = users.contains("*"+ userIP )

      println("UserIsTyping: " + usr.userIP)

      if(!isAlreadyTyping(usr.userIP)) {
        users = users map replace(usr.userIP, "*" + usr.userIP)
        println("Current user List: " + users)
        updateListeners()
      }
    } case UserPressedSendButton(userIP) =>  {
      println("UserStoppedTyping: " + userIP)
      users = users map replace("*" + userIP, userIP)
      updateListeners()
    }
    case UserJoined(userIP) => {
      users = users :+ userIP
      updateListeners()
    }
    case UserLeft(userIP) => {
      users = users.filterNot(elem => elem == userIP || elem == "*" + userIP)
      updateListeners()
    }
    case a:Any => println("WTF: " + a)
  }
}

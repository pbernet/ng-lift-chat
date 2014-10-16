package org.example.chat.comet

import java.util.concurrent.ScheduledFuture

import net.liftweb.actor.LiftActor
import net.liftweb.http.ListenerManager
import net.liftweb.util.Helpers._
import net.liftweb.util.Schedule

case class UserIsTyping(userIP: String)

case class UserIsThinking(userIP: String)

case class UserPressedSendButton(userIP: String)

case class UserJoined(userIP: String)

case class UserLeft(userIP: String)

object UserServer extends LiftActor with ListenerManager {
  var users = List[String]()
  var mapOfFutures = Map[String, ScheduledFuture[Unit]]()


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
    case UserIsTyping(userIP) => {
      users = users map {
        case userStr if userStr.contains(userIP) => userIP + " is typing"
        case userStr => userStr
      }
      println("UserIsTyping: " + userIP)
      activateIsThinkingFor(userIP)

      println("Current user List: " + users)
      updateListeners()

    }
    case UserIsThinking(userIP) => {
      users = users map {
        case userStr if userStr.contains(userIP) => userIP + " is thinking"
        case userStr => userStr
      }
      updateListeners()
    }
    case UserPressedSendButton(userIP) => {
      println("UserPressedSendButton: " + userIP)
      users = users map {
        case userStr if userStr.contains(userIP) => userIP
        case userStr => userStr
      }
      cancelThinkingFor(userIP)
      updateListeners()
    }
    case UserJoined(userIP) => {
      users = users :+ userIP
      updateListeners()
    }
    case UserLeft(userIP) => {
      users = users.filterNot(_.contains(userIP))
      updateListeners()
    }
    case a: Any => println("WTF: " + a)
  }

  private def cancelThinkingFor(userIP: String) {
    mapOfFutures.get(userIP).foreach(_.cancel(false))
  }

  private def activateIsThinkingFor(userIP: String) {
    val theFuture = Schedule.schedule(UserServer, UserIsThinking(userIP), 1.seconds)

    //replace existing future
    mapOfFutures = mapOfFutures.map {
      case (uIP, future) if uIP == userIP => {
        future.cancel(false)
        (uIP, theFuture)
      }
      case t => t
    }
    //add new future
    if (!mapOfFutures.contains(userIP)) {
      mapOfFutures = mapOfFutures.updated(userIP, theFuture)
    }
  }
}

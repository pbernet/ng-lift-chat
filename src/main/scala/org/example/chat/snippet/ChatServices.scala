package org.example.chat.snippet

import net.liftmodules.ng.Angular._
import net.liftweb.common.Empty
import net.liftweb.http.S
import org.example.chat.comet._

object ChatServices {
  def render = renderIfNotAlreadyDefined(
    angular.module("ChatServices")
      .factory("ChatService", jsObjFactory()
      .jsonCall("sendChat", sendChat)
      .jsonCall("sendJoin", sendJoin)
      .jsonCall("sendLeave", sendLeave)
      )
  )

  private def sendChat:(String) => Empty.type = {
    (msg: String) => {
      ChatServer ! ChatMessage(msg,getUserIP)
      Empty
    }
  }

  private def sendJoin = {
      UserServer ! UserJoined(getUserIP)
      Empty
  }

  private def sendLeave = {
    UserServer ! UserLeft(getUserIP)
    Empty
  }

  private def getUserIP = {
    //We are in the context of the ajax request
    S.containerRequest.map(_.remoteAddress).openOr("")
  }
}

package org.example.chat.comet

import net.liftmodules.ng.Angular.NgModel
import net.liftmodules.ng.{BindingToClient, SessionScope, SimpleNgModelBinder}
import net.liftweb.http.CometListener

case class UsersBinding(users: List[String] = List()) extends NgModel

class UserBinder extends SimpleNgModelBinder[UsersBinding](
  "chat2",
  UsersBinding(UserServer.users)
) with BindingToClient with SessionScope with CometListener {
  def registerWith = UserServer
}

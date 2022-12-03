package org.onru.carrotcb
package actors

import messages.ContextConsumer.{EntityRead, ReadEntity}
import messages.EntityManager.Entity
import akka.actor.Actor
import akka.util.Timeout

class ContextConsumer(implicit timeout: Timeout) extends Actor {

  def receive: PartialFunction[Any, Unit] = {
    case ReadEntity(`type`: String) =>
      println(`type`)
      sender() ! EntityRead(Entity("newID", `type`))
      println("actor: " + `type`)
  }
}

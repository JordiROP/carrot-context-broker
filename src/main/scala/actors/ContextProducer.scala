package org.onru.carrotcb
package actors

import messages.ContextProducer.CreateEntity
import messages.EntityManager.Entity
import messages.ContextProducer.EntityCreated

import akka.actor.Actor
import akka.util.Timeout

class ContextProducer(implicit timeout: Timeout) extends Actor {

  def receive: PartialFunction[Any, Unit] = {
    case CreateEntity(entity: Entity) =>
        sender() ! EntityCreated(entity)
        println(entity)
  }

}

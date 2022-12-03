package org.onru.carrotcb
package messages

import EntityManager.Entity

object ContextProducer {
  case class CreateEntity(entity: Entity)

  sealed trait ProducerResponse
  case class EntityCreated(entity: Entity) extends ProducerResponse
}

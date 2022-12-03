package org.onru.carrotcb
package messages

import messages.EntityManager.Entity

object ContextConsumer {
  case class ReadEntity(`type`: String)

  sealed trait ConsumerResponse
  case class EntityRead(entity: Entity) extends ConsumerResponse
}

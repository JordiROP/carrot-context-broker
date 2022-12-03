package org.onru.carrotcb
package messages.persistance

import messages.EntityManager.Entity

object DBManager {
  case class ReadEntity(`type`: String)

  sealed trait DBManagerResponse
  case class OneInserted (entity: Entity) extends DBManagerResponse
}

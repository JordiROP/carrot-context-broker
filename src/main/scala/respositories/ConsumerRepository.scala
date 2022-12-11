package org.onru.carrotcb
package respositories

import models.Entity
import types.Id

trait Repository {
  def retrieveEntity(id: Id): Entity
}

class ConsumerRepository extends Repository {
  override def retrieveEntity(id: Id): Entity = Entity(id, "a description")
}

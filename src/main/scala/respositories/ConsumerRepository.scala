package org.onru.carrotcb
package respositories

trait ConsumerRepository[F[_]] {
  def retrieveEntity(id: String):
}

class ConsumerRepository {

}

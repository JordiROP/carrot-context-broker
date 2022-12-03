package org.onru.carrotcb
package messages

import messages.EntityManager.Entity

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object EntityManager {
  case class Entity(id: String, `type`: String)
}

trait EntityMarshaller extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val entityFormat: RootJsonFormat[Entity] = jsonFormat2(Entity)
}

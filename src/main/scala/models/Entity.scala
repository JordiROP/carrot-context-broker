package org.onru.carrotcb
package models
import io.circe._
import types.Id

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

final case class Entity(id: Id, description: String)

object Entity {
  implicit val decode: Decoder[Entity] = deriveDecoder[Entity]
  implicit val encode: Encoder[Entity] = deriveEncoder[Entity]
}
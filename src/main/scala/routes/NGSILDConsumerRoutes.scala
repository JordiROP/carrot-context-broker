package org.onru.carrotcb
package routes

import models.Entity
import respositories.Repository
import types.Id

import cats.Applicative
import cats.effect.IO
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.{EntityDecoder, EntityEncoder, HttpRoutes}

final class NGSILDConsumerRoutes(repo: Repository) extends Http4sDsl[IO] {
  implicit def decodeEntity: EntityDecoder[IO, Entity] = jsonOf
  implicit def encodeEntity[A[_]: Applicative]: EntityEncoder[A, Entity] = jsonEncoderOf

  var informationModelType: String = "ngsi"
  var version: String = "v1"

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "ngsi" / "v1" / "entity" / id =>
      val entityId = id.asInstanceOf[Id]
      Ok(repo.retrieveEntity(entityId))
  }
}

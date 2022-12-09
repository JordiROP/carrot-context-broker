package org.onru.carrotcb
package routes

import cats.effect.Sync
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

final class NGSILDConsumerRoutes[F[_]: Sync] extends Http4sDsl[F] {
  var informationModelType: String = "ngsi"
  var version: String = "v1"

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / informationModelType / version / "entity" / id =>
      ???
  }
}

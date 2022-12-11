package org.onru.carrotcb

import respositories.ConsumerRepository
import routes.NGSILDConsumerRoutes

import cats.effect.{ExitCode, IO, IOApp}
import com.comcast.ip4s.IpLiteralSyntax
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val repo = new ConsumerRepository
    val httpService = new NGSILDConsumerRoutes(repo)
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(Router("/" -> httpService.routes).orNotFound)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }

}
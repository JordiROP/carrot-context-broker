package org.onru.carrotcb

import routes.{RestContextConsumerAPI, RestContextProducerAPI}

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.{Materializer, SystemMaterializer}
import akka.util.Timeout
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.{ExecutionContextExecutor, Future}

object Run extends App with RequestTimeout {
  // this configs are in the application.conf file
  val config = ConfigFactory.load()
  val host = config.getString("http.host") // Gets the host and a port from the configuration
  val port = config.getInt("http.port")

  implicit val system: ActorSystem = ActorSystem()  // ActorMaterializer requires an implicit ActorSystem
  implicit val ec: ExecutionContextExecutor = system.dispatcher  // bindingFuture.map requires an implicit ExecutionContext

  val producerApi: Route = new RestContextProducerAPI(system, requestTimeout(config)).routes // the RestApi provides a Route
  val consumerApi: Route = new RestContextConsumerAPI(system, requestTimeout(config)).routes // the RestApi provides a Route
  val api: Route = Directives.concat(producerApi, consumerApi)
  implicit val materializer: Materializer = SystemMaterializer(system.classicSystem).materializer  // bindAndHandle requires an implicit materializer

  val bindingFuture: Future[ServerBinding] = Http().newServerAt(host, port).bindFlow(api) // starts the HTTP server
  val log = Logging(system.eventStream, "carrotcb")

  try {
    //    Here we start the HTTP server and log the info
    bindingFuture.map { serverBinding ⇒
      log.info(s"RestApi bound to ${serverBinding.localAddress}")
    }
  }catch {
    //    If the HTTP server fails to start, we throw an Exception and log the error and close the system
    case ex: Exception ⇒
      log.error(ex, "Failed to bind to {}:{}!", host, port)
      //      System shutdown
      system.terminate()
  }
}

trait RequestTimeout {
  import scala.concurrent.duration._
  def requestTimeout(config: Config): Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}
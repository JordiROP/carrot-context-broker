package org.onru.carrotcb
package routes

import actors.{ContextConsumer, ContextProducer}
import messages.ContextConsumer.{ConsumerResponse, ReadEntity}
import messages.{ContextConsumer, EntityMarshaller}

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.StatusCodes.Created
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

class RestContextConsumerAPI(system: ActorSystem, timeout: Timeout) extends ConsumerRestRoutes {
  implicit val requestTimeout: Timeout = timeout
  implicit def executionContext: ExecutionContextExecutor = system.dispatcher

  def createContextConsumer(): ActorRef =
    system.actorOf(
      Props(new ContextConsumer))
}

sealed trait ConsumerRestRoutes extends ContextConsumerAPI with EntityMarshaller {
  val version = "v1"

  protected val readEntityByType: Route   = {
    path(version / "entity" )
      get {
        parameter("type") { `type` =>
          onSuccess(readEntity(`type`)) {
            case ContextConsumer.EntityRead(ent) => complete(Created, ent)
          }
        }
      }
  }

  val routes: Route = readEntityByType
}

trait ContextConsumerAPI {
  def createContextConsumer(): ActorRef

  implicit def executionContext: ExecutionContext
  implicit def requestTimeout: Timeout

  lazy val contextConsumer: ActorRef = createContextConsumer()

  def readEntity(`type`: String): Future[ConsumerResponse] = {
    contextConsumer.ask(ReadEntity(`type`))
      .mapTo[ConsumerResponse]
  }
}



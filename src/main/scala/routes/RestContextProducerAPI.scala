package org.onru.carrotcb
package routes

import actors.ContextProducer
import messages.ContextProducer.{CreateEntity, ProducerResponse}
import messages.EntityManager.Entity
import messages.{ContextProducer, EntityMarshaller}

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.StatusCodes.Created
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

class RestContextProducerAPI(system: ActorSystem, timeout: Timeout) extends ProducerRestRoutes {
  implicit val requestTimeout: Timeout = timeout
  implicit def executionContext: ExecutionContextExecutor = system.dispatcher

  def createContextProducer(): ActorRef =
    system.actorOf(
      Props(new ContextProducer))
}

sealed trait ProducerRestRoutes extends ContextProducerAPI with EntityMarshaller {
  val version = "v1"
  val service = "entity"

  protected val createEntityRoute: Route = {
    path(version / "entity")
      post {
        entity(as[Entity]) { ent =>
          onSuccess(createEntity(ent)) {
            case ContextProducer.EntityCreated(ent) => complete(Created, ent)
          }
        }
      }
  }

  val routes: Route = createEntityRoute
}

trait ContextProducerAPI {
  def createContextProducer(): ActorRef

  implicit def executionContext: ExecutionContext
  implicit def requestTimeout: Timeout

  lazy val contextProducer: ActorRef = createContextProducer()

  def createEntity(entity: Entity): Future[ProducerResponse] = {
    contextProducer.ask(CreateEntity(entity: Entity))
      .mapTo[ProducerResponse]
  }
}



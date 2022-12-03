package org.onru.carrotcb
package actors.persistance

import org.mongodb.scala.{Document, MongoClient, MongoDatabase}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MongoDB(uri: String = "mongodb://localhost:27017", dbName: String = "local") extends Persistence[Document] {
  val logger: Logger = LoggerFactory.getLogger(classOf[MongoDB])
  private val _client: MongoClient = MongoClient(uri)
  private val _db: MongoDatabase = _client.getDatabase(dbName)

  override def getOne: Document = ???

  override def getAll: Seq[Document] = Await.result(_db.getCollection("col").find().collect().toFuture(), Duration.Inf)

  override def insertOne(): Unit = ???

  override def insertBatch(): Unit = ???

  override def updateOne(): Unit = ???

  override def updateAll(): Unit = ???

  override def deleteOne(): Unit = ???

  override def deleteAll(): Unit = ???
}
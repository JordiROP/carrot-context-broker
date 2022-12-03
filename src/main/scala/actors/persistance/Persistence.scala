package org.onru.carrotcb
package actors.persistance

trait Persistence[A] {
  def getOne:A
  def getAll:Seq[A]
  def insertOne(): Unit
  def insertBatch(): Unit
  def updateOne(): Unit
  def updateAll(): Unit
  def deleteOne(): Unit
  def deleteAll(): Unit
}

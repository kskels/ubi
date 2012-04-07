package ubi.core.micclient

import scala.collection.immutable.List
import ubi.protocols.UbiMessage
import akka.actor.ActorRef

case class DataPacket(val words : List[String]) extends UbiMessage
case class Subscribe(val subscriber : ActorRef) extends UbiMessage

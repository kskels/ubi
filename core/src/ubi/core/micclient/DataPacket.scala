package ubi.core.micclient

import scala.collection.immutable.List
import ubi.protocols.UbiMessage

case class DataPacket(val words : List[String]) extends UbiMessage

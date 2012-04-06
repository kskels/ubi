package ubi.core.micclient

import scala.collection.immutable.List
import ubi.protocols.Header

class DataPacket(val header : Header, val words : List[String])

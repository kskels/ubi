package ubi.core.micclient.messages

import scala.actors.Actor;
import scala.actors.Actor._;
import scala.collection.immutable.List;

class DataPacket {
  var source : Actor = null;
  var target : Actor = null;
  var words : List[String] = List();
}

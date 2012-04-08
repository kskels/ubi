package ubi.core.micclient

import scala.collection.immutable.List
import akka.actor.ActorRef
import ubi.protocols.{StartOk, Start, UbiMessage}
import ubi.core.{PluginBase}

case class DataPacket(words : List[String]) extends UbiMessage
case class Subscribe() extends UbiMessage

class MicClient extends PluginBase {
    var _subscribers = List[ActorRef]();

    def notifySubscribers(list : List[String]) {
        for (subscriber <- _subscribers) {
            subscriber ! DataPacket(list);
        }
    }

    def receive = {
        case Subscribe() => {
            log.info("Subscribe received from " + sender);
            _subscribers = sender :: _subscribers;
        }
        case Start() =>
            sender ! StartOk();
        case msg =>
            log.info("Unknown data received, ignoring: " + msg);
    }
}

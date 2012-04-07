package ubi.core.micclient

import scala.collection.immutable.List
import akka.actor.{ActorRef, Actor}
import ubi.protocols.{Start, UbiMessage}
import ubi.log.Log
import ubi.log.LogLevel._

case class DataPacket(words : List[String]) extends UbiMessage
case class Subscribe() extends UbiMessage

class MicClient extends Actor {
    var _subscribers = List[ActorRef]();

    def notifySubscribers(list : List[String]) {
        for (subscriber <- _subscribers) {
            subscriber ! DataPacket(list);
        }
    }

    def receive = {
        case Subscribe() => {
            Log.log(INFO, "Subscribe received from " + sender);
            _subscribers = sender :: _subscribers;
        }
        case Start() =>
            Log.log(INFO, "Start received");
        case msg =>
            Log.log(INFO, "Unknown data received, ignoring: " + msg);
    }
}

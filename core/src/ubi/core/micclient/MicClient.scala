package ubi.core.micclient

import scala.collection.immutable.List
import akka.actor.{ActorRef, Actor}
import ubi.protocols.UbiMessage

case class DataPacket(words : List[String]) extends UbiMessage
case class Subscribe(subscriber : ActorRef) extends UbiMessage

class MicClient extends Actor {
    var _subscribers = List[ActorRef]();

    def notifySubscribers(list : List[String]) {
        for (subscriber <- _subscribers) {
            subscriber ! DataPacket(list);
        }
    }

    def receive = {
        case Subscribe(subscriber) =>
            _subscribers = subscriber :: _subscribers;
    }
}

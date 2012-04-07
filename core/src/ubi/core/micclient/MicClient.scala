package ubi.core

import micclient.{DataPacket, Subscribe}
import scala.collection.immutable.List
import akka.actor.{ActorRef, Actor}

class MicClient extends Actor {
    var _subscribers = List[ActorRef]();

    def notifySubscribers(list : List[String]) {
        if (_subscribers.isEmpty) return;
        for (subscriber <- _subscribers) {
            subscriber ! DataPacket(list);
        }
    }

    def receive = {
        case Subscribe(subscriber) =>
            _subscribers = subscriber :: _subscribers;
    }
}
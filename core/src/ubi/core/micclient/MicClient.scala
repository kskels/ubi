package ubi.core

import micclient.DataPacket
import scala.collection.mutable.Set
import scala.collection.immutable.List
import akka.actor.Actor

class MicClient extends Actor {
    val _subscribers = Set[Actor]();

    def notifySubscribers(list : List[String]) {
        if (_subscribers.isEmpty) return;
        for (subscriber <- _subscribers) {
            subscriber ! DataPacket(list);
        }
    }

    def receive = {
        while(true) {
            Thread.sleep(1000);
            notifySubscribers(List("McHalls", "was", "here"));
        }
    }
}
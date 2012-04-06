package ubi.core

import micclient.DataPacket
import scala.collection.mutable.Set
import scala.collection.immutable.List
import ubi.protocols.Header

class MicClient extends PluginBase("MicClient") {
    val _subscribers = Set[PluginBase]();

    def notifySubscribers(list : List[String]) {
        if (_subscribers.isEmpty) return;
        for (subscriber <- _subscribers) {
            subscriber ! new DataPacket(new Header(this, subscriber), list);
        }
    }

    override def act() {
        loop {
            Thread.sleep(1000);
            notifySubscribers(List("McHalls", "was", "here"));
        }
    }
}
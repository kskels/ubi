package ubi.core

import scala.collection.immutable.List;
import ubi.core.micclient.messages.DataPacket;

class MicClient extends PluginBase("MicClient") {
    def notifySubscribers(list : List[String]) {
        if (subscribers.isEmpty) return;
        var packet = new DataPacket();
        packet.source = this;
        packet.words = list;
        for (subscriber <- subscribers) {
            packet.target = subscriber;
            subscriber ! packet;
        }
    }

    override def act() {
        while (true) {
            Thread.sleep(1000);
            notifySubscribers(List("McHalls", "was", "here"));
        }
    }
}

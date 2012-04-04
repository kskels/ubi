package ubi.core

import scala.actors.Actor;
import scala.actors.Actor._;
import scala.collection.immutable.List;
import ubi.core.subscribers.SubscriberHandler;
import ubi.core.micclient.messages.DataPacket;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class MicClient extends PluginBase("MicClient") {
    def notifySubscribers() {
        if (subscribers.isEmpty) return;
        var packet = new DataPacket();
        packet.source = this;
        packet.words = List("McHalls", "was", "here");
        for (subscriber <- subscribers) {
            packet.target = subscriber;
            subscriber ! packet;
        }
    }
}

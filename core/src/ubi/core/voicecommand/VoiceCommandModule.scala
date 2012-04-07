package ubi.core.voicecommand

import ubi.log.Log
import ubi.log.LogLevel._
import collection.mutable.HashMap
import collection.immutable.List
import akka.actor.{ActorRef, Actor}
import ubi.core.micclient.DataPacket
import ubi.protocols.UbiMessage

case class Subscribe(subscriber : ActorRef) extends UbiMessage

class VoiceCommandModule extends Actor {
    var _subscribers = List[ActorRef]();
    val _commandHandlers = new HashMap[String, ActorRef]();
    var _inputSession: Session = null;
    val _outputSessions: List[Session] = List();

    def notifySubscribers(list: List[String]) {
        for (subscriber <- _subscribers) {
            subscriber ! DataPacket(list);
        }
    }

    def receive = {
        case Subscribe(subscriber) => {
            Log.log(INFO, "Subscriber added: " + subscriber);
            _subscribers = subscriber :: _subscribers;
        }
        case DataPacket(words) => {
            Log.log(INFO, "Received data: " + words);
            notifySubscribers(words);
        }
        case msg =>
            Log.log(INFO, "Unknown data received, ignoring: " + msg);
    }
}

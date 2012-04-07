package ubi.core.voicecommand

import ubi.log.Log
import ubi.log.LogLevel._
import collection.mutable.HashMap
import collection.immutable.List
import akka.actor.{Actor}
import ubi.core.micclient.DataPacket
import akka.actor.{ActorRef, Props, ActorSystem}
import ubi.protocols.{Error, Start, UbiMessage}

case class Subscribe() extends UbiMessage

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
        case Subscribe() => {
            Log.log(INFO, "Subscribe received from " + sender);
            _subscribers = sender :: _subscribers;
        }
        case DataPacket(words) => {
            Log.log(INFO, "Received data: " + words);
            notifySubscribers(words);
        }
        case Start() => {
            val micClient = context.system.actorFor("MicClient");
            if (micClient == null) {
                sender ! Error("MicClient is not found, cannot subscribe");
            } else {
                micClient ! ubi.core.micclient.Subscribe();
            }
        }
        case msg =>
            Log.log(INFO, "Unknown data received, ignoring: " + msg);
    }
}

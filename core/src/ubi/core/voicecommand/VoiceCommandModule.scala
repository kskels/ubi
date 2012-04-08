package ubi.core.voicecommand

import collection.mutable.HashMap
import collection.immutable.List
import ubi.core.micclient.DataPacket
import akka.actor.ActorRef
import ubi.protocols.{StartOk, Error, Start, UbiMessage}
import ubi.core.{PluginBase}

case class Subscribe() extends UbiMessage

class VoiceCommandModule extends PluginBase {
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
            _subscribers = sender :: _subscribers;
        }
        case DataPacket(words) => {
            notifySubscribers(words);
        }
        case Start() => {
            val micClient = context.system.actorFor("MicClient");
            if (micClient == null) {
                sender ! Error("MicClient is not found, cannot subscribe");
            } else {
                micClient ! ubi.core.micclient.Subscribe();
                sender ! StartOk();
            }
        }
        case msg =>
            log.info("Unknown data received, ignoring: " + msg);
    }
}

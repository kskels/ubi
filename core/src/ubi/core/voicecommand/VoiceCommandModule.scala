package ubi.core.voicecommand

import ubi.log.Log
import ubi.log.LogLevel._
import ubi.core.micclient.DataPacket
import collection.mutable.HashMap
import collection.mutable.Set
import akka.actor.Actor

class VoiceCommandModule extends Actor {
    val _subscribers = Set[Actor]();
    val _commandHandlers = new HashMap[String, Actor]();
    var _inputSession: Session = null;
    val _outputSessions: List[Session] = List();

    def receive = {
        case msg: DataPacket => {
            Log.log(INFO, "Received data: " + msg.words);
            val isReset = isResetPresent(msg.words);
        }
        case msg => {
            Log.log(INFO, "Unknown data received, ignoring");
        }
    }

    def notifySubscribers(list: List[String]) {
        if (_subscribers.isEmpty) return;
        for (subscriber <- _subscribers) {
            subscriber ! DataPacket(list);
        }
    }

    def isResetPresent(words: List[String]): Boolean = {
        for (word <- words) {
            if (word == Commands.ResetCommand) return true;
        }
        return false;
    }
}
package ubi.core.voicecommand

import ubi.log.Log
import ubi.log.LogLevel._
import collection.mutable.HashMap
import collection.immutable.List
import ubi.core.micclient.DataPacket
import akka.actor.{ActorRef, Actor}

class VoiceCommandModule extends Actor {
    var _subscribers = List[ActorRef]();
    val _commandHandlers = new HashMap[String, ActorRef]();
    var _inputSession: Session = null;
    val _outputSessions: List[Session] = List();

    def receive = {
        case Subscribe(subscriber) => {
            Log.log(INFO, "Subscriber added: " + subscriber);
            _subscribers = subscriber :: _subscribers;
        }
        case DataPacket(words) => {
            Log.log(INFO, "Received data: " + words);
            val isReset = isResetPresent(words);
        }
        case msg =>
            Log.log(INFO, "Unknown data received, ignoring: " + msg);
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
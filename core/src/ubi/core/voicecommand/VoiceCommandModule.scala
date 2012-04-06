package ubi.core.voicecommand

import ubi.log.Log
import ubi.log.LogLevel._
import ubi.core.{Globals, PluginBase}
import ubi.core.micclient.DataPacket
import collection.mutable.HashMap
import collection.mutable.Set
import ubi.protocols.Header

class VoiceCommandModule extends PluginBase("VoiceCommandModule") {
    val _subscribers = Set[PluginBase]();
    val _commandHandlers = new HashMap[String, PluginBase]();
    var _inputSession: Session = null;
    val _outputSessions: List[Session] = List();

    override def initialize() {
        Globals.locationService.subscribe(this, List("MicClient"));
    }

    override def act() {
        loop {
            react {
                case msg: DataPacket => {
                    Log.log(INFO, "Received data: " + msg.words);
                    val isReset = isResetPresent(msg.words);
                    act();
                }
                case msg => {
                    Log.log(INFO, "Unknown data received, ignoring");
                    act();
                }
            }
        }
    }

    def notifySubscribers(list: List[String]) {
        if (_subscribers.isEmpty) return;
        for (subscriber <- _subscribers) {
            subscriber ! new DataPacket(new Header(this, subscriber), list);
        }
    }

    def isResetPresent(words: List[String]): Boolean = {
        for (word <- words) {
            if (word == Commands.ResetCommand) return true;
        }
        return false;
    }
}
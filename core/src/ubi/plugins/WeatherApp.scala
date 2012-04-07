package ubi.plugins

import ubi.log.Log
import ubi.log.LogLevel._
import akka.actor.Actor
import ubi.core.micclient.DataPacket
import ubi.protocols.Error._
import ubi.protocols.{Error, Start}

class WeatherApp extends Actor {
    def receive = {
        case DataPacket(words) =>
            Log.log(INFO, "Received data: " + words);
        case Start() => {
            val vcModule = context.system.actorFor("VoiceCommandModule");
            if (vcModule == null) {
                sender ! Error("VoiceCommandModule is not found, cannot subscribe");
            } else {
                vcModule ! ubi.core.voicecommand.Subscribe();
            }
        }
        case msg =>
            Log.log(INFO, "Unknown data received, ignoring: " + msg);
    }
}

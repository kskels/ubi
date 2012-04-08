package ubi.plugins

import ubi.core.micclient.DataPacket
import ubi.protocols.{StartOk, Error, Start}
import ubi.core.PluginBase

class WeatherApp extends PluginBase {
    def receive = {
        case DataPacket(words) => {}
        case Start() => {
            val vcModule = context.system.actorFor("VoiceCommandModule");
            if (vcModule == null) {
                sender ! Error("VoiceCommandModule is not found, cannot subscribe");
            } else {
                vcModule ! ubi.core.voicecommand.Subscribe();
                sender ! StartOk();
            }
        }
        case msg =>
            log.info("Unknown data received, ignoring: " + msg);
    }
}

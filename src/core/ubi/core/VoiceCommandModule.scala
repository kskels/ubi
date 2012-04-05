package ubi.core

import micclient.messages.DataPacket
import ubi.log.{LogLevel, Log}
import ubi.log.LogLevel._;
;

class VoiceCommandModule extends PluginBase("VoiceCommandModule") {
    override def initialize() {
        Globals.locationService.subscribe(this, List("MicClient"));
    }

    override def act() {
        while (true) {
            receive {
                case msg : DataPacket =>
                    Log.log(INFO, "Received data: " + msg.words);
            }
        }
    }
}
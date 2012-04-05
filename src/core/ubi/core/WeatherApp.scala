package ubi.core

import micclient.messages.DataPacket
import ubi.log.Log
import ubi.log.LogLevel._

class WeatherApp extends PluginBase("WeatherApp") {
    override def initialize() {
        Globals.locationService.subscribe(this, List("VoiceCommandModule"));
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
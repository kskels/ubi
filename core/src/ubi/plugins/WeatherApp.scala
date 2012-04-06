package ubi.plugins

import ubi.log.Log
import ubi.log.LogLevel._
import ubi.core.{Globals, PluginBase}
import ubi.core.micclient.DataPacket

class WeatherApp extends PluginBase("WeatherApp") {
    override def initialize() {
        Globals.locationService.subscribe(this, List("VoiceCommandModule"));
    }

    override def act() {
        while (true) {
            receive {
                case msg: DataPacket =>
                    Log.log(INFO, "Received data: " + msg.words);
            }
        }
    }
}
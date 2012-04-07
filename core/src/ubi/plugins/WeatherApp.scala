package ubi.plugins

import ubi.log.Log
import ubi.log.LogLevel._
import akka.actor.Actor
import ubi.core.micclient.DataPacket

class WeatherApp extends Actor {
    def receive = {
        case DataPacket(words) =>
            Log.log(INFO, "Received data: " + words);
        case msg =>
            Log.log(INFO, "Unknown data received, ignoring: " + msg);
    }
}

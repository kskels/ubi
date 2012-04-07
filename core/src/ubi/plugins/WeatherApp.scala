package ubi.plugins

import ubi.log.Log
import ubi.log.LogLevel._
import ubi.core.micclient.DataPacket
import akka.actor.Actor

class WeatherApp extends Actor {
    def receive = {
        case DataPacket(words) =>
        Log.log(INFO, "Received data: " + words);
    }
}
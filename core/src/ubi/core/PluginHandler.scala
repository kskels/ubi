package ubi.core

import akka.actor.{Props, ActorSystem}
import ubi.plugins.WeatherApp
import voicecommand.VoiceCommandModule

class PluginHandler {
    def initialize() {
        val system = ActorSystem("Ubi");
        val micClient = system.actorOf(Props[MicClient], name = "MicClient")
        val voiceCommandModule = system.actorOf(Props[VoiceCommandModule], name = "VoiceCommandModule")
        val weatherApp = system.actorOf(Props[WeatherApp], name = "WeatherApp")
    }
}

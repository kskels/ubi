package ubi.core

import scala.collection.immutable.List
import micclient.MicClient
import ubi.plugins.WeatherApp
import voicecommand.VoiceCommandModule
import ubi.protocols.Start
import akka.actor.{ActorRef, Props, ActorSystem}

class PluginHandler {
    def initialize() {
        val system = ActorSystem("Ubi");
        var actors = List[ActorRef]();
        actors = system.actorOf(Props[MicClient], name = "MicClient") :: actors;
        actors = system.actorOf(Props[VoiceCommandModule], name = "VoiceCommandModule") :: actors;
        actors = system.actorOf(Props[WeatherApp], name = "WeatherApp") :: actors;
        for (actor <- actors) {
            actor ! Start();
        }
    }
}

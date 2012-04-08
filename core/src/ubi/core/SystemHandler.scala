package ubi.core

import akka.actor.{Props, ActorSystem}
import ubi.protocols.Start
import com.typesafe.config.ConfigFactory

class SystemHandler {
    def initialize() {
        val config = ConfigFactory.load();
        val system = ActorSystem("Ubi", config.getConfig("ubi").withFallback(config));
        val pluginHandler = system.actorOf(Props[PluginHandler], name = "PluginHandler");
        pluginHandler ! Start();
    }
}

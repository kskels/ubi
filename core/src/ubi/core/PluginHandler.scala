package ubi.core

import scala.collection.immutable.List
import micclient.MicClient
import ubi.plugins.WeatherApp
import voicecommand.VoiceCommandModule
import akka.actor.{Actor, ActorRef, Props}
import ubi.protocols.{StartOk, Error, Start}

class PluginHandler extends CoreBase {
    var _commander : ActorRef = null;
    var _plugins = List[ActorRef]();
    var _runningPlugins = List[ActorRef]();
    var _failedPlugins = List[ActorRef]();

    def handleStart() {
        _plugins = context.actorOf(Props[MicClient], name = "MicClient") :: _plugins;
        _plugins = context.actorOf(Props[VoiceCommandModule], name = "VoiceCommandModule") :: _plugins;
        _plugins = context.actorOf(Props[WeatherApp], name = "WeatherApp") :: _plugins;
        for (actor <- _plugins) {
            actor ! Start();
        }
    }

    def checkStartOk() {
        if (_runningPlugins.length + _failedPlugins.length < _plugins.length) return;
        _commander ! StartOk();
    }

    def receive = {
        case Start() => {
            _commander = sender;
            handleStart();
        }
        case StartOk() => {
            _runningPlugins = sender :: _runningPlugins;
            checkStartOk();
        }
        case Error(message) => {
            _failedPlugins = sender :: _failedPlugins;
            checkStartOk();
        }
        case msg =>
            log.info("Unknown data received, ignoring: " + msg);
    }
}

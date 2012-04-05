package ubi.core

import scala.actors.Actor;
import ubi.core.subscribers.SubscriberHandler;

class PluginBase(name : String) 
extends SubscriberHandler with Actor with Plugin {
    def register() {
        Globals.locationService.register(name, this);
    }

    def initialize() {}
    
    def act() {}
}

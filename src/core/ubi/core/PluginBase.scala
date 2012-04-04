package ubi.core

import scala.actors.Actor;
import scala.actors.Actor._;
import ubi.core.subscribers.SubscriberHandler;

class PluginBase(name : String) 
extends SubscriberHandler with Actor with IPlugin {
    def register(service : LocationService) {
        service.register(name, this);
    }
    
    def act() {}
}

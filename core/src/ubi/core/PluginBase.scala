package ubi.core

import scala.actors.Actor;

class PluginBase(val name : String) extends Actor {
    def register() {
        Globals.locationService.register(name, this);
    }

    def initialize() {}
    
    def act() {}
}

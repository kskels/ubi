package ubi.core

import scala.actors.Actor;
import scala.actors.Actor._;
import scala.collection.mutable.HashMap;
import ubi.log.Log;
import ubi.log.LogLevel._;

class LocationService {
    val plugins = new HashMap[String,Actor]();
    
    def register(name : String, plugin : Actor) {
      Log.log(INFO, "Registering plugin '" + name + "'");
      plugins.put(name, plugin);
    }
}

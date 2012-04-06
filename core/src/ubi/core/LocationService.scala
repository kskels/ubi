package ubi.core

import scala.collection.mutable.HashMap;
import ubi.log.Log;
import ubi.log.LogLevel._;

class LocationService {
    val _plugins = new HashMap[String,PluginBase]();
    
    def register(name : String, plugin : PluginBase) {
      Log.log(INFO, "Registering plugin: " + name);
      _plugins.put(name, plugin);
    }
}

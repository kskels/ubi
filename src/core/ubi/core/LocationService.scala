package ubi.core

import scala.collection.mutable.HashMap;
import ubi.log.Log;
import ubi.log.LogLevel._;

class LocationService {
    val plugins = new HashMap[String,PluginBase]();
    
    def register(name : String, plugin : PluginBase) {
      Log.log(INFO, "Registering plugin: " + name);
      plugins.put(name, plugin);
    }

    def subscribe(subscriber : PluginBase, services : List[String]) : List[String] = {
        var failed : List[String] = List();
        for (service <- services) {
            val result = plugins.get(service);
            if (result.isDefined) {
                result.get.subscribers.add(subscriber);
            } else {
                failed = service :: failed;
            }
        }
        failed;
    }
}

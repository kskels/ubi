package ubi.core

import scala.actors.Actor;
import scala.actors.Actor._;
import scala.collection.immutable.List;

class MicClient extends Actor {
    var subscribers : List[Actor] = List();
    
    def act() {

    }
}

package ubi.core.subscribers

import scala.actors.Actor;
import scala.collection.mutable.Set;

trait SubscriberHandler {
  var subscribers : Set[Actor] = Set();
}

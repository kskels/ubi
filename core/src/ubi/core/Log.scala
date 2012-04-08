package ubi.core

import akka.actor.Actor
import akka.event.Logging

trait Log extends Actor {
    val log = Logging(context.system, this);

    override def preStart() = {
        log.debug("Starting")
    }

    override def preRestart(reason: Throwable, message: Option[Any]) {
        log.error(reason, "Restarting due to [{}] when processing [{}]",
            reason.getMessage, message.getOrElse(""))
    }
}

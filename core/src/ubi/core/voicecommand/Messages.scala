package ubi.core.voicecommand

import akka.actor.ActorRef
import ubi.protocols.UbiMessage

case class Subscribe(val subscriber : ActorRef) extends UbiMessage

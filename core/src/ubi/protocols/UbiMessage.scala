package ubi.protocols

trait UbiMessage
case class Start() extends UbiMessage
case class Error(message : String) extends UbiMessage

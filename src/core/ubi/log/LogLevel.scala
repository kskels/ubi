package ubi.log

object LogLevel extends Enumeration {
    type LogLevel = Value
    val DEVEL, DEBUG, INFO, WARNING, ERROR, FATAL = Value
}

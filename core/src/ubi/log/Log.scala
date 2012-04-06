package ubi.log

import java.text.SimpleDateFormat;
import java.util.Calendar;
import ubi.log.LogLevel._;

object Log {
    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    def log(level : LogLevel, message : String) {
        val time = sdf.format(Calendar.getInstance().getTime());
        println("[" + time + "] " + level + ":: " + message);
    }
}

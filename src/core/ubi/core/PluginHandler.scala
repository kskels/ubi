package ubi.core

class PluginHandler {
    def initialize() {
        new MicClient().register(Globals.locationService);
        new VoiceCommandModule().register(Globals.locationService);
    }
}

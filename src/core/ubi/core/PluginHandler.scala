package ubi.core

class PluginHandler {
    def initialize() {
        val micClient = new MicClient();
        val voiceCommandModule = new VoiceCommandModule();
        micClient.register();
        voiceCommandModule.register();
        micClient.initialize();
        voiceCommandModule.initialize();
        micClient.start();
        voiceCommandModule.start();
    }
}

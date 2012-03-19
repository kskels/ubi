
#include <connection.hpp>
#include <log.hpp>

#include <core.pb.h>
#include <speech.pb.h>

#include <iostream>
#include <sstream>


// Command line tool for sending the test commands towards the big brain.
// It uses Google Protocol Buffers and Berkley sockets for the transport.
//
// usage: ./tool word[1] word[2] .. word[n]

int main(int argc, char* argv[]) {
  // init the log to cosole printouts
  Log::registerConsumer(Log::ToCoutConsumer());

  if (argc < 2) {
    Log(INFO) << "usage: ./tool word[1] word[2] .. word[n]";
    return -1;
  }

  Connection connection("localhost", 19292);
  connection.connect(); // move to constructor?

  if (!connection.state()) {
    Log(INFO) << "Big brain started on the correct port?";
    return -1;
  }

  SpeechMessage::Words words;
  for (int i(1); i != argc; ++i) {
    words.add_word(argv[i]);
  }
  SpeechMessage sphinx;
  *(sphinx.mutable_words()) = words;

  UbiMessage::Payload payload;
  payload.set_name("SpeechMessage");
  std::stringstream ss;
  sphinx.SerializeToOstream(&ss);
  payload.set_data(ss.str());

  UbiMessage ubi;
  ubi.set_type(UbiMessage::DATAFLOW);
  *(ubi.mutable_payload()) = payload;

  connection.send(ubi);
  connection.disconnect();

  return 0;
}


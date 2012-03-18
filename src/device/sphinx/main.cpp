
#include <connection.hpp>
#include <log.hpp>

#include <core.pb.h>
#include <sphinx.pb.h>

#include <iostream>
#include <sstream>


int main(int argc, char* argv[]) {
  // init log to the terminal printouts 
  Log::registerConsumer(Log::ToCoutConsumer());

  if (argc < 2) {
    Log(INFO) << "Enter at least one word as a command line argument";
    return -1;
  }

  Connection connection("localhost", 19292);
  connection.connect(); // move to constructor?

  if (!connection.state())
    return -1;

  SphinxMessage::Words words;
  for (int i(1); i != argc; ++i) {
    words.add_word(argv[i]);
  }

  SphinxMessage sphinx;
  *(sphinx.mutable_words()) = words;

  UbiMessage::Payload payload;
  payload.set_name("SphinxMessage");
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


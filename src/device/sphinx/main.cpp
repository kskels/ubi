
#include <connection.hpp>
#include <log.hpp>

#include <core.pb.h>
#include <sphinx.pb.h>

#include <iostream>
#include <sstream>


int main(int argc, char* argv[]) {
  // init log to the terminal printouts 
  Log::registerConsumer(Log::ToCoutConsumer());

  Connection connection("localhost", 19292);
  connection.connect(); // move to constructor?

  if (!connection.state())
    return -1;

  SphinxMessage::Words words;
  words.add_word("go");
  words.add_word("forward");
  words.add_word("10");
  words.add_word("meters");

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


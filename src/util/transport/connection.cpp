
#include <connection.hpp>
#include <peterint.hpp>
#include <log.hpp>
#include <core.pb.h>

#include <google/protobuf/text_format.h>

#include <iostream>
#include <sstream>
#include <string>
#include <vector>

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <errno.h>


void Connection::connect() {
  Log(INFO) << "Connecting to " << _address << ":" << _port << "..";

  struct sockaddr_in serv_addr;
  _sockfd = socket(AF_INET, SOCK_STREAM, 0);
  if (_sockfd < 0) {
    Log(INFO) << "Failed to open the socket";
    return;
  } 
  Log(DEBUG) << "Socket opened with value " << _sockfd;
  
  struct hostent *server = gethostbyname(_address.c_str());
  if (!server) {
    Log(INFO) << "Failed to get host by name";
    return;
  } 
  Log(DEBUG) << "Host by name gotten";
 
  bzero((char *) &serv_addr, sizeof(serv_addr));
  serv_addr.sin_family = AF_INET;
  bcopy((char *)server->h_addr, (char *)&serv_addr.sin_addr.s_addr, server->h_length);
  serv_addr.sin_port = htons(_port);
  // TODO: non-blocking connect
  if (::connect(_sockfd,(struct sockaddr *) &serv_addr,sizeof(serv_addr)) < 0) {
    Log(INFO) << "Failed to connect";
    return;
  }
  Log(INFO) << "Connection established :)";

  _state = true;
}

void Connection::disconnect() {
  Log(INFO) << "Disconnecting..";
  ::shutdown(_sockfd, SHUT_RDWR);
  _state = false;
}

void Connection::send(const UbiMessage &message) {
  // pretty print the message
  std::string output;
  google::protobuf::TextFormat::PrintToString(message, &output);
  Log(DEBUG) << "UbiMessage: \n" << output;

  std::stringstream ss;
  message.SerializeToOstream(&ss);

  std::string size = peterint::encode(ss.str().size());
  if (::write(_sockfd, size.c_str(), size.size()) < 0) {
    Log(INFO) << "Failed to write size into the socket";
    _state = false;
    return;
  }
  Log(DEBUG) << "Sent size in " << size.size() << " bytes";

  // TODO: write -> send
  // TODO: buffered send and nonblocking + non-busywaiting

  if (::write(_sockfd, ss.str().c_str(), ss.str().size()) < 0) {
    Log(INFO) << "Failed to write data into the socket";
    _state = false;
    return;
  }
  Log(DEBUG) << "Sent data in " << ss.str().size() << " bytes";
}

UbiMessage *Connection::receive(UbiMessage &message) {
  char byte;
  int n = ::recv(_sockfd, &byte, 1, MSG_DONTWAIT);
  if (n == 0 || errno == EAGAIN || errno == EWOULDBLOCK) {
    return 0;
  } else if (n < 0) {
    Log(INFO) << "Failed to recv from the socket";
    _state = false;
    return 0;
  }

  // TODO: buffered + nonblocking recv
  size_t size = 0;
  while(peterint::decode(byte, &size)) {
    if(::recv(_sockfd, &byte, 1, 0) < 0) {
      Log(INFO) << "Failed to recv from the socket";
      _state = false;
      return 0;
    }
  }

  std::vector<char> buffer(size, 0); 
  n = ::recv(_sockfd, &buffer[0], size, 0);
  if (n < 0) {
    Log(INFO) << "Failed to recv from the socket";
    _state = false;
    return 0;
  } else if (n != size) {
    Log(INFO) << "Failed to recv complete message";
    _state = false;
    return 0;
  }
  Log(DEBUG) << "Received data in " << n << " bytes";

  std::stringstream ss;
  for (int i(0); i != size; ++i)
    ss << buffer[i];
  message.ParseFromIstream(&ss);
  return &message;
}

bool Connection::state() const {
  return _state;
}


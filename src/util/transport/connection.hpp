
#ifndef _UBI_CONNECTION_H_
#define _UBI_CONNECTION_H_

#include <vector>
#include <string>

class UbiMessage;

class Connection {
public:
  Connection(const std::string& address = "localhost", size_t port = 19292) :
     _address(address), _port(port), _sockfd(0), _state(false){};
	
  void connect();
  void disconnect();
  bool state() const;

  void send(const UbiMessage& message);
  UbiMessage* receive(UbiMessage& message);
  
private:
  std::string _address;
  size_t _port;
  int _sockfd;
  bool _state;
};

#endif // !_UBI_CONNECTION_H_



#include <boost/thread.hpp>

struct Ctx {
  
};

struct Foo {
  Foo(Ctx* ctx) : _ctx(ctx) {};
  void operator()(){
    std::cout << "..." << std::endl;
  };
  Ctx* _ctx;
};

struct Processer processer  {
  Processer(Ctx* ctx) : _ctx(ctx) {};
  void operator()(){
    std::cout << "..." << std::endl;
  };
  Ctx* _ctx;
};

struct Sender {
  Sender(Ctx* ctx) : _ctx(ctx) {};
  void operator()() {
    std::cout << "running sender.." << std::endl;
  }
  Ctx* _ctx;
};

int main(int argc, char* argv[])
{
  Ctx ctx;

  Foo foo(&ctx);
  boost::thread thread(foo);

  Sender sender(&ctx);
  boost::thread sender_thread(sender);

  Processer processer(&ctx);
  boost::thread 

  thread.join(); 
  sender_thread.join();

  return 0;
}


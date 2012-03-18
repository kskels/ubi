
#ifndef _UBI_PETERINT_H_
#define _UBI_PETERINT_H_

#include <string>


namespace peterint {

  inline std::string encode(size_t size) {
    std::string result;
    for (; size || result.empty(); size = size >> 7) {
      char byte = size & 0x7F;
      if (!result.empty())
        byte = byte | 0x80;
      result.insert(result.begin(), byte);
    }
    return result;
  }

  inline bool decode(char byte, size_t* size) {
    if (byte >> 7) { 
      *size = *size + (byte + 128);   
      *size = *size << 7; 
      return true;
    }
    *size = *size + byte; 
    return false;
  }

} // namespace

#endif // _UBI_PETERINT_H_


cmake_minimum_required(VERSION 2.8)

set(CMAKE_MODULE_PATH ../../../cmake)
find_package(GTK3 REQUIRED)

include_directories(${GTK3_INCLUDE_DIRS})

add_executable(sample
  main.cpp
)
target_link_libraries(sample
  ${GTK3_LIBRARY_DIRS}
)


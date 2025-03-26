cmake_minimum_required(VERSION 3.29)
project(UTP_Project_cpp)

set(CMAKE_CXX_STANDARD 20)

add_library(UTP_Project_cpp SHARED Figures.cpp)

target_include_directories(UTP_Project_cpp PUBLIC "/home/ekren/.jdks/openjdk-23.0.1/include")
target_include_directories(UTP_Project_cpp PUBLIC "/home/ekren/.jdks/openjdk-23.0.1/include/linux")
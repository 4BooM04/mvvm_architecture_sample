
# README
# mvvm_architecture_sample
## Overview
This repository contains simple android project, which using androidx, MVVM,Databinding, and NAVIGATION classes for solving MVVM navigation problem.
It also describes modular pakage structure for better project undestanding and compiler performance
## Installation
#### For android studio
  1. Copy repository to the local repository folder
  2. Open repository on Android Studio (Latest used version is 3.2)
  3. Wait for project indexation
  4. Build project

#### For CI and grade
  1. Copy repository to local repository folder
  2. run : .gradlew clean
  3. run : .gradlew assembleRelease (for release version)
  4. run : .gradlew assembleDebug (for debug version)

## Coding Guide

- We use official [coding convention](https://kotlinlang.org/docs/reference/coding-conventions.html) and default android studio lint checks
- For setup code style in Android Studio go to *Preferences > Editor > Code Style > Kotlin* and chose *SetFrom > Predefined Style > Kotlin Style guide*
- We use camelCase notation! Don't use Hungarian notation
- We use the dependency injection pattern
- DI framework uses only for injecting dependency on activity and fragments. The else code base uses constructor dependency pattern.

#### General app structure description
    - App sources divides into modules
    - Project contain this modules:
      - :app                  - contains app ui logic
      - :kit                  - contains app business logic
      - :core                 - contains shared classes and logic for all project modules like data models
      - :storage              - for interfaces and implementation of cache layer
      - :ui                   - cross-project shareable ui  util functions an classes

#### :kit module structure
      - encrypting            - contains encrypting decrypting logic
      - repositories          - contains classes for accessing data and different CRUD operations
      - useCases              - contains business rule units (single responsibility classes)

#### :app module structure
      - application           - contain app delegate and dependency injection container
      - binders               - for databinding adapters
      - ui                    - contains implementations of screens and common UI structure
      - view                  - for custom views that can be shared throw app

#### :ui module structure
      - container             - for helper components for fragment container activities
      - extensions            - for cross-project shareable extension functions
      - navigation            - shareable components for event based navigation
      - formatter             - shareable converters that help represent different types of data on ui (DateTime as date string, for example)
      - utils                 - shareable utility classes

#### :core module structure
      - models                - application data classes
      - systemServices        - contains APIs for system calls like network state and geolocation

#### :network
     - for network-based logic (network client, JSON parsing, etc)
     - this part will be updated


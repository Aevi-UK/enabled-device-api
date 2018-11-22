# enabled-device-api
An API that can be used to access details about the AEVI enabled device that your application is installed on.

# DMS Client

A library which can be used to query a device's UIN and logged in user at runtime.

## Adding the dms-client dependency

The `dms-client` library is published to Bintray. Add the snippet below to your top-level build.gradle repositories DSL:
```
maven {
    url "http://dl.bintray.com/aevi/aevi-uk"
}
```

Then, in your application build.gradle:

```
implementation "enabled-device-api:dms-client:<version>"
```

## Sample application

Please see the `SampleApp` module for a very simple example of how to use this library.

## Simulator Application

The `SimulatorApp` is provided in order to simulate the `Aevi Agent` being installed on your development devices.
It simulates the `ContentProvider` which is embedded within the Agent, and allows you to develop as if the Agent were also instaled.

**NOTE**
You must install the `SimulatorApp` before the `dms-client` library will work.
Only one of the Agent *or* the SimulatorApp may be installed on a single device at any one time.

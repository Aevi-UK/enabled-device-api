# enabled-device-api
An API that can be used to access details about the AEVI enabled device that your application is installed on.
It can be used to query a device's UIN and logged in user at runtime.

## Adding the dependency to your project

The `enabled-device-api` is published to Bintray. Add the snippet below to your top-level build.gradle repositories DSL:
```
maven {
    url "http://dl.bintray.com/aevi/aevi-uk"
}
```

Then, in your application build.gradle:

```
implementation "com.aevi.dms:enabled-device-api:<version>"
```

## Sample application

Please see the `SampleApp` module for a very simple example of how to use this API.

## Simulator Application

The `SimulatorApp` is provided in order to simulate the `Aevi Agent` being installed on your development devices.
It simulates the `ContentProvider` which is embedded within the Agent, and allows you to develop as if the Agent were also installed.

**NOTE**
You must install the `SimulatorApp` before this API will work.
Only one of the Agent *or* the SimulatorApp may be installed on a single device at any one time.
The simulator app returns a null UIN by default, this can be changed manually to match a project specific example inside of the app.

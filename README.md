# enabled-device-api
An API that can be used to access details about the AEVI enabled device that your application is installed on.
It can be used to query a device's UIN and logged in user at runtime.

## Adding the dependency to your project
You will need to generate a [Github personal access token](https://docs.github.com/en/free-pro-team@latest/github/authenticating-to-github/creating-a-personal-access-token) with the `read:packages` OAuth scope and then define the following repository in you main gradle project file:
```
maven {
    name = "github"
    url = uri("https://maven.pkg.github.com/aevi-uk/*")
    credentials {
        username = "[your github username]"
        password = "[your github personal access token]"
    }
}
```

This will allow Gradle to fetch prebuilt binaries of the library from the AEVI publication repository. You can then add the dependency to your relevant module _build.gradle_ file:


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

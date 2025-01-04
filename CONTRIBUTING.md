# Contributing

## Environment Setup

**Install Java**

For these instructions we'll use SDKman! to manage the installation

```bash
sudo apt install zip unzip
curl -s "https://get.sdkman.io" | bash
```

Install Java 11

```bash
sdk install 11.0.25-tem
```

Check installation
```
java -version
```

## Build
TNoodle CLI uses Gradle, so building from source is just the standard gradle build command.
```
./gradlew build
```
The built .jar file will be located at `./build/releases/tnoodle-cli-VERSION.jar` 

If you want to build the full distributions for each operating system, then use the `runtimeZip` task.
```
./gradlew runtimeZip
```
The built distributions will be in `./build/releases/` 
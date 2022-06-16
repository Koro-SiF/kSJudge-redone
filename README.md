# kSJudge-redone
[Java 17, Gradle 7.4.2.][Minecraft 1.18.2]

A Judge plugin to submit and judge plots from the PlotSquared plugin. This uses the MySQL database.

This was originally the kJudge plugin from a different github(my other github) which used to be for Minecraft version 1.16.5 
and was badly organised. I retook the project to clean it up, update it to Minecraft version 1.18.2, and change parts of it to 
be a lot more efficient and readable.

Dependencies in the plugin folder: [PlotSquared, LuckPerms] (these plugins need to be added to the server for kSJudge to work)

Dependencies used in buid.gradle:

```gradle
dependencies {
    compileOnly 'org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT'
    implementation "net.kyori:adventure-api:4.11.0"
    implementation 'com.intellectualsites.paster:Paster:1.1.4'
    implementation 'org.apache.logging.log4j:log4j-api:2.17.2'
    implementation 'com.plotsquared:PlotSquared-Core:6.9.0' // PlotSquared Core API
    compileOnly('com.plotsquared:PlotSquared-Bukkit:6.9.0') { transitive = false } // PlotSquared Bukkit API
    compileOnly 'net.luckperms:api:5.4' // Luckperms API
}
```

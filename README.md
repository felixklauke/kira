# kira

Kira is a lightweight replacement for the overall much too complex and weird to use jackson object mapper.
It's primary purpose is translating Java Objects into maps that only contain non abstract data types. Those can be serialized in JSON or YAML very easily. 

## Supported formats
- JSON
- YAML
- XML

# Usage

**Maven Repositories:**
```xml
<repositories>
  <!-- Klauke Enterprises Releases -->
  <repository>
    <id>klauke-enterprises-maven-releases</id>
    <name>Klauke Enterprises Maven Releases</name>
    <url>https://repository.klauke-enterprises.com/repository/maven-releases/</url>
  </repository>
	
  <!-- Klauke Enterprises Snapshots -->
  <repository>
    <id>klauke-enterprises-maven-snapshots</id>
    <name>Klauke Enterprises Maven Snapshots</name>
    <url>https://repository.klauke-enterprises.com/repository/maven-snapshots/</url>
  </repository>
</repositories>
```

**Maven dependencies**
```xml
<!-- Kira -->
<dependency>
  <groupId>de.felixklauke.kira</groupId>
  <artifactId>kira-core</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <scope>compile</scope>
</dependency>
```

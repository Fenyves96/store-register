### Usage in another maven project

- Upload to local .m2 repository:
```
mvn install:install-file \
-Dfile="D:\dev\projects\Store Register\target\store-reigster-1.0-jar-with-dependencies.jar" \ --path to the jar
-DgroupId=hu.fenyvesvolgyimate \
-DartifactId=store-register \
-Dversion=1.0 \
-Dpackaging=jar \
-DgeneratePom=true
```

- in the other project pom.xml
```
    <dependencies>
        <dependency>
            <groupId>hu.fenyvesvolgyimate</groupId>
            <artifactId>store-register</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
```



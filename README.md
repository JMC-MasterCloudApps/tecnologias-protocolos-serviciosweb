# tecnologias-protocolos-serviciosweb
Máster Cloud Apps: Desarrollo y despliegue de aplicaciones en la nube.  Tecnologías y protocolos de servicios web.

## Generate api-docs.html

### Manually working with files

File `api-docs.html` can be generated using `openapi-generator-cli.jar` tool.

You can download it with this command:

```
$ wget https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/5.3.0/openapi-generator-cli-5.3.0.jar -O openapi-generator-cli.jar
```

api-docs.yaml file can be downloaded from this URL (with the application executed):

http://localhost:8080/v3/api-docs.yaml

To generate the api-docs.html you can use the following command:

```
$ java -jar openapi-generator-cli.jar generate -i api-docs.yaml -g html
```

### Automated with maven

Execute the following command: 

`$ mvn verify`

**OpenAPI definition**
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
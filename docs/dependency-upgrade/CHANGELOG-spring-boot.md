> Stand: 2026-11-15. Recherche aus den offiziellen Quellen (URLs je Abschnitt). Formulierungen sind teils Paraphrasen der Release Notes, keine wörtlichen Zitate.
> Was davon für dieses Projekt gilt und was im Probelauf verifiziert wurde, steht in [PLAN.md](PLAN.md); wo dieser Text „NOT CONFIRMED" sagt, zählt der Plan.

# Spring Boot 3.4.1 → 4.1.2 changelog (official sources only)

## Read this first: how reliable this is

- **All sources were read via WebFetch**, which returns a small model's rendering of each page, not raw text. Identifiers were requested verbatim, but the prose is paraphrase. Direct `curl` to github.com was refused (403), so I could not cross-check against raw wiki source.
- **Everything below was read in a source** unless marked "NOT CONFIRMED" or "inference".
- **Not read at all:** Jackson 3's own migration guide, the Spring Framework null-safety migration doc, the Hibernate 7.1–7.4 migration guides, and the starter-parent POM (plugin management).
- **Release dates** on GitHub release pages show day and month only; no year was displayed.
- **Discrepancies inside the sources:**
  - The 4.0 wiki lists "Hibernate 7.2", but the v4.0.0 release page shows Hibernate 7.1.8.Final; 7.2.0.Final arrived in 4.0.1.
  - The 4.1 wiki says "Spring Framework 7.0.8" but links to v7.0.7.
  - The 4.0 configuration changelog gives the reason for `spring.jackson.generator` as "Partially replaced by 'spring.jackson.json.read'" and for `spring.jackson.parser` as "...'spring.jackson.json.write'". These look swapped; the migration guide maps `spring.jackson.parser.*` to `spring.jackson.json.read`.
  - The 3.5 notes name `spring.http.codecs.*` as replacement keys, while the 3.5 config changelog's Deprecated table was rendered as `spring.http.codec.*`. The Added table has `spring.http.codecs.*`.
- **Version check:** Maven Central metadata lists 4.1.2 as the newest GA; 4.2.0-RC2 is the newest overall. The patch lines are 3.4.0–3.4.13, 3.5.0–3.5.16, 4.0.0–4.0.9 and 4.1.0–4.1.2. Source: https://repo1.maven.org/maven2/org/springframework/boot/spring-boot/maven-metadata.xml

---

## 1. Minor releases

### 1.1 Spring Boot 3.5
Source: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.5-Release-Notes (body fully covered; the tail beyond 100k characters is sidebar only). Config keys: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.5-Configuration-Changelog

**Upgrading from Spring Boot 3.4**
- **spring-boot-parent:** `spring-boot-parent` is no longer published; define your own dependency management.
- **Actuator 'heapdump' Endpoint:** defaults to `access=NONE`. It needs both `management.endpoints.web.exposure.include` and `management.endpoint.heapdump.access=unrestricted`.
- **Using '.enabled' and Other Boolean Configuration Properties:** values must be exactly `true` or `false`. Previously anything other than `false` counted as enabled.
- **Validation of Profile Naming:** only `-`, `_`, letters and digits are allowed, and names may not start or end with `-` or `_`. Since `3.5.1`, `.`, `+` and `@` are also allowed, and `spring.profiles.validate=false` disables the check.
- **Follow Redirects with TestRestTemplate:** it now uses the same redirect settings as `RestTemplate`. `HttpOption.ENABLE_REDIRECTS` is deprecated; use `TestRestTemplate.withRedirects(…)`.
- **Auto-configured TaskExecutor Names:** the only bean name is `applicationTaskExecutor`; `taskExecutor` is gone. Restore it with an alias via `BeanFactoryPostProcessor`/`registerAlias`, or use `TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME`.
- **Groovy Template Configuration Properties:** `spring.groovy.template.configuration.*` is deprecated in favour of `spring.groovy.template.*`.
- **Redis:** when `spring.data.redis.url` is set, the database comes from the URL (default `0`) and `spring.data.redis.database` is ignored.
- **Prometheus Pushgateway:**
  - Needs `io.prometheus:prometheus-metrics-exporter-pushgateway` instead of `io.prometheus:simpleclient_pushgateway`.
  - `management.prometheus.metrics.export.pushgateway.base-url` becomes `...pushgateway.address` (`host:port`).
  - New keys: `...pushgateway.format`, `...scheme`, `...token`.
- **Native Test with Maven:** the `nativeTest` profile now mirrors `native`. Native tests run only when the Spring Boot and Native Build Tools plugins are declared in the project.
- **ECS Structured Logging JSON:** now a nested format.
- **Apache Pulsar:** default client moves from `3.3.x` to `4.0.x`.
- **Couchbase Capella:** the embedded certificate is no longer picked up automatically. Use a PEM bundle under `spring.ssl.bundle.pem` with `classpath:com/couchbase/client/core/env/capella-ca.pem`, plus `spring.couchbase.env.ssl.bundle`.
- **Bean Conditions and Generic Return Types:** `@ConditionalOnMissingBean` now considers generics on `@Bean` return types. Use `@ConditionalOnMissingBean(Converter.class)` to match regardless of generics.
- **Deprecations from Spring Boot 3.3:** removed.
- **Minimum Requirements Changes:** none.

**New and Noteworthy (22 headings)**
1. Annotations to Register Filter and Servlet: `@ServletRegistration`, `@FilterRegistration`.
2. Load Properties From Environment Variables: `env:` prefix in `spring.config.import`.
3. Customizing Structured Logging Stack Traces: `logging.structured.json.stacktrace.*`.
4. ClientHttpConnector Builder and Configuration Properties: global `WebClient` properties, follow-redirects on by default, `ClientHttpConnectorBuilder`.
5. SSL Support for Service Connections: Cassandra, Couchbase, Elasticsearch, Kafka, MongoDB, RabbitMQ, Redis; also via Testcontainers and Docker Compose.
6. Task Decoration for Scheduled Tasks: a `TaskDecorator` bean is applied to `taskScheduler`, `ThreadPoolTaskSchedulerBuilder` and `SimpleAsyncTaskSchedulerBuilder`.
7. AsyncTaskExecutor with Custom Executor: `spring.task.execution.mode=force`.
8. OpenTelemetry: `OTEL_RESOURCE_ATTRIBUTES` and `OTEL_SERVICE_NAME` are supported; `service.namespace` comes from `spring.application.group`; `service.group` is deprecated; a custom `BatchSpanProcessor` is allowed; export sits under `management.tracing.opentelemetry.export`.
9. Spring Batch: custom `JobParametersConverter`; `spring.batch.jdbc.validate-transaction-state`.
10. Ignore Properties When Using `spring-boot-configuration-processor`: an `ignored` section in `META-INF/additional-spring-configuration-metadata.json`.
11. OAuth 2 Client Auto-configuration: `OAuth2ClientAutoConfiguration`, `ReactiveOAuth2ClientAutoConfiguration`, `OAuth2ClientWebSecurityAutoConfiguration`, `ReactiveOAuth2ClientWebSecurityAutoConfiguration`.
12. Auto-configuration for Bean Background Initialization: `bootstrapExecutor` bean.
13. Redis: `spring.data.redis.lettuce.read-from`.
14. Liquibase: 4.31.0; `spring.liquibase.analytics-enabled`, `spring.liquibase.license-key`.
15. Vibur Connection Pool: `DataSourceBuilder` support; `org.vibur:vibur-dbcp` managed.
16. Testcontainers and Docker Compose support for `lldap/lldap` (`LLdapContainer`).
17. Cloud Native Buildpacks (parent heading).
18. Ubuntu Noble: default builder `paketobuildpacks/builder-noble-java-tiny` (no shell); run image `paketobuildpacks/ubuntu-noble-run-base` if a shell is needed.
19. Docker Config Authentication: plugins read Docker `config.json`, including credential helpers.
20. Triggering Quartz Jobs From the Actuator: POST `/actuator/quartz/jobs/{groupName}/{jobName}`.
21. SSL Bundle Metrics: `ssl.chains`, `ssl.chain.expiry`.
22. Mappings Endpoint: includes `WebMvc.fn` router functions; response structure updated.

**Dependency upgrades (versions as stated)**
- Spring: Authorization Server 1.5.0, Data 2025.0.0, GraphQL 1.4.0, HATEOAS 2.5.0, Integration 6.5.0, LDAP 3.3.0, Security 6.5.0, Session 3.5.0, WS 4.1.0.
- Third-party: Artemis 2.40, AssertJ 3.27, Awaitility 4.3, Brave 6.1.0, Byte Buddy 1.17, Caffeine 3.2, Cassandra Driver 4.19, Commons Codec 1.18, Commons DBCP 2.13, Elasticsearch Client 8.17, Flyway 11.7, GraphQL Java 23.1, Gson 2.13, Hamcrest 3.0, HikariCP 6.3, HtmlUnit 4.11, InfluxDB Java 2.25, Jaybird 6.0, JUnit Jupiter 5.12, Kafka 3.9, Liquibase 4.31, Micrometer 1.15.0, Micrometer Tracing 1.5.0, Mockito 5.17, MongoDB 5.4, MSSQL JDBC 12.10, MySQL 9.2, OpenTelemetry 1.49, Oracle Database 23.7.0.25.01, Pulsar 4.0, Pulsar Reactive 0.6.0, Quartz 2.5, R2DBC MySQL 1.4, Rabbit AMQP Client 5.25, Rabbit Stream Client 0.23, Selenium 4.31, Selenium HtmlUnit 4.30, SnakeYAML 2.4, SQLite JDBC 3.49, Testcontainers 1.21, Thymeleaf Layout Dialect 3.4.0, UnboundID LDAPSDK 7.0, WebJars Locator Lite 1.1, Zipkin Reporter 3.5.0.

**Miscellaneous (57 bullets)**
1. Postgres Docker container `application_name` defaults to `spring.application.name`.
2. Jackson auto-configuration keeps already-registered modules.
3. `server.tomcat.max-parameter-count`.
4. Process info contributor includes virtual thread details (JDK 24+).
5. ECS logging adds markers to `tags`.
6. `OnErrorConsoleStatusListener` installed in Logback.
7. `errors` from `ErrorAttributes` includes `MethodValidationResult` errors.
8. Zipkin uses `ZipkinHttpClientSender` by default, falling back to `URLConnectionSender`.
9. New `@ConditionalOnBooleanProperty`.
10. Bitnami PostgreSQL empty passwords with `ALLOW_EMPTY_PASSWORD` (Docker Compose).
11. `management.server.accesslog.prefix` added (renamed, see 31).
12. AWS Advanced JDBC Wrapper detected from `jdbc:aws-wrapper:` URLs.
13. `spring.validation.method.adapt-constraint-violations`.
14. `VirtualThreadsMetrics` bean when `io.micrometer:micrometer-java21` is present.
15. Native images no longer use `java.home`.
16. `spring.jooq.config`.
17. `SslManagerBundle` factory methods for `TrustManagerFactory`/`TrustManager`.
18. `logging.structured.json.customizer` accepts multiple customizers.
19. `spring.r2dbc.pool.acquire-retry`.
20. `@ConditionalOnBean`/`@ConditionalOnMissingBean` match generic `@Bean` return types.
21. `@ConditionalOnProperty`/`@ConditionalOnBooleanProperty` are repeatable.
22. Unbindable keys under `spring.datasource.dbcp2|hikari|oracleucp|tomcat` removed from metadata.
23. `spring.mvc.contentnegotiation.default-content-types`.
24. `EndpointRequest` can match on HTTP method.
25. `SanitizingFunction.sanitizeValue()` and other builder methods.
26. `@ConfigurationPropertiesBinding` `@Bean` methods can be lambdas.
27. `ApplicationConversionService` detects generic types from converter `@Bean` methods.
28. `CqlOperations` bean.
29. `ReactiveCqlOperations` bean.
30. `ObjectDirectoryMapper` bean.
31. `management.server.{server}.accesslog.prefix` (`jetty`|`tomcat`|`undertow`).
32. `micrometer.observations.annotations.enabled` removed in favour of `management.observations.annotations.enabled`.
33. `spring.kafka.listener.auth-exception-retry-interval`.
34. Buildpacks logging improved.
35. `spring.data.mongodb.protocol`.
36. Logback and Log4j2 respect the console charset.
37. `spring.graphql.path` → `spring.graphql.http.path`; `spring.graphql.sse.timeout` → `spring.graphql.http.sse.timeout`.
38. Zipkin `URLConnectionSender` support removed.
39. Default of `OtlpMetricsProperties.url` removed.
40. `JdbcTemplate` properties for `ignoreWarnings`, `skipResultsProcess`, `skipUndeclaredResults`, `resultsMapCaseInsensitive`.
41. `DataSource` auto-configuration mostly works without `spring-jdbc`.
42. `server.tomcat.use-apr` defaults to `never`.
43. `PollerMetadataCustomizer`.
44. GC info in process info.
45. Readonly flag removed from the `BuildImage` mojo.
46. `server.servlet.session.cookie.same-site=omitted`.
47. `MeterProvider` beans for the OTLP exporters.
48. `spring.ldap.referral`.
49. Customizers for `OtlpHttpSpanExporterBuilder`/`OtlpGrpcSpanExporterBuilder`.
50. `spring.kafka.consumer.max-poll-interval`.
51. `RECORD_COMPONENT` removed as a `@DefaultValue` target.
52. `RestClientSsl` extends the `HttpClientProperties` config.
53. `RestClientAutoConfiguration` applies to reactive apps with virtual threads.
54. `OtlpMeterRegistry` uses a user `OtlpMetricsSender`.
55. Histogram flavor and max buckets per registry.
56. `logging.structured.json.context`.
57. `spring.test.print-condition-evaluation-report=false`.

**Deprecations in 3.5.0**
- `ConditionalOutcome.inverse(…)` / `ConditionOutcome.inverse(ConditionOutcome)`.
- `SignalFX` support.
- `org.springframework.boot.autoconfigure.security.servlet.RequestMatcherProvider` → `org.springframework.boot.actuate.autoconfigure.security.servlet.RequestMatcherProvider`.
- `ClientsConfiguredCondition` → `@ConditionalOnOAuth2ClientRegistrationProperties`.
- `IssuerUriCondition` → `@ConditionalOnIssuerLocationJwtDecoder`.
- `KeyValueCondition` → `@ConditionalOnPublicKeyJwtDecoder`.
- `OnEnabledDevToolsCondition` → `@ConditionalOnEnabledDevTools`.
- `spring.mvc.converters.preferred-json-mapper` → `spring.http.converters.preferred-json-mapper`.
- `spring.codec.log-request-details` and `spring.codec.max-in-memory-size` → `spring.http.codecs.*`.
- `...oauth2.client.servlet.OAuth2ClientAutoConfiguration` → `...oauth2.client.OAuth2ClientAutoConfiguration`.
- `LoggingSystemProperties.getDefaultCharset()` → `getDefaultConsoleCharset()` / `getDefaultFileCharset()`.
- Multi-parameter constructor of `ThreadPoolTaskSchedulerBuilder`.
- `KafkaConnectionDetails…BootstrapServers` methods.
- Default constructor of `ConnectionDetailsFactories`.

**Config changelog 3.5:** 25 deprecated, 64 added, 9 removed. Removed: `management.otlp.metrics.export.resource-attributes` (→ `management.opentelemetry.resource-attributes`), `management.prometheus.metrics.export.histogram-flavor`, the pushgateway `base-url` keys, `spring.datasource.dbcp2.driver`, `spring.datasource.hikari.metrics-tracker-factory`, `spring.datasource.hikari.scheduled-executor`, `spring.datasource.oracleucp.connection-wait-duration-in-millis`, `spring.datasource.oracleucp.hostname-resolver`.

### 1.2 Spring Boot 4.0
Source: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Release-Notes (fully read). Config keys: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Configuration-Changelog (first 100k of 104k characters read)

**Upgrading from Spring Boot 3.5:** the release notes only point to the migration guide (section 2) and recommend upgrading to 3.5 first. There is no minimum-requirements section here.

**New and Noteworthy**
1. Milestones Available from Maven Central (since `4.0.0-M1`).
2. Gradle 9 supported; Gradle 8.x from `8.14`.
3. HTTP Service Clients: auto-configuration for `@HttpExchange` interfaces.
4. API Versioning: `spring.mvc.apiversion.*`, `spring.webflux.apiversion.*`; beans `ApiVersionResolver`, `ApiVersionParser`, `ApiVersionDeprecationHandler`.
5. JmsClient: auto-configured `JmsClient`.
6. Task Decoration: multiple `TaskDecorator` beans become a `CompositeTaskDecorator`.
7. OpenTelemetry starter: `spring-boot-starter-opentelemetry`.
8. Configuration Properties Metadata for External Types: `@ConfigurationPropertiesSource`.
9. SSL Info: `WILL_EXPIRE_SOON` now reports `VALID`.
10. SSL Health: `expiringChains` entry; `management.health.ssl.certificate-validity-warning-threshold`.
11. MongoDB Health Indicators: moved from `spring-boot-data-mongodb` to `spring-boot-mongodb`. MongoDB Properties: `spring.data.mongodb.representation.big-decimal`.
12. Kotlin Serialization: module `spring-boot-kotlinx-serialization-json`, starter `spring-boot-starter-kotlin-serialization` (renamed in 4.0.1, see section 3), `spring.kotlinx.serialization.json.*`.
13. RestTestClient: bound to `MockMvc` with `@SpringBootTest`/`@AutoConfigureMockMvc`, or to a running server.
14. Redis Static Master/Replica: `spring.data.redis.masterreplica.nodes` (Lettuce only).
15. Redis Observability: `MicrometerTracing` replaces `MicrometerCommandLatencyRecorder`.

**Dependency upgrades (as stated)**
- Spring: Micrometer 1.16, Micrometer Tracing 1.6, Reactor 2025.0, Spring AMQP 4.0, Batch 6.0, Data 2025.1, Framework 7.0, GraphQL 2.0, HATEOAS 3.0, Integration 7.0, Kafka 4.0, LDAP 4.0, Pulsar 2.0, REST Docs 4.0, Security 7.0, Session 4.0, WS 5.0.
- Third-party: Artemis 2.43.0, Brave 6.3, Commons Codec 1.19, Commons Lang3 3.19.0, Couchbase Client 3.9, Ehcache3 3.11.1, Elasticsearch Client 9.1, Flyway 11.11, GraphQL 24, Groovy 5.0, Gson 2.13.2, H2 2.4, Hibernate 7.2, Hibernate Validator 9.0, HikariCP 7.0, HtmlUnit 4.16.0, Jackson 3.0, Jakarta Annotation 3.0, Jakarta Persistence 3.2, Jakarta Servlet 6.1, Jakarta Validation 3.1, Jakarta WebSocket 2.2, Jakarta WS RS 4.0, Jedis 6.2.0, Json-smart 2.6.0, Jetty 12.1, Kafka 4.1.0, Kotlin 2.2.20, Kotlin Serialization 1.9, Lettuce 6.8.1.RELEASE, Liquibase 5.0, Maven AntRun Plugin 3.2, Maven Javadoc Plugin 3.12, Mockito 5.20, MongoDB 5.6.0, MSSQL JDBC 13.2, Native Build Tools Plugin 0.11, Neo4j Java Driver 6.0, OpenTelemetry 1.54.0, Pulsar 4.1.0, R2DBC H2 1.1, R2DBC Postgresql 1.1, Rabbit AMQP Client 5.27, Selenium 4.37, Selenium HtmlUnit 4.35, SnakeYAML 2.5, TestContainers 2.0, Tomcat 11.0, XML Maven Plugin 1.2.

**Miscellaneous (23)**
1. `@MeterTag` on `@Counted`/`@Timed` via `ValueExpressionResolver`.
2. MongoDB `@ServiceConnection` supports `MongoDBAtlasLocalContainer`.
3. Better error for binding failure due to a missing class.
4. Several reactive auto-configuration classes renamed.
5. Public members (except constants) removed from auto-configuration classes.
6. JDK `HttpClient`-based clients use virtual threads when `spring.threads.virtual.enabled=true`.
7. `logging.console.enabled`.
8. Log4j 3 config detection improved.
9. DevTools restart resource lookup optimised.
10. `ScheduledTasksObservabilityAutoConfiguration` → `ScheduledTasksObservationAutoConfiguration`.
11. `spring.elasticsearch.api-key`.
12. `ConditionalOnEnabledTracing` → `ConditionalOnEnabledTracingExport`.
13. `management.tracing.enabled` → `management.tracing.export.enabled`.
14. `SanitizableData.getKey()`/`getLowerCaseKey()` never return `null`.
15. `PropertiesConfigAdapter` specialised interfaces.
16. `spring.dao.exceptiontranslation.enabled` → `spring.persistence.exceptiontranslation.enabled`.
17. `server.tomcat.resource.cache-max-size`.
18. `Spring-Boot-Jar-Type: development-tool` excludes dependencies from uber jars.
19. AWS ECS recognised as a `CloudPlatform`.
20. `@ObservationKeyValue` support.
21. Members of configurations imported by auto-configurations are now package-private.
22. `LocalHostWebClient`/`LocalHostWebConnectionHtmlUnitDriver` → `UriBuilderFactoryWebClient`/`UriBuilderFactoryWebConnectionHtmlUnitDriver`.
23. New property for the Elasticsearch client sniffer (name not given on the page).

**Deprecations in 4.0.0**
- `OperationMethod(Method, OperationType)` → `OperationMethod(Method, OperationType, Predicate<Parameter>)`.
- Jackson 2 support (shipped in deprecated form).
- `org.springframework.boot.env.EnvironmentPostProcessor` → `org.springframework.boot.EnvironmentPostProcessor`.

**Config changelog 4.0:** 10 deprecated, 141 added, 179 removed. Relevant rows:
- Deprecated: `spring.http.client.*` and `spring.http.reactiveclient.*` → `spring.http.clients.*` (`connect-timeout`, `read-timeout`, `redirects`, `ssl.bundle`, `imperative.factory`, `reactive.connector`).
- Removed: `management.endpoint.<id>.enabled` for 25 endpoints → `management.endpoint.<id>.access`.
- Removed: `management.endpoints.jackson.isolated-object-mapper` (no replacement listed).
- Removed: `management.health.probes.enabled` → `management.endpoint.health.probes.enabled`.
- Removed: `management.tracing.enabled` → `management.tracing.export.enabled`.
- Removed: `management.tracing.opentelemetry.export.*` → `management.opentelemetry.tracing.export.*`.
- Removed: `management.tracing.brave.span-joining-supported` → `management.brave.tracing.span-joining-supported`.
- Removed: `management.observations.long-task-timer.enabled`.
- Removed: `server.error.*` (`include-binding-errors`, `include-exception`, `include-message`, `include-path`, `include-stacktrace`, `path`, `whitelabel.enabled`) → `spring.web.error.*`.
- Removed: `server.servlet.encoding.*` → `spring.servlet.encoding.*`.
- Removed: all `server.undertow.*`.
- Removed: `spring.dao.exceptiontranslation.enabled` → `spring.persistence.exceptiontranslation.enabled`.
- Removed: `spring.jackson.read` → `spring.jackson.json.read`; `spring.jackson.write` → `spring.jackson.json.write`; `spring.jackson.generator`, `spring.jackson.parser` (see discrepancy note).
- Removed: `spring.test.mockmvc.webclient.enabled` → `spring.test.mockmvc.htmlunit.webclient.enabled`; `spring.test.mockmvc.webdriver.enabled` → `spring.test.mockmvc.htmlunit.webdriver.enabled`.
- Removed: `spring.test.observability.auto-configure` (superseded by `spring.test.metrics.export` and `spring.test.tracing.export`).
- Removed: `spring.test.webclient.mockrestserviceserver.enabled` → `spring.test.restclient.mockrestserviceserver.enabled`; `spring.test.webclient.register-rest-template`.
- No removed or deprecated rows start with `spring.mvc`, `spring.servlet`, `spring.web.`, `spring.jpa`, `spring.data.jpa`, `spring.datasource`, `spring.sql`, `spring.devtools`, `spring.main`, `spring.task`, `spring.threads`, `spring.docker`, `spring.testcontainers`, `logging.` or `management.info`.
- Added (selection): `spring.jackson.json.read`, `spring.jackson.json.write`, `spring.jackson.cbor.*`, `spring.jackson.xml.*`, `spring.jackson.datatype.datetime`, `spring.jackson.find-and-add-modules`, `spring.jackson.use-jackson2-defaults`, `spring.jackson2.*` (deprecated), `spring.mvc.apiversion.*`, `spring.servlet.encoding.*`, `spring.http.clients.*`, `spring.http.serviceclient`, `spring.test.mockmvc.htmlunit.*`.

### 1.3 Spring Boot 4.1
Source: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.1-Release-Notes (body covered in the first 100k characters; the last 6k were not read and are probably sidebar). Config keys: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.1.0-Configuration-Changelog

**Upgrading from Spring Boot 4.0**
- **Deprecations from Spring Boot 4.0:** removed.
- **Deprecated Derby Support:** Apache Derby is retired; move to H2 or HSQL.
- **Layertools Support:** the layertools jar mode is removed; use `tools`.
- **Skip Tests and AOT Processing:** `-DskipTests` no longer skips test AOT processing in Maven. The plugin only honours `maven.test.skip`.
- **Spring Data JPA Bootstrap Modes:** for `spring.data.jpa.repositories.bootstrap-mode`, `deferred` now fails if no `AsyncTaskExecutor` bean exists (the page spells it `AsycTaskExecutor`). `lazy` no longer sets the bootstrap executor on `LocalContainerEntityManagerFactoryBean`.
- **Tomcat Temporary Directory:** deleted on context close; set `server.tomcat.basedir` to keep it.
- **ReactorClientHttpRequestFactoryBuilder and ReactorClientHttpConnectorBuilder defaults:** `proxyWithSystemProperties()` is applied; change with `withHttpClientDefaults`/`withoutHttpClientDefaults`.
- **Minimum Requirements Changes:** jOOQ `3.20`, which requires Java 21+.

**New and Noteworthy**
- Spring gRPC Support: Netty or Servlet HTTP/2; there is a migration guide for Spring gRPC 1.0 users.
- Jackson: `spring.jackson.read.*` and `spring.jackson.write.*` for features common to CBOR/JSON/XML; a `HandlerInstantiator` backed by application beans.
  - Factory Configuration and Customization: `spring.jackson.factory.*`, `JsonFactoryBuilderCustomizer`, `CborFactoryBuilderCustomizer`, `XmlFactoryBuilderCustomizer`.
- Specify the Encoding When Importing Configuration Files: `spring.config.import=classpath:import.properties[encoding=utf-8]`.
- HTTP Client Cookie Handling: `TestRestTemplate.withCookieHandling`, `spring.http.clients.cookie-handling`.
- HTTP Client SSRF Mitigation with `InetAddressFilter`.
- Observability:
  - Context Propagation of Async methods (`@Async`).
  - Auto-configured Conventions: Kafka and Rabbit `…ObservationConvention` beans; meter conventions for `JvmMemoryMetrics`, `JvmThreadMetrics`, `ClassLoaderMetrics`, `ProcessorMetrics`.
  - OpenTelemetry Enhancements: `management.opentelemetry.enabled`, `management.opentelemetry.tracing.sampler`, `management.opentelemetry.tracing.limits.*`, `management.opentelemetry.logging.limits.*`, exemplars, SSL bundles for OTLP.
  - Environment Variables: most OTel environment variables are read.
- SSL Support for RabbitMQ Streams: `spring.rabbitmq.stream.ssl.enabled`, `spring.rabbitmq.stream.ssl.bundle`.
- Service Connections to RabbitMQ Streams: `@ServiceConnection(type = RabbitStreamConnectionDetails.class)`.
- File Rotation Support for Log4j: `size`, `time`, `size-and-time`, `cron`.
- Embedded LDAP: `spring.ldap.embedded.ssl.bundle`.
- SimpleMessageListenerContainer Support: a configurer for `SimpleJmsMessageListener`.
- Docker Compose: `spring.docker.compose.start.log-level`.
- OAuth2 Resource Server: `spring.security.oauth2.resourceserver.jwt.authorities-claim-expressions`.
- MongoDB Support for Spring Batch: `spring-boot-batch-data-mongo`, `spring.batch.data.mongo.schema.initialize`.
- Lazy JDBC connection Fetching Support: `spring.datasource.connection-fetch` (`eager`|`lazy`), `LazyConnectionDataSourceProxy`.
- @RedisListener Auto-configuration: `spring.data.redis.listener.*`, `RedisMessageListenerContainerConfigurer`.
- Info Actuator Endpoint: `process.uptime`, `process.startTime`, `process.currentTime`, `process.timezone`, `process.locale`, `process.workingDirectory`.
- Gradle Plugin Updates: `bootBuildImage --environment`; `BuildInfo` writes `META-INF/build-info.properties`.
- Maven Plugin Updates: layers configuration from `META-INF/spring/layers/<name>.xml` on the classpath.
- Restoration of Spock Support (Spock 2.4, Groovy 5).

**Dependency upgrades (as stated)**
- Spring: Micrometer 1.17.0, Micrometer Tracing 1.7.0, Reactor Bom 2025.0.6, Spring AMQP 4.1.0, Spring Data Bom 2026.0.0, Spring Framework 7.0.8, Spring GraphQL 2.0.4, Spring gRPC 1.1.0, Spring HATEOAS 3.1.0, Spring Integration 7.1.0, Spring Kafka 4.1.0, Spring LDAP 4.1.0, Spring Security 7.1.0, Spring Session 4.1.0.
- Third-party: ActiveMQ 6.2.6, Artemis 2.53.0, Byte Buddy 1.18.10, Commons Codec 1.21.0, Commons DBCP2 2.14.0, Commons Lang3 3.20.0, Commons Pool2 2.13.1, Couchbase Client 3.11.3, Ehcache3 3.12.0, Flyway 12.4.0, Git Commit ID Maven Plugin 9.2.0, Grpc Java 1.80.0, Hibernate 7.4, Hibernate Validator 9.1, HtmlUnit 4.21.0, HttpClient5 5.6.1, HttpCore5 5.4.2, Infinispan 16.1.4, Jedis 7.4.1, jOOQ 3.21.5, Kotlin 2.3.21, Kotlin Serialization 1.11.0, Lettuce 7.5.2.RELEASE, Maven Assembly Plugin 3.8.0, Maven Compiler Plugin 3.15.0, Maven Dependency Plugin 3.15.0, Maven Jar Plugin 3.5.0, Maven Resources Plugin 3.5.0, Maven Source Plugin 3.4.0, Maven War Plugin 3.5.1, Mockito 5.23.0, MongoDB 5.8.0, MySQL 9.7.0, OpenTelemetry 1.62.0, Oracle Database 23.26.2.0.0, Pooled JMS 3.2.2, Prometheus Client 1.5.1, Protobuf Common Protos 2.70.0, Protobuf Maven Plugin 5.1.4, Rabbit AMQP Client 5.30.0, Rabbit Stream Client 1.6.0, Selenium 4.43.0, Selenium HtmlUnit 4.43.0, SnakeYAML 2.6, SQLite JDBC 3.53.2.0, Thymeleaf Layout Dialect 4.0.1, Versions Maven Plugin 2.21, XmlUnit2 2.11.

**Miscellaneous (26)**
1. Web servers' temp directories deleted on context close.
2. `server.compression.additional-mime-types`.
3. `spring.webflux.default-html-escape`.
4. Constructor-bound `Optional` parameters bind as empty instead of `null`.
5. Empty YAML objects are kept.
6. Default properties bind when an empty property is defined.
7. `EntityManagerFactoryBuilder` can add `PersistenceUnitPostProcessor`s.
8. `FailureAnalyzedException`.
9. `spring.data.rest.return-body-on-delete`.
10. Mustache uses `Charset`.
11. Customizers for `OtlpHttpLogRecordExporterBuilder`/`OtlpGrpcLogRecordExporterBuilder`.
12. Kotlin extensions for `TestEntityManager`.
13. OAuth2 resource server in non-web applications.
14. `spring.jpa.bootstrap` (async background bootstrap of `LocalContainerEntityManagerFactoryBean`).
15. Console ANSI on by default on Windows 11+.
16. `KafkaTemplate` `closeTimeout`/`allowNonTransactional`.
17. Docker Compose support for `docker.elastic.co/elasticsearch/elasticsearch`.
18. `info` endpoint returns truststore certificates.
19. `management.otlp.metrics.export.compression-mode=gzip`.
20. `publishMaxGaugeForHistograms` on `OTLPRegistry`.
21. `management.tracing.exemplars.filter`.
22. Truststore certificate expiry metrics.
23. AssertJ `Assert` implementations use `@CheckReturnValue`.
24. Failure analyzer for Testcontainers without a usable Docker environment.
25. Beans from `MockRestServiceServiceAutoConfiguration` are `@ConditionalOnMissingBean`.
26. New `@AutoConfigureWebServer` test annotation.

**Deprecations in 4.1.0**
- `org.springframework.boot.jdbc.DatabaseDriver.DERBY`, `org.springframework.boot.jdbc.EmbeddedDatabaseConnection.DERBY`.
- Dynatrace V1 API properties and classes.
- DevTools `LiveReload` (no replacement).

**Config changelog 4.1:** 1 default changed, 5 deprecated, 105 added, 7 removed.
- Deprecated: `spring.devtools.livereload.enabled`, `spring.devtools.livereload.port` ("Deprecated with no replacement"); three `management.dynatrace.metrics.export.v1.*` keys.
- Removed: `logging.file.clean-history-on-start`, `logging.file.max-history`, `logging.file.max-size`, `logging.file.total-size-cap` → `logging.logback.rollingpolicy.*`; `logging.pattern.rolling-file-name` → `logging.logback.rollingpolicy.file-name-pattern`; `spring.data.redis.lettuce.cluster.refresh.adaptive`; `spring.datasource.oracleucp.role-name`.
- Added (selection): `spring.jackson.factory.constraints.read.*`/`.write.*`, `spring.jpa.bootstrap`, `spring.datasource.connection-fetch`, `spring.http.clients.cookie-handling`, `server.compression.additional-mime-types`.
- I did not ask which key had its default changed.

### 1.4 Spring Framework 7.0 (pulled in by Boot 4.0)
Source: https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-7.0-Release-Notes

**Upgrading From Spring Framework 6.2**
- **Baseline Upgrades:** JDK 17 minimum (JDK 25 recommended); Jakarta EE 11; Servlet 6.1 (Tomcat 11.0, Jetty 12.1); JPA 3.2 (Hibernate ORM 7.1/7.2); Bean Validation 3.1 (Hibernate Validator 9.0/9.1); Netty 4.2; Kotlin 2.2; GraalVM 25; JUnit 6.
- **Removed APIs:**
  - `spring-jcl` (replaced by Apache Commons Logging 1.3.0).
  - `javax.annotation`/`javax.inject` support (use `jakarta.*`).
  - Path mapping options: `suffixPatternMatch`, `registeredSuffixPatternMatch`, `trailingSlashMatch`, `favorPathExtension`, `ignoreUnknownPathExtensions`, `matchOptionalTrailingSeparator`.
  - Undertow support.
  - `ListenableFuture`, `webjars-locator-core`, OkHttp3 support, Theme support.
  - `HttpComponentsClientHttpRequestFactory#setConnectTimeout`.
- **Breaking Changes:**
  - `HttpHeaders` no longer extends `MultiValueMap` (`asMultiValueMap` is a deprecated fallback).
  - SpEL max operations limit of 10,000 since 7.0.8 (`spring.expression.maxOperations`).
  - `SimpleEvaluationContext` has no compilation by default since 7.0.9.
  - `SpringExtension` uses a test-method-scoped `ExtensionContext`. Opt out with `@SpringExtensionConfig(useTestClassScopedExtensionContext = true)` or `spring.test.extension.context.scope=test_class`.
- **Deprecations:** `RestTemplate` (documentation-level; `@Deprecated` in 7.1), `<mvc:*>` XML namespace, Kotlin script templating, JUnit 4 support (`SpringRunner` etc.), Jackson 2.x support, `PathMatcher` in Spring MVC, `HandlerMappingIntrospector`, some `HttpStatus` values (RFC 9110), `org.springframework.web.servlet.view.document`/`.feed`, `<lang:*>`, BeanShell.
- **Null Safety:** Spring's JSR 305-semantics nullness annotations are deprecated in favour of JSpecify. `MethodParameter#isOptional` only checks local `@Nullable`. The page does not name `org.springframework.lang.Nullable` explicitly.
- **Servlet 6.1 and WebSocket 2.2:** `MockHttpServletRequest`/`MockHttpServletResponse` aligned with the Servlet API (null header names and values).
- **JPA 3.2 and Hibernate ORM 7.1/7.2:** `EntityManagerFactory` and shared `EntityManager` injectable via `@Autowired`; native Hibernate support moved from `orm.hibernate5` to `orm.jpa.hibernate`.
- **Persistence unit management:** `MutablePersistenceUnitInfo` no longer implements `PersistenceUnitInfo`; use `SpringPersistenceUnitInfo`.
- **JMS destination handling:** default is `SimpleDestinationResolver`.
- **Jackson 3.x support:** Jackson 3 is the default with fallback to 2.x. 2.x auto-detection is planned to be disabled in 7.1 and removed in 7.2. The package is `tools.jackson`; `jackson-annotation` types such as `@JsonView` and `@JsonTypeInfo` stay in `com.fasterxml.jackson`. `Jackson2ObjectMapperBuilder` is replaced by `JsonMapper.builder()`.
- **kotlinx.serialization support:** `KotlinDetector#hasSerializableAnnotation`.
- **Google Gson support in WebFlux:** `GsonEncoder`/`GsonDecoder`.
- **GraalVM Native Applications:** unified reachability metadata format; resource hints use globs; `ExecutableMode.INTROSPECT` and most `MemberCategory` values deprecated.
- **CORS Pre-Flight requests behavior change:** preflight requests are no longer rejected when the CORS configuration is empty (#31839).
- **RestClient behavior changes for Reactor client:** `ReactorClientHttpRequestFactory` honours `https.proxyHost`/`https.proxyPort`.

**New and Noteworthy (headings)**
- Null Safety (JSpecify).
- Class-File API usage for Java 24+ apps.
- Programmatic Bean Registration (`BeanRegistrar`).
- Optional support with null-safe and Elvis operators in SpEL.
- Consistent proxy type defaulting (`@Proxyable`).
- Resilience features (`RetryTemplate`, `@Retryable`, `@ConcurrencyLimit`, `@EnableResilientMethods`).
- Embracing JPA 3.2 and Hibernate `StatelessSession`.
- Introducing `JmsClient` and revisiting `JdbcClient`.
- Trusted Packages for Jackson JMS Converters.
- API Versioning.
- HTTP Interface Client configuration (`@ImportHttpServices`).
- HTTP Interface Client support for InputStream and OutputStream.
- PathPattern matching improved (`AntPathMatcher` deprecated).
- Easier message converters configuration with `HttpMessageConverters` (`WebMvcConfigurer#configureMessageConverters(HttpMessageConverters.ServerBuilder)`).
- Pausing of Test Application Contexts (`spring.test.context.cache.pause`).
- Improved Dependency Injection in `@Nested` Test Class Hierarchies.
- Bean Overrides for non-singleton Beans (`@MockitoBean`, `@MockitoSpyBean`, `@TestBean`).
- `RestTestClient`.
- Context Propagation for Kotlin Coroutines.

The page does not mention multipart handling, `ProblemDetail`, `MockMvcTester` or `CorsRegistry`.

---

## 2. Spring Boot 4.0 Migration Guide (full outline)
Source: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide (the body ends with "@MockBean and @SpyBean Removal"; everything after is sidebar)

**Before You Start**
- **Upgrade to the Latest `3.5.x` Version:** and remove calls to deprecated APIs first.
- **Review Dependencies:** compare the 3.5.x and 4.0.x managed dependencies. Unmanaged ones (for example Spring Cloud) need an explicitly compatible version.
- **Review System Requirements:** Java 17+ (latest LTS recommended), Kotlin 2.2+, GraalVM native-image 25+, Jakarta EE 11, Servlet 6.1 baseline, Spring Framework 7.x. The guide gives no Maven or Gradle minimum.
- **Review Deprecations from Spring Boot 3.x:** all removed in 4.0.

**Upgrade to Spring Boot 4**
- **Configuration Properties Migration:** temporarily add `org.springframework.boot:spring-boot-properties-migrator` (scope `runtime`); remove it afterwards.
- **Features Removed From This Release:**
  - Undertow (no Servlet 6.1 support).
  - Pulsar Reactive auto-configuration and dependency management.
  - Embedded Executable Uber Jar Launch Scripts.
  - Spring Session Hazelcast.
  - Spring Session MongoDB.
  - Spock Integration (restored in 4.1).
- **Module Dependencies:**
  - **Main Code:** modules are `spring-boot-<technology>`, packages `org.springframework.boot.<technology>`, starters `spring-boot-starter-<technology>`. Technologies that previously needed no starter may need one now, for example `spring-boot-starter-flyway` and `spring-boot-starter-liquibase`.
  - **Test Code:** modules `spring-boot-<technology>-test`, packages `org.springframework.boot.<technology>.test`, starters `spring-boot-starter-<technology>-test`. Every test starter includes `spring-boot-starter-test`. Example given: `@WithMockUser` needs `spring-boot-starter-security-test`.
  - **Starters table** (main | test); the test starter is the main name plus `-test` unless noted:
    - Core: `spring-boot-starter-aspectj`, `spring-boot-starter-cloudfoundry`, `spring-boot-starter-validation`, `spring-boot-starter-kotlinx-serialization-json`.
    - Web Server (no test starters): `spring-boot-starter-jetty`, `spring-boot-starter-reactor-netty`, `spring-boot-starter-tomcat`.
    - Web Client: `spring-boot-starter-restclient`, `spring-boot-starter-webclient`.
    - Web: `spring-boot-starter-jersey`, `-graphql`, `-hateoas`, `-restdocs` (since 4.0.4; no test starter listed), `-session-data-redis`, `-session-jdbc`, `spring-boot-starter-webmvc` | `spring-boot-starter-webmvc-test`, `-webflux`, `-webservices`.
    - Database: `-cassandra`, `-couchbase`, `-elasticsearch`, `-flyway`, `-jdbc`, `-jooq`, `-liquibase`, `-ldap`, `-mongodb`, `-neo4j`, `-r2dbc`.
    - Spring Data: `-data-cassandra` (`-reactive`), `-data-couchbase` (`-reactive`), `-data-elasticsearch`, `-data-jdbc`, `spring-boot-starter-data-jpa` | `spring-boot-starter-data-jpa-test`, `-data-ldap`, `-data-mongodb` (`-reactive`), `-data-neo4j`, `-data-r2dbc`, `-data-redis` (`-reactive`), `-data-rest`.
    - IO: `-hazelcast`, `-mail`, `-quartz`, `-sendgrid`, `-cache`, `-batch-jdbc`, `-batch`.
    - JSON: `-gson`, `spring-boot-starter-jackson` | `spring-boot-starter-jackson-test`, `-jsonb`.
    - Messaging: `-activemq`, `-artemis`, `-jms`, `-rsocket`, `-amqp`, `-integration`, `-kafka`, `-pulsar`, `-websocket`.
    - Security: `-security`, `-security-oauth2-authorization-server`, `-security-oauth2-client`, `-security-oauth2-resource-server`, `-security-saml2`.
    - Templating: `-freemarker`, `-groovy-templates`, `-mustache`, `-thymeleaf`.
    - Production-Ready: `spring-boot-starter-actuator` | `spring-boot-starter-actuator-test`, `-micrometer-metrics`, `-opentelemetry`, `-zipkin`.
  - **Modules table** (main | test module where one exists):
    - Core: `spring-boot-cloudfoundry`, `spring-boot-validation`, `spring-boot-kotlin-serialization`, `spring-boot-reactor`.
    - Web server: `spring-boot-jetty`, `spring-boot-reactor-netty`, `spring-boot-tomcat`.
    - Web client: `spring-boot-restclient` | `-test`, `spring-boot-webclient` | `-test`.
    - Web: `spring-boot-jersey`, `spring-boot-graphql` | `-test`, `spring-boot-hateoas`, `spring-boot-restdocs`, `spring-boot-session-data-redis`, `spring-boot-session-jdbc`, `spring-boot-webmvc` | `spring-boot-webmvc-test`, `spring-boot-webflux` | `-test`, `spring-boot-webservices` | `-test`.
    - Database: `spring-boot-cassandra`, `-couchbase`, `-elasticsearch`, `-flyway`, `spring-boot-jdbc` | `-test`, `spring-boot-jooq` | `-test`, `-liquibase`, `-ldap`, `-mongodb`, `-neo4j`, `-r2dbc`.
    - Spring Data: `spring-boot-data-<x>` | `-test` for cassandra, couchbase, elasticsearch, jdbc, jpa, ldap, mongodb, neo4j, r2dbc, redis; `spring-boot-data-rest` (no test module).
    - IO: `spring-boot-hazelcast`, `-mail`, `-quartz`, `-sendgrid`, `spring-boot-cache` | `-test`, `-batch-jdbc`, `-batch`.
    - JSON: `spring-boot-gson`, `spring-boot-jackson`, `spring-boot-jsonb`.
    - Messaging: `spring-boot-activemq`, `-artemis`, `-jms`, `spring-boot-rsocket` | `-test`, `-amqp`, `-integration`, `-kafka`, `-pulsar`, `-websocket`.
    - Security: `spring-boot-security` | `-test`, `-security-oauth2-authorization-server`, `-security-oauth2-client`, `-security-oauth2-resource-server`, `-security-saml2`.
    - Templating: `spring-boot-freemarker`, `-groovy-templates`, `-mustache`, `-thymeleaf`.
    - Production-Ready: `spring-boot-health`, `spring-boot-micrometer-metrics` | `-test`, `spring-boot-micrometer-observation`, `spring-boot-micrometer-tracing` | `-test`, `-micrometer-tracing-brave`, `-micrometer-tracing-opentelemetry`, `spring-boot-opentelemetry`, `spring-boot-zipkin`.
  - **Classic Starters:** `spring-boot-starter-classic` (in place of `spring-boot-starter`) and `spring-boot-starter-test-classic` (in place of `spring-boot-starter-test`) bring in all modules, giving a Boot-3-like classpath. Intended as a transition aid.
  - **Deprecated Starters:**
    - `spring-boot-starter-oauth2-authorization-server` → `spring-boot-starter-security-oauth2-authorization-server`
    - `spring-boot-starter-oauth2-client` → `spring-boot-starter-security-oauth2-client`
    - `spring-boot-starter-oauth2-resource-server` → `spring-boot-starter-security-oauth2-resource-server`
    - `spring-boot-starter-web` → `spring-boot-starter-webmvc`
    - `spring-boot-starter-web-services` → `spring-boot-starter-webservices`
  - **Package Organization:** each module has the root package `org.springframework.boot.<module>`.
  - **Considerations For Projects Building Their Own Starters:** do not support Boot 3 and Boot 4 in one artifact.
- **Migration Strategy:** either adopt the modular starters directly, or go in two steps: first the classic starters to fix imports, then replace them with specific starters.
- **Review Other Release Notes:** Spring AMQP 4.0, Batch 6.0, Data 2025.1, GraphQL 2.0, Framework 7.0, Integration 7.0, Kafka 4.0, Pulsar 2.0, Security 7.0, REST Docs 4.0, Session 4.0, WS 5.0.

**Upgrading Core Features**
- **JSpecify Nullability annotations:** Boot 4 is annotated with JSpecify, which can break null-checker and Kotlin builds. Users of `org.springframework.lang` annotations should follow the Framework null-safety migration guide.
- **Logback Default Charset:** log files default to `UTF-8`; console uses `Console#charset()` if available, otherwise `UTF-8`.
- **BootstrapRegistry and EnvironmentPostProcessor package changes:** `BootstrapRegistry` moves from `org.springframework.boot` to `org.springframework.boot.bootstrap`. `EnvironmentPostProcessor` moves from `org.springframework.boot.env` to `org.springframework.boot`. Update `spring.factories`.
- **Property Mapper API Changes:** `PropertyMapper` no longer calls adapters or predicates for `null` sources. `alwaysApplyingWhenNonNull()` is removed; use `always()` before `to(...)` to map nulls.
- **DevTools Live Reload Support:** disabled by default; enable with `spring.devtools.livereload.enabled=true`.

**Upgrading Dependencies and Build Plugins**
- **Optional Dependencies in Maven:** no longer packaged in uber jars; use `<includeOptional>true</includeOptional>` to include them.
- **AOP Starter POM:** `spring-boot-starter-aop` → `spring-boot-starter-aspectj`. Only needed if you use `org.aspectj.lang.annotation.Aspect` and similar.
- **Dependency Management for Spring Retry:** removed; declare a version or use Framework's retry.
- **Dependency Management for Spring Authorization Server:** `spring-authorization-server.version` no longer works; use `spring-security.version`.
- **Classic Uber-Jar Loader Support:** removed; delete `<loaderImplementation>CLASSIC</loaderImplementation>`.
- **Cyclone DX Gradle Plugin:** minimum `3.0.0`.

**Upgrading Jackson**
- Jackson 3 is the default. Group IDs and packages change from `com.fasterxml.jackson` to `tools.jackson`. The exception is `jackson-annotations`, which keeps group `com.fasterxml.jackson.core` and package `com.fasterxml.jackson.annotation`.
- Class renames: `JsonObjectSerializer` → `ObjectValueSerializer`; `JsonValueDeserializer` → `ObjectValueDeserializer`; `Jackson2ObjectMapperBuilderCustomizer` → `JsonMapperBuilderCustomizer`.
- Annotation renames: `@JsonComponent` → `@JacksonComponent`; `@JsonMixin` → `@JacksonMixin`. Supporting classes replace `Json` with `Jackson`.
- Properties: `spring.jackson.read.*` → `spring.jackson.json.read`; `spring.jackson.write.*` → `spring.jackson.json.write`. `spring.jackson.parser.*` maps to `spring.jackson.json.read` where a `JsonReadFeature` exists; otherwise use a `JsonMapperBuilderCustomizer`.
- All classpath modules are now registered. `spring.jackson.find-and-add-modules=false` restores the Boot 3 behaviour.
- **Format-Specific Mappers:** a `JsonMapper` and an `XmlMapper` are auto-configured. To replace them, define a `JsonMapper` or `XmlMapper` bean, not an `ObjectMapper` bean.
- **Jackson 2 Compatibility:** `spring.jackson.use-jackson2-defaults=true` aligns the `JsonMapper` defaults with Boot 3.x. The deprecated stop-gap module `org.springframework.boot:spring-boot-jackson2` uses properties under `spring.jackson2.*`.

**Upgrading Actuator and Production-Ready Features**
- **Removed Support for javax.annotations.NonNull and org.springframework.lang.Nullable:** endpoint parameters can no longer use `org.springframework.lang.Nullable` to mark a parameter optional. Use `org.jspecify.annotations.Nullable`.
- **Liveness and Readiness Probes:** enabled by default, so health exposes the `liveness` and `readiness` groups. Disable via `management.endpoint.health.probes.enabled`.

**Upgrading Web Features**
- **Fonts Added to Common Static Locations:** `PathRequest#toStaticResources` now includes `/fonts/**`. Exclude with `.excluding(StaticResourceLocation.FONTS)`.
- **Spring Session:** `spring.session.redis.*` → `spring.session.data.redis.*`; `spring.session.mongodb.*` → `spring.session.data.mongodb.*`.
- **HttpMessageConverters Deprecation:** `org.springframework.boot.http.converter.autoconfigure.HttpMessageConverters` is deprecated. Contributing `HttpMessageConverter` beans directly is no longer supported. Use `ClientHttpMessageConvertersCustomizer` and `ServerHttpMessageConvertersCustomizer`.
- **Forwarded Headers Strategy:** `server.forward-headers-strategy` no longer applies to WAR deployments in an external container. Register a `FilterRegistrationBean<ForwardedHeaderFilter>` yourself.
- **Jersey:** Jersey 4.0 has no Jackson 3 support; add `spring-boot-jackson2`.
- **Tomcat:** for WAR deployment, replace `spring-boot-starter-tomcat` with `spring-boot-starter-tomcat-runtime`.

**Upgrading Data Features**
- **Elasticsearch Client:** `RestClient` auto-configuration → `Rest5Client`; `RestClientBuilderCustomizer` → `Rest5ClientBuilderCustomizer`. `org.elasticsearch.client:elasticsearch-rest-client` and `-sniffer` are no longer managed.
- **Persistence Modules:** new `spring-boot-persistence` module. `@EntityScan` is now `org.springframework.boot.persistence.autoconfigure.EntityScan`. `spring.dao.exceptiontranslation.enabled` → `spring.persistence.exceptiontranslation.enabled`.
- **MongoDB:** connection keys move from `spring.data.mongodb.*` to `spring.mongodb.*`. Management keys change `mongo` to `mongodb`. Keys that require Spring Data keep `spring.data.mongodb.*`.
  - MongoDB UUID and BigDecimal Representations: must be set explicitly (`spring.mongodb.representation.uuid`, `spring.data.mongodb.representation.big-decimal`).
- **Hibernate Dependency Management:** `hibernate-jpamodelgen` → `hibernate-processor`. `hibernate-proxool` and `hibernate-vibur` are no longer managed.

**Upgrading Messaging Features**
- **Kafka Streams Customization:** `StreamBuilderFactoryBeanCustomizer` removed; use `StreamsBuilderFactoryBeanConfigurer`.
- **Spring Kafka Retry Features:** `spring.kafka.retry.topic.backoff.random` → `spring.kafka.retry.topic.backoff.jitter`.
- **Spring AMQP Retry Features:** `RabbitRetryTemplateCustomizer` → `RabbitTemplateRetrySettingsCustomizer` / `RabbitListenerRetrySettingsCustomizer`.

**Upgrading IO Features**
- **Spring Batch:** `spring-boot-starter-batch` now runs in memory. Use `spring-boot-starter-batch-jdbc` to keep database metadata.

**Upgrading Testing Features**
- **Mockito Captor and Mock Annotations:** `MockitoTestExecutionListener` is removed. If `@Mock`/`@Captor` fields stop working, use Mockito's `MockitoExtension`.
- **Using MockMVC and @SpringBootTest:** `@SpringBootTest` no longer provides MockMvc; add `@AutoConfigureMockMvc`. HtmlUnit settings move to `@AutoConfigureMockMvc(htmlUnit = @HtmlUnit(webClient = false, webDriver = false))`.
- **Using WebClient or TestRestTemplate and @SpringBootTest:** these beans are no longer provided. Add `@AutoConfigureTestRestTemplate` plus `org.springframework.boot:spring-boot-resttestclient` and `org.springframework.boot:spring-boot-restclient`, or switch to `RestTestClient` with `@AutoConfigureRestTestClient`.
- **TestRestTemplate Failures:** add test-scoped `spring-boot-resttestclient`. The class is now `org.springframework.boot.resttestclient.TestRestTemplate`.
- **@PropertyMapping Annotation:** moved from `org.springframework.boot.test.autoconfigure.properties` to `org.springframework.boot.test.context`. `Skip` is now `org.springframework.boot.test.context.PropertyMapping.Skip`.
- **@MockBean and @SpyBean Removal:** replaced by `@MockitoBean`/`@MockitoSpyBean`. Field-level use on test classes is a direct swap. They are not supported in `@Configuration`/`@TestConfiguration` classes. For shared mocks, declare `@MockitoBean(types = {OrderService.class, UserService.class})` on the test class or on a custom meta-annotation.

The guide does not mention Testcontainers, `@ServiceConnection`, Hibernate bytecode enhancement, `native-maven-plugin` or a Maven minimum version.

---

## 3. Patch lines
Source: https://github.com/spring-projects/spring-boot/releases/tag/v<version> for each of the 40 pages (3.4.2–3.4.13, 3.5.1–3.5.16, 4.0.0–4.0.9, 4.1.0–4.1.2). Counts are as rendered by the fetch model.

**3.4.2 – 3.4.13** (12 releases, 23 Jan to 18 Dec)
- Pure maintenance: 4–19 bug fixes per release, plus documentation and 20–38 dependency bumps each.
- Spring Framework went 6.2.2 → 6.2.15, Hibernate 6.6.5 → 6.6.39.Final, Tomcat 10.1.36 → 10.1.50.
- Noteworthy notes:
  - 3.4.3: Guava is no longer managed (Prometheus Client 1.3.6).
  - 3.4.4: Tomcat APR is off by default on Java 24+.
  - 3.4.7: Tomcat 10.1.42 adds multipart limits, configurable via `server.tomcat.max-part-count` and `server.tomcat.max-part-header-size`.
  - 3.4.12: Testcontainers fix for Docker 29.0.0 (#48104).
- Relevant fixes: nested test classes not inheriting `@DataJpaTest` properties (#44407, 3.4.4); ddl-auto default with multiple DataSources (#44516, 3.4.4); `spring.jpa.hibernate.ddl-auto` suggested values (#45350, 3.4.6); `WebSecurityCustomizer` beans excluded by `@WebMvcTest` (#47255, 3.4.12).

**3.5.1 – 3.5.16** (16 releases, 19 Jun to 25 Jun the following year)
- Same character: 0–21 bug fixes per release. 3.5.2 and 3.5.3 are single-fix hot-fixes for property-source/binder regressions (#46032, #46040). 3.5.16 contains only 3 dependency upgrades.
- Spring Framework 6.2.8 → 6.2.19, Hibernate 6.6.18 → 6.6.53.Final, Tomcat 10.1.42 → 10.1.55.
- Noteworthy notes:
  - 3.5.1: Tomcat multipart limits (as 3.4.7).
  - 3.5.8: Docker 29 / Testcontainers fix.
  - 3.5.13: Jackson moves to 2.21.2 because 2.19.x and 2.20.x are end-of-support.
- Relevant fixes: `DefaultErrorAttributes` change altered the shape of validation error responses (#46260, 3.5.4); auto-configured MockMvc ignored `@FilterRegistration` (#46605, 3.5.5); Maven plugin gained a way to exclude optional dependencies (#25403, 3.5.7); DevTools restarter with parameterless main (#47987, 3.5.8); random value property source unsuitable for secrets (#50172, 3.5.14).

**4.0.0 – 4.0.9** (20 Nov to 22 Oct)
- 4.0.0 GA: 2 features, 28 fixes. Shipped Framework 7.0.1, Hibernate 7.1.8.Final, Jackson 3.0.2, Tomcat 11.0.14, Testcontainers 2.0.2, Native Build Tools 0.11.3. Fixes included "error properties should not be under `server.*`" (#48201) and `@JsonTest` with both Jackson 2 and 3 present (#48198).
- 4.0.1 (25 fixes). Noteworthy:
  - Hibernate upgraded to 7.2.0.Final.
  - Starter renamed: `spring-boot-starter-kotlin-serialization` → `spring-boot-starter-kotlinx-serialization-json`.
  - `TestRestTemplate` now requires `spring-boot-restclient`.
  - Fixes: `ProblemDetail` rendered to XML incorrectly (#48222); `spring.jackson.default-property-inclusion` not applied to content inclusion (#48343).
- 4.0.2 (21 fixes): `jetty-ee11-servlets` removed from `spring-boot-jetty`. Fixes: validator message interpolation in native image (#48828); "AOT processing of tests should not be disabled when 'skipTests' is set" (#48662).
- 4.0.3 (10 fixes): slice tests with `spring-boot-starter-test-classic` (#48981); configuration-processor whitespace (#49060).
- 4.0.4 (18 fixes). Attention Required:
  - Jackson 3.1.0 and Jackson 2 2.21.1 (3.0.x and 2.20.x end-of-support).
  - Default `server.tomcat.max-part-count` raised from 10 to 50.
  - OpenTelemetry `ZipkinSpanExporter` deprecated.
  - Also: `spring-boot-starter-restdocs` added; Maven plugin `-parameters` for AOT (#49295).
- 4.0.5 (9 fixes), 4.0.6 (24), 4.0.7 (24), 4.0.8 (23), 4.0.9 (41 fixes, 1 feature): no attention notes.
  - 4.0.6 highlights: classic starters missing modules (#50071, #50069); `@Ssl` with `@ServiceConnection` on `@Bean` methods (#50064); `use-jackson2-defaults` turning on `FAIL_ON_UNKNOWN_PROPERTIES` (#49951).
  - 4.0.9 ends at Framework 7.0.10, Hibernate 7.2.25.Final, Jackson 3.1.7, Tomcat 11.0.27.

**4.1.0 – 4.1.2**
- 4.1.0 (10 Jun): 2 features, 33 fixes, 59 upgrades. Hibernate 7.4.1.Final, Tomcat 11.0.22, Kotlin 2.3.21.
- 4.1.1 (20 Aug): 33 fixes, 21 docs, 57 upgrades.
  - Attention Required: the Gradle plugin no longer auto-configures gRPC when the Protobuf plugin is applied (#50822).
  - Upgrades: Framework 7.0.9, Hibernate 7.4.5.Final, Jackson 3.1.5, JSpecify 1.0.1, Native Build Tools 1.1.4/1.1.8, Tomcat 11.0.24.
  - Fixes include a `JpaBaseConfiguration` dependency loop with `AsyncTaskExecutor` beans (#50801).
- 4.1.2 (22 Oct, marked Latest): 1 feature (`JavaVersion.TWENTY_SEVEN`), 51 fixes, 19 docs, 55 upgrades. No attention notes.
  - Upgrades: Framework 7.0.10, Hibernate 7.4.13.Final, Hibernate Validator 9.1.5.Final, Jackson 3.1.7 / Jackson 2 2.21.7, Tomcat 11.0.27, PostgreSQL JDBC 42.7.14, Native Build Tools 1.1.14, Lombok 1.18.48.
  - Fixes include DevTools breaking manual JDBC/R2DBC config without starters (#51890), logging lost when the test context is paused (#52088), SBOM manifest attributes in Maven WARs (#51651).

---

## 4. Relevant for this project

Additional sources for this section:
- https://docs.spring.io/spring-boot/system-requirements.html (documents 4.1.2)
- https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html (4.1.2; first 100k characters only, truncated before the Data JPA section)
- https://docs.spring.io/spring-boot/reference/testing/testcontainers.html (4.1.2)
- https://docs.spring.io/spring-boot/appendix/dependency-versions/coordinates.html (4.1.2)
- https://github.com/testcontainers/testcontainers-java/releases/tag/2.0.0
- https://docs.hibernate.org/orm/7.0/migration-guide/migration-guide.html

**Platform minimums (4.1.2 docs)**
- Java 17 to 27, so Java 17 is fine.
- Maven 3.6.3 or later.
- Spring Framework 7.0.10+, Tomcat 11.0.x (Servlet 6.1).
- Native images: GraalVM Community 25 or above, Native Build Tools 1.1.14. Boot 4.0 managed NBT 0.11.x.
- In 3.5 the Maven `nativeTest` profile changed to mirror `native`.
- In 4.1 `-DskipTests` no longer skips test AOT processing; only `maven.test.skip` does.
- Framework 7 uses the new GraalVM reachability-metadata format; this matters only if the project registers custom `RuntimeHints`.

**Starters**
- `spring-boot-starter-web` is deprecated in favour of `spring-boot-starter-webmvc`. It is still in the 4.1.2 managed coordinates, so it still resolves.
- `spring-boot-starter-data-jpa`, `spring-boot-starter-validation`, `spring-boot-starter-actuator`, `spring-boot-devtools`, `spring-boot-configuration-processor` and `spring-boot-testcontainers` keep their artifact IDs.
- Tests: `spring-boot-starter-test` alone no longer supplies the technology test slices. Add the matching test starters, each of which includes `spring-boot-starter-test`:
  - `spring-boot-starter-webmvc-test` for `@WebMvcTest`/MockMvc
  - `spring-boot-starter-data-jpa-test`
  - `spring-boot-starter-validation-test`
  - `spring-boot-starter-actuator-test`
  - `spring-boot-starter-jackson-test`
- Transitional alternative: `spring-boot-starter-classic` plus `spring-boot-starter-test-classic`.

**@WebMvcTest / MockMvc**
- New imports (4.1.2 docs): `org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest` and `org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc`, both from module `spring-boot-webmvc-test`.
- `org.springframework.test.web.servlet.MockMvc` is unchanged.
- `@SpringBootTest` no longer auto-provides MockMvc; add `@AutoConfigureMockMvc`.
- The old Boot 3 package is not named in the sources.

**@MockBean**
- Removed in 4.0. Replace with `org.springframework.test.context.bean.override.mockito.MockitoBean` (and `@MockitoSpyBean`).
- Not allowed in `@Configuration`/`@TestConfiguration` classes. Use class-level `@MockitoBean(types = …)` instead.
- If plain `@Mock`/`@Captor` fields relied on Boot's `MockitoTestExecutionListener`, add Mockito's `MockitoExtension`.

**Jackson**
- Boot 4 auto-configures a Jackson 3 `JsonMapper`; packages are `tools.jackson.*` with group `tools.jackson.core` (`jackson-core`/`jackson-databind` 3.1.7 in 4.1.2).
- `JsonTypeInfo`, `JsonSubTypes`, `JsonBackReference`, `JsonManagedReference` and `JsonIgnore` stay in `com.fasterxml.jackson.annotation` (artifact `com.fasterxml.jackson.core:jackson-annotations`, managed at 2.21). No change is needed for those imports.
- The test's `com.fasterxml.jackson.databind.ObjectMapper` is Jackson 2. An `ObjectMapper` bean of that type is no longer auto-configured, and defining one no longer replaces the auto-configured mapper.
  - Preferred: switch to the auto-configured `JsonMapper`, or to `tools.jackson.databind.ObjectMapper`. That exact class name is an inference from the package rename and is NOT CONFIRMED from a source.
  - Stop-gap: add `org.springframework.boot:spring-boot-jackson2` (deprecated, `spring.jackson2.*`).
  - Jackson 2 `jackson-databind` 2.21.7 is still in dependency management.
- Behaviour changes: all classpath Jackson modules are now registered (`spring.jackson.find-and-add-modules=false` to revert). `spring.jackson.use-jackson2-defaults=true` approximates Boot 3 defaults.
- If `application.properties` uses `spring.jackson.parser.*`, `.generator.*`, `.read.*` or `.write.*`, see the renames in section 1.2. 4.1 reintroduces `spring.jackson.read.*`/`write.*` as cross-format settings.
- `@JsonComponent`/`@JsonMixin` become `@JacksonComponent`/`@JacksonMixin` if used.
- Custom `HttpMessageConverter` beans are no longer picked up; use `ServerHttpMessageConvertersCustomizer`.

**org.springframework.lang.Nullable**
- Deprecated in Framework 7 (JSR 305-style annotations are superseded by JSpecify).
- The Boot guide names `org.jspecify.annotations.Nullable` as the replacement and states it is required for optional Actuator endpoint parameters.
- `org.jspecify:jspecify` 1.0.1 is managed.
- The Framework null-safety migration doc itself was not read.

**Multipart upload**
- No `spring.servlet.multipart.*` key appears as removed or deprecated in the 4.0 config changelog.
- Tomcat enforces part limits: `server.tomcat.max-part-count` (default 10 in 4.0.0–4.0.3, 50 from 4.0.4, so 50 in 4.1.2) and `server.tomcat.max-part-header-size`.

**Problem details**
- `spring.mvc.problemdetails.enabled` is not mentioned in any source read, and no `spring.mvc.*` key is removed in 4.0.
- 4.0.1 fixed `ProblemDetail` XML rendering.
- If `server.error.*` keys are used, they are now `spring.web.error.*`.

**CORS via `WebMvcConfigurer`**
- No API change found.
- Framework 7: preflight requests are no longer rejected when the CORS configuration is empty.
- `HttpHeaders` no longer implements `MultiValueMap`; this matters only if code treats headers as a map.

**JPA / Hibernate**
- Hibernate goes 6.6.x → 7.2 (Boot 4.0.1+) → 7.4 (7.4.13.Final in 4.1.2); Jakarta Persistence 3.2.
- `@EntityScan`, if used, is now `org.springframework.boot.persistence.autoconfigure.EntityScan`.
- `hibernate-jpamodelgen` → `hibernate-processor`.
- 4.1: `spring.data.jpa.repositories.bootstrap-mode=deferred` requires an `AsyncTaskExecutor` bean; new `spring.jpa.bootstrap`.
- No change to `spring.jpa.hibernate.ddl-auto` is listed in any Boot source.
- Hibernate 7.0 changes worth checking:
  - Default timestamp precision DDL changed.
  - Queries with an implicit select and no result type are stricter.
  - Refresh/lock of detached entities fails.
  - Cascade PERSIST is no longer implied for `@Id`/`@MapsId` associations.
  - `EntityExistsException` when cascading to a detached instance.
  - Removed annotations: `@Where` → `@SQLRestriction`; Hibernate `@OrderBy` → `@SQLOrder`; `@Proxy`, `@LazyToOne`, `@LazyCollection`, Hibernate `@Table`/`@Index`/`@ForeignKey`.
  - `CascadeType.SAVE_UPDATE`/`DELETE` removed.
  - `GenerationType#SEQUENCE` only with `@SequenceGenerator`.
  - Native queries return `java.time` types.
  - Classpath-scan entity discovery needs `hibernate-scan-jandex` (managed by Boot 4.1.2).

**Hibernate bytecode-enhancement Maven plugin**
- The Hibernate 7.0 guide says the plugin's groupId and artifactId changed. New coordinates: `org.hibernate.orm:hibernate-maven-plugin`, goal `enhance`, parameters `enableLazyInitialization` and `enableDirtyTracking`.
- Both parameters default to `false` in the 7.0 Maven plugin; the guide calls this a regression "fixed in Hibernate ORM 7.1". Set them explicitly anyway.
- NOT CONFIRMED: the old coordinates (the guide does not state them).
- NOT CONFIRMED: whether the Boot parent manages the plugin version. It is not in the dependency coordinates table, and the parent POM's pluginManagement was not read.
- NOT CONFIRMED: `enableAssociationManagement`/`enableExtendedEnhancement` in 7.4.

**Testcontainers with `@ServiceConnection`**
- `spring-boot-testcontainers` and `org.springframework.boot.testcontainers.service.connection.ServiceConnection` are unchanged.
- Testcontainers moves 1.21 → 2.0 (2.0.5 managed in 4.1.2), with breaking changes:
  - Every module artifact gets the `testcontainers-` prefix: use `org.testcontainers:testcontainers-postgresql` and `org.testcontainers:testcontainers-junit-jupiter`.
  - Container classes move to `org.testcontainers.<module>`: `org.testcontainers.postgresql.PostgreSQLContainer`. The old package is not named in the sources.
  - `org.testcontainers.junit.jupiter.Testcontainers`/`Container` are unchanged.
  - JUnit 4 support is removed.
- A `PostgreSQLContainer` creates both `JdbcConnectionDetails` and `R2dbcConnectionDetails`.

**Actuator**
- `management.endpoint.<id>.enabled` is removed in favour of `management.endpoint.<id>.access`.
- `heapdump` has `access=NONE` by default (3.5).
- Liveness and readiness health groups are on by default (4.0); `management.health.probes.enabled` → `management.endpoint.health.probes.enabled`.
- `.enabled` properties must be literally `true` or `false` (3.5).
- Health support lives in the separate `spring-boot-health` module. Several 4.0.x fixes concern Actuator without it (#49196, #50188).

**DevTools**
- Live reload is off by default in 4.0 and deprecated without replacement in 4.1 (`spring.devtools.livereload.enabled`, `spring.devtools.livereload.port`).
- 4.1.2 fixes a DevTools failure with manually configured JDBC (#51890).

**Configuration processor**
- No required change found.
- New options: the `ignored` section in additional metadata (3.5) and `@ConfigurationPropertiesSource` (4.0).

**Test stack versions (4.1.2)**
- JUnit Jupiter 6.0.3 and Mockito 5.23.0 are managed.
- Framework 7's `SpringExtension` now uses a test-method-scoped `ExtensionContext`; opt out with `spring.test.extension.context.scope=test_class`.
- `MockHttpServletRequest`/`MockHttpServletResponse` are stricter about null headers.

**Other 3.5 behaviour changes that could bite**
- Profile-name validation.
- The `taskExecutor` bean name is gone (only `applicationTaskExecutor`).
- `@ConditionalOnMissingBean` is now generic-aware.
- `TestRestTemplate` follows redirects.

**Suggested path per the guide**
- Go to the latest 3.5.x first (3.5.16) and clear deprecations.
- Then 4.x, optionally with `spring-boot-properties-migrator` (runtime scope) and the classic starters as an intermediate step.
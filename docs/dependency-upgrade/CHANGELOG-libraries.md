> Stand: 2026-11-15. Recherche aus den offiziellen Quellen (URLs je Abschnitt). Formulierungen sind teils Paraphrasen der Release Notes, keine wörtlichen Zitate.
> Was davon für dieses Projekt gilt und was im Probelauf verifiziert wurde, steht in [PLAN.md](PLAN.md); wo dieser Text „NOT CONFIRMED" sagt, zählt der Plan.

# Dependency changelog research (read 2026-11-15)

All ten libraries were read from official sources. Three limits apply throughout:

- **GitHub release pages** (Testcontainers, maven-compiler-plugin, Native Build Tools, MapStruct Spring Extensions) were only reachable through WebFetch, which paraphrases. Item wording there is not verbatim, and those pages show day and month without the year.
- **Years and missing dates** were taken from Maven Central directory listings or POM timestamps, and are marked "(Central)".
- **Everything else** (springdoc, MapStruct, Lombok, pgjdbc, Hibernate, JUnit, NBT changelog) was read as raw text from the changelog files or doc pages.


---

## 1. springdoc-openapi 2.7.0 → 3.2.0

Sources:
- https://raw.githubusercontent.com/springdoc/springdoc-openapi/main/CHANGELOG.md (3.x and 2.8.0–2.8.14)
- https://raw.githubusercontent.com/springdoc/springdoc-openapi/spring-boot-3/CHANGELOG.md (2.8.11, 2.8.15–2.10.0)
- https://springdoc.org/faq.html and https://springdoc.org/v2/faq.html (compatibility)

The GitHub releases page was not read. Both changelogs also carry undated, unreleased sections "3.2.1" and "2.10.1", which are not on Maven Central.

### Compatibility matrix (FAQ)

| Spring Boot | springdoc-openapi |
|---|---|
| 3.4.x | 2.7.x – 2.8.x |
| 3.5.x | 2.8.x – 2.10.x |
| 4.0.x | 3.0.x |
| 4.1.x | 3.1.x – 3.2.x |

The README states 3.x is for Boot 4.x (`main` branch), 2.x for Boot 3.x (`spring-boot-3` branch), Java 17+. Per the changelog, 2.8.0–2.8.8 were built on Boot 3.4.1–3.4.5 and 2.8.9 moved to Boot 3.5.0.

### 2.x line

**2.8.0 (2025-01-03)**
- Added: #2790 OpenAPI 3.1 becomes the default implementation.
- Added: #2817 obey annotations when flattening `@ParameterObject` fields.
- Added: #2826 `@RequestParam` can be sent in form instead of query.
- Added: #2822 `ParameterCustomizer` may return null.
- Added: #2830 support for deprecated fields.
- Added: #2780 security schema by auto-configuration.
- Changed: spring-boot 3.4.1, spring-cloud-function 4.2.0, swagger-core 2.2.27.
- Fixed: #2804 2.7.0 depended on Spring Cloud milestone 4.2.0-M1.
- Fixed: #2828 missing `Associations` bean.
- Fixed: #2823 capturing pattern in identical paths.
- Fixed: #2817 `required` added automatically for `@NotNull`/`@NotBlank`.
- Fixed: #2814 circular reference with `management.endpoint.gateway.enabled=true`.
- Fixed: #2798 Kotlin `Unit` schema.
- Fixed: #2797 removing operationId via customizer.
- Fixed: #2833 infinite recursion with OAS 3.1.
- Fixed: #2827 `@Parameter(required = false)` was ignored.

**2.8.1 (2025-01-06)**
- Fixed: #2834 `ClassNotFoundException: kotlin.reflect.full.KClasses` after upgrading from 2.7.0.

**2.8.2 (2025-01-12)**
- Added: #2849 better compatibility for projects migrating from OAS 3.0 to 3.1.
- Fixed: #2846 `ClassCastException` with spring-data-rest and OAS 3.1.
- Fixed: #2844 `PageableObject`/`SortObject` named `Pageablenull`/`Sortnull`.

**2.8.3 (2025-01-12)**
- Added: #2851 refined condition for ignoring types with `PolymorphicModelConverter`.

**2.8.4 (2025-01-25)**
- Added: #2873 performance of `getGenericMapResponse`.
- Added: #2836 option to set allowed locales.
- Added: #2862 Swagger-UI prefix path aligned with WebMvc behaviour.
- Changed: spring-boot 3.4.2, spring-cloud-function 4.2.1, swagger-core 2.2.28.
- Fixed: #2870 native image broken with 2.8.x and Boot 3.4.1.
- Fixed: #2869 exception logged for Spring Data repository delete.
- Fixed: #2856 `@JsonUnwrapped` ignored.
- Fixed: #2852 `@Schema(types=…)` on multipart params with `springdoc.default-support-form-data`.

**2.8.5 (2025-02-16)**
- Added: #2696 sealed classes no longer need `@JsonSubTypes`.
- Added: #2898 native-image reflection hints.
- Added: #2891 `trimIndent` refactor.
- Added: #2931 `OpenAPIService` serverBaseUrl thread safety.
- Added: #2933 `PagedModel` VIA_DTO in `ResponseEntity`.
- Changed: swagger-ui 5.18.3.
- Fixed: #2902 schema replaced by String with `RepresentationModel`.
- Fixed: #2876 endpoints with the same name mixed up.
- Fixed: #2895 actuator filter.
- Fixed: #2894 `@JsonUnwrapped`/`@Schema` on properties.
- Fixed: #2881 `@PageableDefault` with one-indexed-parameters.
- Fixed: #2888 parameter and response ordering.

**2.8.6 (2025-03-23)**
- Added: #2909 check both SerDe `BeanPropertyDefinition` for `@JsonUnwrapped`/`@Schema`.
- Added: #2927 bail sealed subclass introspection on Schema.
- Added: #2917 `Future` added to ignored response wrappers.
- Added: #2938 built-in support for `LocalTime`, `YearMonth`, `MonthDay`.
- Changed: swagger-ui 5.20.1, swagger-core 2.2.29, spring-cloud-function 4.2.2, spring-boot 3.4.4.
- Fixed: #2928 `SchemaBuilder` methods.
- Fixed: #2905 `enumAsRef` with actuator.
- Fixed: #2939 duplicate `ModelConverter` with DevTools.
- Fixed: #2941 native `/v3/api-docs` with Map field.

**2.8.7 (2025-05-04)**
- Added: #2944 `springdoc-openapi-bom` project.
- Added: #2948 servers configurable via application.yml.
- Added: #2963 default content type `application/problem+json` for problem details.
- Added: #2971 Kotlin value class lists.
- Changed: swagger-ui 5.21.0, swagger-core 2.2.30, spring-boot 3.4.5, spring-security-oauth2-authorization-server 1.4.3.
- Fixed: #2947 trailing-slash warning.
- Fixed: #2960 NPE customizing a group without schema.
- Fixed: #2969 resource handler path with `/webjar` prefix.
- Fixed: #2964 `Duration` description/example.
- Fixed: #2972 `@Header(schema=…)` broken since 2.8.0.
- Fixed: #2976/#2967 build failure with private inner class.

**2.8.8 (2025-05-04)**
- Fixed: #2977 projects not using kotlin-reflect.

**2.8.9 (2025-06-10)**
- Added: #2944 `@Positive` support.
- Added: #3011 type-use annotations for method parameters.
- Changed: spring-boot 3.5.0.
- Fixed: #2982 `application/problem+json` not set for `ProblemDetail`.
- Fixed: #2990 POST x-www-form-urlencoded with one parameter.
- Fixed: #2998 method-level `@Webhook`.
- Fixed: #3012 order of examples.

**2.8.10 (2025-08-20)**
- Added: #3046 `@jakarta.annotation.Nonnull`.
- Added: #3042 externalDocs in `SpecPropertiesCustomizer`.
- Added: #3057 webhook discovery refactor.
- Changed: spring-boot 3.5.4, swagger-ui 5.27.1, swagger-core 2.2.36.
- Fixed: #3050 `@RequestPart` JSON missing Content-Type.
- Fixed: #2978 parameter no longer optional since 2.8.8.
- Fixed: #3022 NPE in `SchemaUtils`.
- Fixed: #3026 media type merging.
- Fixed: #3036 typo.
- Fixed: #3039 property resolution for extensions in `@OpenAPIDefinition` Info.
- Fixed: #3051 `RequestPart` with Map.
- Fixed: #3060 use `adaptFromForwardedHeaders`.

**2.8.11 (2025-08-23)**
- Added: #3065 javadoc and performance optimization.
- Changed: spring-boot 3.5.5.
- Fixed: #3064 `ClassNotFoundException: kotlin.reflect.full.KClasses`.

**2.8.12 (2025-09-01)**
- Changed: swagger-ui 5.28.0.
- Fixed: #3073 duplicate key `Parameter` for two GETs with the same path.
- Fixed: #3071 swagger `@RequestBody` with `@RequestPart`.
- Fixed: #3066 parameter became required after 2.8.10.

**2.8.13 (2025-09-07)**
- Added: #3084 Scalar support.
- Changed: swagger-ui 5.28.1.
- Fixed: #3076 extra `type: string` with oneOf.

**2.8.14 (2025-11-02)**
- Added: #3090 log when SpringDocs/Scalar is enabled by default.
- Changed: swagger-ui 5.30.1, swagger-core 2.2.38, spring-boot 3.5.7, commons-lang3 3.18.0, Scalar 0.3.12.
- Fixed: #3107 compatibility with lower `getOpenApi()`.
- Fixed: #3121 NPE in `KotlinDeprecatedPropertyCustomizer`.

**2.8.15 (2026-01-01)**
- Added: #3122 log notifications.
- Added: #3123 serving static resources.
- Added: #3151 `@Order` on `ApplicationReadyEvent` listener.
- Added: #3158 API groups in Scalar.
- Added: #3187 Scalar WebMVC/WebFlux.
- Added: #3185 GitHub issue settings.
- Added: #3186 decouple Web Server APIs.
- Added: #3131 warning messages.
- Added: #3183 remove unused consumer.
- Added: #3141 `useReturnTypeSchema` evaluated per HTTP status.
- Changed: spring-boot 3.5.9, swagger-core 2.2.41, swagger-ui 5.31.0, Scalar 0.4.3.
- Fixed: #3133 content type from swagger `@RequestBody` precedence.
- Fixed: #3146 WebJar resource handler mappings.
- Fixed: #3168 `@Schema` on Kotlin value classes.
- Fixed: #3178 Kotlin `LinkedHashSet`.
- Fixed: #3170 warnings for title/description in application.yml.

**2.8.16 (2026-02-27)**
- Added: `springdoc.swagger-ui.document-title`.
- Changed: spring-boot 3.5.11, swagger-core 2.2.43, swagger-ui 5.32.0, Scalar 0.5.55.
- Fixed: #3230 Scalar source URLs `null/<groupName>`.
- Fixed: #3226 `@JsonView` for `Page<T>`.
- Fixed: #3205 UI with native GraalVM 25.
- Fixed: #3219 swagger-core 2.2.43.
- Fixed: #3193 `OpenApi` field naming in `SpringDocConfigProperties`.
- Fixed: #3161 duplicate `_links`.
- Fixed: type annotation without Kotlin; property resolution for default values.

**2.8.17 (2026-04-12)**
- Added: `@Range` support.
- Added: `nullable: true` for Kotlin nullable types.
- Changed: spring-boot 3.5.13, swagger-core 2.2.47, swagger-ui 5.32.2.
- Fixed: #3259 annotated generic types on parameters.
- Fixed: #3255 `$ref` nullable wrapping / OAS 3.1.
- Fixed: #3245 swagger-core 2.2.45.
- Fixed: #3241 nondeterministic `@ControllerAdvice` responses.
- Fixed: #3236 YAML group URLs.
- Fixed: `ExtensionProperty` JsonNode; `LocalDate` defaults.

**2.9.0 (2026-07-31)**
- Added: request-specific Swagger UI index transformation.
- Added: mechanism to disable `nullable` for Kotlin properties.
- Changed: spring-boot 3.5.16, swagger-core 2.2.52, swagger-ui 5.32.11, commons-lang 3.20.0, SonarQube clean-ups (#3306, #3307).
- Fixed: #3304 Kotlin parent-class nullable.
- Fixed: #3298 Boot 3.5.16.
- Fixed: #3294 duplicated `swagger-config` path.
- Fixed: #3293 `SNAKE_CASE` record fields.
- Fixed: #3291 vulnerabilities (Boot 3.5.14, swagger-ui 5.32.5).
- Fixed: #3288 `WebProperties`/`WebMvcProperties` optional.
- Fixed: #3281 `Page` property order.
- Fixed: #3274 description disappearing.
- Fixed: #3270 `ParameterObject` validation applied to same-name `PathVariable`.
- Fixed: #3263 null Map key.
- Fixed: #3262 test module versions.

**2.9.1 (2026-09-06)**
- Security: GHSA-rhhx-6j8h-8cvw unbounded per-locale OpenAPI cache (memory exhaustion via `Accept-Language`).
- Security: GHSA-c925-vm88-mpp9 Scalar starters trust forwarded headers.
- Security: CVE-2026-75838 XSS in DOMPurify bundled with swagger-ui, fixed by swagger-ui 5.32.14.
- Added: #3340 `JsonNullable` described without wrapper.
- Added: #3325 swagger artifacts managed in `springdoc-openapi-bom`.
- Added: #3321 `springdoc.login-endpoint.username-example` / `password-example`.
- Changed: Scalar starters no longer register forwarded-header handling (set `server.forward-headers-strategy=framework` or `native`).
- Changed: new `springdoc.cache.max-entries` (default `100`).
- Changed: #3351 `Duration`/`LocalTime`/`OffsetTime` now carry `format` (`duration`, `time-local`, `time`).
- Changed: indirect `Set` types get `uniqueItems: true`.
- Changed: swagger-core 2.2.55, swagger-ui 5.32.14.
- Fixed: #3320, #3319, #3313, #3331, #3322, #3315, #3314, #3300, #3341, #3338, #3332, #3136, #3317, plus Spring Data REST response post-processing hardening.

**2.10.0 (2026-10-24)**
- Added: #3370 `ParameterCustomizer` can read type-use annotations.
- Added: #3382 `@Nullable` Java properties marked nullable, opt-in via `springdoc.model-converters.java-nullable-property-customizer.enabled=true`.
- Added: #3390 `springdoc.model-converters.polymorphic-converter.one-of-as-ref` and `…one-of-ref-suffix`.
- Changed: #3360 `PagedModel`/`PageMetadata` properties now required.
- Changed: #3361 `MonetaryAmount` `amount`/`currency` required.
- Changed: #3359 Scalar 0.6.83.
- Changed: swagger-ui 5.34.0, spring-cloud-function 4.2.4, commons-lang3 3.21.0.
- Fixed: #3352 class-level `@ResponseStatus`.
- Fixed: #3355 `WebSession` parameter.
- Fixed: #3364 HATEOAS without `LinkRelationProvider`.
- Fixed: #3368 `WebAsyncTask` unwrapped.
- Fixed: #3377 JSpecify `@Nullable` parameter.
- Fixed: #3381 nullable Kotlin oneOf.
- Fixed: #3387 Scalar integration.
- Fixed: #3373 Jackson 2 no longer lifted above Boot's version in Gradle builds.

### 3.x line

**3.0.0-M1 (2025-08-20)**: #3062 Spring Boot 4.0.0-M2 support.
**3.0.0-RC1 (2025-11-02)**: #3095 Spring Boot 4.0.0-RC1 support.

**3.0.0 (2025-11-21)**
- Added: #2975 Spring Framework 7 initial API versioning support.
- Added: #3123 static resources for WebFlux.
- Changed: Spring Boot 4.0.0, Scalar 0.4.3.
- Fixed: #3131 warning messages; #3121 NPE in `KotlinDeprecatedPropertyCustomizer`.

**3.0.1 (2026-01-01)**
- Added: same list as 2.8.15 (#3122, #3123, #3151, #3158, #3187, #3185, #3186, #3131, #3183, #3141).
- Changed: Spring Boot 4.0.1, swagger-core 2.2.41, swagger-ui 5.31.0, Scalar 0.4.3.
- Fixed: #3133, #3146, #3168, #3178, #3170.
- Fixed: #3173 `/v3/api-docs` returned Base64 with Spring Framework 7.0.2.
- Fixed: #3155 native image regression with springdoc 3.0 / Boot 4.0.

**3.0.2 (2026-02-27)**
- Added: #3229 API versioning with functional endpoints.
- Added: #3208 `springdoc.swagger-ui.document-title`.
- Changed: Spring Boot 4.0.3, swagger-core 2.2.43, swagger-ui 5.32.0, Scalar 0.5.55.
- Fixed: #3232 springdoc endpoint paths during API version resolution.
- Fixed: #3230 Scalar source URLs.
- Fixed: #3228 starter 3.x did not depend on `org.springframework.boot:spring-boot-starter`.
- Fixed: #3220 reachability metadata incompatible with GraalVM 25.
- Fixed: #3195 compile failure with spring-boot-data-rest.
- Fixed: #3193, #3215, #3199, #3198, #3206.

**3.0.3 (2026-04-12)**
- Added: #3246 MCP (Model Context Protocol) support.
- Added: #3256 `nullable: true` for Kotlin nullable types.
- Added: #3239 `@Range`.
- Added: #3244 `LocalDate` defaults.
- Changed: Spring Boot 4.0.5, swagger-core 2.2.47, swagger-ui 5.32.2.
- Changed: #3260 `@ConditionalOnClass(HateoasProperties.class)`.
- Changed: MCP header forwarding and base path.
- Fixed: #3258 API Version Required with WebFlux broke the UI.
- Fixed: #3259, #3255, #3247, #3245, #3235, #3226.

**3.1.0 (2026-07-31)**
- Added: #3269 mechanism to disable `nullable` for Kotlin.
- Added: request-specific Swagger UI index transformation.
- Changed: Spring Boot 4.1.0, Spring AI 2.0.0, swagger-core 2.2.52, swagger-ui 5.32.11, #3307/#3306.
- Fixed: #3304, #3294, #3293, #3292, #3284, #3282, #3281, #3274, #3270, #3266, #3263.

**3.1.1 (2026-09-06)**
- Security (MCP): GHSA-6f5m-mhjg-qwxq, GHSA-4v2q-56v7-2cpw, GHSA-m4cg-mhpg-rh2r, GHSA-5f9r-4mc4-qh3c, GHSA-jcgg-59c8-w4wh.
- Security: GHSA-rhhx-6j8h-8cvw (per-locale cache), GHSA-c925-vm88-mpp9 (Scalar forwarded headers), CVE-2026-75838 (DOMPurify XSS, swagger-ui 5.32.14).
- Added: #3340, #3325, #3321 (as in 2.9.1).
- Changed: MCP is now opt-in (`springdoc.ai.mcp.enabled=true`, `springdoc.ai.mcp.dashboard-enabled=true`).
- Changed: Scalar starters no longer register forwarded-header handling.
- Changed: `springdoc.cache.max-entries` (default 100); `springdoc.ai.mcp.audit.redact` (default true).
- Changed: #3351 `Duration`/`LocalTime`/`OffsetTime` formats; indirect `Set` gets `uniqueItems: true`.
- Changed: swagger-core 2.2.55, swagger-ui 5.32.14.
- Fixed: #3328/#3337 NPE with spring-hateoas without `HateoasProperties`.
- Fixed: #3314, #3317, #3332, #3320, #3319, #3313, #3331, #3322, #3315, #3300, #3341, #3338, #3136.

**3.2.0 (2026-10-24)**
- Added: #3370, #3382, #3390 (as in 2.10.0).
- Added: #3388 MCP tools registered with `McpStatelessSyncServer`.
- Changed: MCP dashboard no longer pre-fills OAuth2 values.
- Changed: #3354 default API version listed only on operations that serve it.
- Changed: #3360 `PagedModel`/`PageMetadata` required; #3361 `MonetaryAmount` required.
- Changed: #3359 Scalar 0.6.83; #3383 Spring AI 2.0.1.
- Changed: Spring Boot 4.1.2, swagger-ui 5.34.0, spring-cloud-function 5.0.5.
- Changed: spring-security-oauth2-authorization-server now the Boot-managed 7.1.2.
- Fixed: #3352, #3355, #3364, #3368, #3377, #3381, #3387, #3373.
- Fixed: #3354 `/v3/api-docs` failure with `@Schema(allowableValues)` on an API version parameter.

### Upgrade impact
- **3.x requires Spring Boot 4.** On Boot 3.4/3.5 the target is `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.10.0` (Boot 3.5.x; for 3.4.x the FAQ lists 2.7.x–2.8.x). On Boot 4.1 the target is `3.2.0`; on Boot 4.0 it is `3.0.x`. The artifact id is unchanged.
- **Generated spec changes.** OpenAPI 3.1 is the default since 2.8.0 (#2790). The changelog does not name the property to switch back. Since 2.9.1/3.1.1 `Duration`/`LocalTime`/`OffsetTime` carry a `format`; since 2.10.0/3.2.0 `PagedModel` properties are required.
- **Security.** 2.9.1/3.1.1 fix the cache and DOMPurify issues; on 3.x MCP is opt-in from 3.1.1.
- **Future 3.2.1** (unreleased) announces swagger-core 3.0.0 with Jackson 3 and renamed swagger artifacts (`swagger-annotations-jakarta` → `swagger-annotations`, etc.). This is not part of 3.2.0.

---

## 2. MapStruct 1.6.3 → (still 1.6.3 stable)

Sources:
- https://github.com/mapstruct/mapstruct/releases (WebFetch)
- `NEXT_RELEASE_CHANGELOG.md` at tags `1.7.0.Beta1` and `1.7.0.Beta2` (raw, verbatim)
- Maven Central metadata

Maven Central shows `1.6.3` (2024-11-09) as the last stable; `latest`/`release` point to `1.7.0.Beta2`. The GitHub page marks both betas as pre-release and 1.6.3 as "Latest". No CR or Final for 1.7.0 is announced anywhere I read.

**1.7.0.Beta1 (2026-02-01, Central)**
- Feature: Java 21 Sequenced Collections (#3240).
- Feature: native `java.util.Optional` mapping (#674), in all source/target combinations; no null check for `Optional` properties.
- Feature: improved Kotlin support, requires `org.jetbrains.kotlin:kotlin-metadata-jvm` (data classes #2281/#2577/#3031, sealed classes #3404).
- Feature: new `@Ignored` annotation to ignore several target properties (#3838).
- Enhancement: locale for `numberFormat`/`dateFormat` (#3628).
- Enhancement: builder detected without factory method, for an inner class ending in `Builder` with a parameterised constructor (#3729).
- Enhancement: custom exception for subclass exhaustive strategy on `@BeanMapping`/`@Mapper`/`@MappingConfig` (#3821).
- Enhancement: `NullValuePropertyMappingStrategy#CLEAR` (#1830).
- Enhancement: deterministic order of supporting fields/methods (#3940).
- Enhancement: `@AnnotatedWith` on decorators (#3659).
- Behaviour change: warning/error for redundant `ignoreUnmappedSourceProperties` entries (#3906), level follows `unmappedSourcePolicy`.
- Behaviour change: warning when the target has no target properties (#1140).
- Behaviour change: `Optional` targets initialised with `Optional.empty()` instead of `null` (#3852).
- Behaviour change: `String` → `Number` is a lossy conversion (#3848), governed by `typeConversionPolicy`.
- Bug: #3786 error message for non-iterable to array.
- Bug: #3809 `@TargetPropertyName` condition in nested update mappings.
- Bug: #3849 duplicate invocation of overloaded lifecycle methods (opt-out via `mapstruct.disableLifecycleOverloadDeduplicateSelector`).
- Bug: #3711 generic `@Context`.
- Bug: #3806 `NullValuePropertyMappingStrategy.IGNORE` for setter-less collections/maps.
- Bug: #3807 public generic fields.
- Bug: #3886 record method treated as fluent setter.
- Bug: #3884 `SET_TO_DEFAULT` with null target.
- Bug: #3905 object named `Override`.
- Build: release via Maven Central Portal; Windows/macOS builds moved; Kotlin test support.

**1.7.0.Beta2 (2026-06-27, Central)**
- Feature: JSpecify nullness annotations (#1243). `@NonNull`/`@Nullable`/`@NullMarked`/`@NullUnmarked` control null checks.
  - A `@NonNull` return type implies `RETURN_DEFAULT`.
  - Mapping a nullable source to a `@NonNull` constructor parameter without `defaultValue` is a compile error.
  - Disable with `mapstruct.disableJSpecify`.
- Feature: `URI` ↔ `String` built-in conversion (#4018).
- Feature: `ClassAccessibility` enum for generated mapper accessibility (#2513).
- Enhancement: `SET_TO_NULL` for overloaded target methods (#3949).
- Enhancement: switch expressions when targeting JDK 14+ (#4062).
- Enhancement: `URI.create(String).toURL()` instead of `new URL(String)` (#4041).
- Enhancement: compile error for `SET_TO_DEFAULT` without accessible no-args constructor (#4060).
- Enhancement: multi-catch (#4021); diamond operator (#4045).
- Enhancement: factory methods for `LinkedHashMap`/`LinkedHashSet` with `SequencedSet`/`SequencedMap` (#3990).
- Enhancement: processor performance (#3991); generics in arrays (#4050).
- Bug: #3994 generic super/extends bound.
- Bug: Javadoc location in distribution zip.
- Bug: #4046 `Optional` target not using static builder factory.
- Bug: #3948 exact generic type matching (wrong mappings / infinite loops).
- Bug: #4027 `ZonedDateTime`/`OffsetDateTime` → `LocalDateTime`/`Instant`.
- Docs: `SECURITY.md`.
- Build: test on JDK 25/26, drop JDK 11 integration test (#4039), Freemarker 2.3.34, Checkstyle rules, plus internal refactorings.

### Upgrade impact
Nothing to change: `org.mapstruct:mapstruct` and `mapstruct-processor` stay at `1.6.3`. If 1.7.0 is adopted later, expect new warnings (lossy `String`→`Number`, redundant `ignoreUnmappedSourceProperties`, empty targets) and changed generated code.

---

## 3. MapStruct Spring Extensions 1.1.2 → 2.0.0

Sources:
- https://github.com/mapstruct/mapstruct-spring-extensions/releases (WebFetch)
- PR https://github.com/mapstruct/mapstruct-spring-extensions/pull/191 (WebFetch)
- `git diff` between tags `v1.1.2`, `v1.1.3`, `v2.0.0` of the repository (sources and `docs/src/docs/asciidoc`)

The release notes contain no "breaking changes" text. The points below marked "(diff)" come from the tag diff, not from release notes.

**1.1.3 (2025-03-14, tag date)**
- #113 Generated `TYPE_DESCRIPTOR_…` field names now replace every non-word character with `_`.
- #121 (issue #119) New `@SpringMapperConfig` attribute `converterRegistrationConfigurationClassName` (default `"ConverterRegistrationConfiguration"`). The generated `ConverterScan` then `@Import`s that class.
- #123 Renovate configured; dependency bumps (Spring Boot test dependency 2.7.18).
- (diff) `@SpringMapperConfig.conversionServiceBeanName` default changed from `""` to `"conversionService"`.
- (diff) New constants `DEFAULT_CONVERSION_SERVICE_BEAN_NAME`, `DEFAULT_ADAPTER_CLASS_NAME`, `DEFAULT_CONFIGURATION_CLASS_NAME`.
- (diff) `@Qualifier` on the adapter's `ConversionService` constructor parameter is only emitted when the name is non-empty and not `"conversionService"`.
- (diff) `ConverterScan`/`ConverterScans`/registration configuration are now generated whenever `generateConverterScan = true`. In 1.1.2 this additionally required a non-empty `conversionServiceBeanName`.

**2.0.0 (2025-11-15)**
- #191 "Set new minimum baseline": Java 17 and Spring 6; PR text says "Next release will be 2.0.0".
- (diff) Gradle toolchain 11 → 17; compile baseline `spring = "6.0.0"` (was `5.0.0.RELEASE`).
- (diff) Docs now say "The minimum JDK version is 17"; required MapStruct is still "at least version `1.4.0.Final`".
- (diff) Generated `ConverterRegistrationConfiguration` always uses `jakarta.annotation.PostConstruct`; the `javax.annotation.PostConstruct` fallback was removed.
- (diff) `jakarta-annotation-api` 1.3.5 → 2.1.1; `jsr250` dependency dropped.
- (diff) Publishing moved to `ossrh-staging-api.central.sonatype.com`.
- About 45 Renovate dependency/CI bumps (JUnit to 6.0.1, Guava 33.5.0-jre, Mockito 5.20.0, commons-lang3 3.19.0, Gradle 8.14.3, test Spring Boot 3.5.7 and others).
- (diff) `ExternalConversion.java`, `AdapterMethodName.java`, `DelegatingConverter.java` are byte-identical between `v1.1.2` and `v2.0.0`.
- (diff) The generated `ConversionServiceAdapter` keeps its shape: `@Component`, static `TypeDescriptor` fields, `@Lazy ConversionService` constructor parameter.

### Upgrade impact
- Bump `org.mapstruct.extensions.spring:mapstruct-spring-annotations` and `mapstruct-spring-extensions` to `2.0.0`. It needs JDK 17 and Spring 6+, with `jakarta.annotation.PostConstruct` on the classpath; a Boot 3/4 project meets this.
- No source change is needed for `@SpringMapperConfig`, `@ExternalConversion` or the `ConversionServiceAdapter` usage.
- Two things to check after the bump:
  - If `generateConverterScan = true` was set without a `conversionServiceBeanName`, `ConverterScan` and `ConverterRegistrationConfiguration` are now generated, and the latter injects `@Qualifier("conversionService") ConfigurableConversionService`.
  - Any code referencing generated `TYPE_DESCRIPTOR_*` names with arrays or special characters.

---

## 4. Lombok 1.18.36 → 1.18.48

Source: https://projectlombok.org/changelog (raw text). Binding: Maven Central `maven-metadata.xml`.

**1.18.38 (2025-03-31)**
- Platform: JDK 24 support.
- Feature: nullity annotation supports JSpecify (config key `jspecify`).
- Bugfix: Eclipse "negative length" error (#3823).
- Bugfix: VSCode "extract local variable" on lombok-generated methods (#3783).

**1.18.40 (2025-09-04)**
- Platform: JDK 25 support (#3859).
- Bugfix: Eclipse/VSCode `IllegalArgumentException: Document does not match the AST` (#3886).
- Performance: `@ExtensionMethod` significantly faster (#3866).
- Bugfix: command-line config tool output for nullity annotations (#3931).
- Feature: `@Jacksonized @Accessors(fluent=true)` creates the Jackson annotations for fluent accessors (#3265, #3270).
- Improbable breaking change: Lombok no longer copies Jackson annotations such as `@JsonProperty` from fields to accessors (it did from 1.18.16 to 1.18.38). Restore with `lombok.copyJacksonAnnotationsToAccessors = true`.

**1.18.42 (2025-09-18)**
- Feature: all `@Log` annotations allow changing the access level, default still private (#2280).
- Bugfix: Javadoc parsing in NetBeans and ErrorProne on JDK 25 (#3940).

**1.18.44 (2026-03-11)**
- Feature: `@Jacksonized` supports Jackson 2 and Jackson 3; a warning is shown until you configure which one (#3950).
- Bugfix: on JDK 25, `val` and `@ExtensionMethod` could report erroneous errors with javac (#3947).
- Bugfix: `@Jacksonized` no longer serialises `transient` fields (#3936).

**1.18.46 (2026-04-22)**
- Platform: JDK 26 support (#4019); Spring Tools Suite 5 (#3985).
- Bugfix: `@Jacksonized` kept generating `@JsonProperty` after an explicit `@JsonIgnore` (#4022).
- Bugfix: Eclipse `@Jacksonized` + `fluent = true` "not a repeatable annotation" (#3934).
- Bugfix: finishing touches for Jackson 3 (#4004).

**1.18.48 (2026-09-01)**
- Platform: JDK 27 support (#4072).
- Bugfix: `@SneakyThrows` on JDK 26 no longer produces class files needing lombok.jar at runtime (#4040).
- Feature: config key `lombok.checkReturnValueAnnotation` (`none`|`lombok`, default `none`) generates `@lombok.CheckReturnValue` (#4013).
- Promotion: `@SuperBuilder` moved to the main package (`lombok.SuperBuilder`). The experimental one remains for now and will be deprecated later; the lombok.config keys for it were renamed (#2209).
- Old cruft: `lombok.experimental.Wither` and `lombok.Delegate` removed from the jar (replacements: `lombok.With`, `lombok.experimental.Delegate`).
- Feature: Checker Framework `@SideEffectFree` on generated constructors.
- Bugfix: `@SideEffectFree` on `build()` for method builders.

**`org.projectlombok:lombok-mapstruct-binding`**: confirmed. Maven Central lists only `0.1.0` and `0.2.0`; latest and release are `0.2.0`, last updated 2020-12-04.

### Upgrade impact
- Set `org.projectlombok:lombok` to `1.18.48`; keep `lombok-mapstruct-binding` at `0.2.0`.
- Check for use of the removed `lombok.experimental.Wither` or `lombok.Delegate`.
- If DTOs rely on `@JsonProperty` on fields being copied to getters/setters, add `lombok.copyJacksonAnnotationsToAccessors = true` to `lombok.config`.
- If `@Jacksonized` is used, configure the Jackson version to silence the 1.18.44 warning. The changelog does not name the config key.
- `lombok.experimental.SuperBuilder` still works.

---

## 5. PostgreSQL JDBC 42.7.3 → 42.7.14

Sources:
- https://raw.githubusercontent.com/pgjdbc/pgjdbc/master/CHANGELOG.md (raw)
- https://jdbc.postgresql.org/security/ (CVE ids)
- https://jdbc.postgresql.org/changelogs/ (index only; lists 42.7.3 … 42.7.14)

The changelog also has an `[Unreleased]` section with protocol-hardening fixes that is not part of 42.7.14.

### Security fixes in range

| Fixed in | Id | Issue | Affected |
|---|---|---|---|
| 42.7.6 | CVE-2026-107313 | On GSS-encrypted connections the driver could store bytes of earlier messages in place of a large value (fixed by PR #3500) | 42.7.4, 42.7.5 |
| 42.7.7 | CVE-2025-49146 (GHSA-hq9p-pm7w-8p54) | `channelBinding=require` allowed fallback to non-SASL authentication | not stated in the changelog section read |
| 42.7.11 | CVE-2026-42198 (GHSA-98qh-xjc8-98pq) | Client-side DoS via huge SCRAM PBKDF2 iteration count; new property `scramMaxIterations` (default 100,000) | not stated in the changelog section read |
| 42.7.12 | CVE-2026-54291 (GHSA-j92g-9f8w-j867) | Silent downgrade from `SCRAM-SHA-256-PLUS` under `channelBinding=require`; related scram-client CVE-2026-53712 | 42.7.4 – 42.7.11 |
| 42.7.14 | CVE-2026-107314 (GHSA-rhp9-mr79-r74h) | `requireAuth` not enforced when it excludes every method or contains no method | 42.7.11 – 42.7.13 |
| 42.7.14 | CVE-2026-107315 (GHSA-f64h-wr5q-3qf3) | Values shorter than their declared length were padded with bytes of earlier messages instead of zeros | 42.7.4 – 42.7.13 |

### Per version

**42.7.4 (2024-08-22)**
- Added: SCRAM dependency 3.1 with channel binding (#3188).
- Added: direct SSL ALPN connections (#3252).
- Added: Korean translation (#3276).
- Added: CI on PostgreSQL 15/16/17beta.
- Fixed: `PgInterval` case-insensitive (#3344).
- Fixed: `Infinity::numeric` in `getObject` (#3304).
- Fixed: order of results for `getDouble` (#3301).
- Fixed: bytea in `preferQueryMode=simple` (#3243).
- Fixed: update count -1 for stored procedure calls (#3235).
- Fixed: `TIME '24:00'` to `LocalTime` in binary mode (#3225).
- Fixed: `setBlob`/`setClob` for LOBs with length -1 (#3136).
- Fixed: result-set parameter validation (#3171, #3167).
- Fixed: `preDescribe` removed from `internalExecuteBatch` (#2883).
- Perf: fewer copies for int4/int2 (#3295); unsynchronised `PgBufferedOutputStream` with configurable buffer sizes (#3248); faster `getDate` (#3141).
- Deps: `com.ongres.scram:scram-client` 3.2.
- Deprecated: PostgreSQL versions older than 9.1 (#3335).
- Docs and tests.

**42.7.5 (2025-01-14)**
- Regression: revert of change fc60537 (#3476).
- Fixed: catalog handling in `PgDatabaseMetaData` (#3390).
- Fixed: default GSS credentials (#3451).
- Fixed: `XAResource.recover` returns only transactions of `current_user` (#3450).
- Fixed: `extra_float_digits` not forced for PostgreSQL ≥ 12 (#3446).
- Fixed: "include columns" excluded from primary keys (#3434).
- Fixed: major server version check / RULE (#3402); RULE privilege removed in v18 (#3378).
- Fixed: `GSSInputStream` buffer reuse (#3255); buffered input stream for GSS (#3373).
- Fixed: `getSchemas()` (#3386).
- Fixed: `thisRow`/`rowBuffer` cleared on `ResultSet.close()` (#3384).
- Feature: `getObject(int, byte[].class)` for bytea (#3274).
- Perf: metadata query by oid (#3427).
- Build: Gradle 8.10.2; CI with Java 23; docs.

**42.7.6 (no date in changelog; POM on Central dated 2025-05-28)**
- `DatabaseMetaData.getIndexInfo()` returns index comment as REMARKS (#3513).
- Single simple query for all startup parameters (#3613).
- Fixed: `extra_float_digits=3` for PostgreSQL 12+ (#3491).
- Fixed: protocol 3.2 and wider cancel keys (#3592).
- Fixed: `PgConnection#abort` on Java 24 (#3582).
- Fixed: `ArrayIndexOutOfBounds` writing big objects on GSS connections (#3500).
- Fixed: application name setting restored (#3509).
- Fixed: catalog handling (#3526, #3565, #3588).
- Fixed: Fastpath API and `sslfactoryarg` un-deprecated (#3493, #3496).
- Fixed: `PgMessageType` (#3609); option to turn off automatic LSN flush (#3403).
- Fixed: `isValid` uses `executeWithFlags` (#3631).
- Fixed: `EOFException` on `PreparedStatement#toString` with unset bytea parameter since 42.7.4 (Issue #3365).
- Build: Java 21 for building, JDK 24 in CI, ErrorProne, dependency updates (Gradle 8.14 and others).

**42.7.7 (2025-06-10)**
- Security: CVE-2025-49146, as above.
- Test: `ChannelBindingRequiredTest`.

**42.7.8 (2025-09-18)**
- Added: configurable boolean-to-numeric conversion for `ResultSet` getters (#3796).
- Perf: `getMetaData` without `QUERY_ONESHOT` (#3783); `BufferedInputStream` (#3750); server-prepared statements for `DatabaseMetaData`.
- Fixed: NPE cancelling a query before the cancel key is known.
- Fixed: `PgArray#calcRemainingDataLength` (#3746).
- Fixed: consistent column order in `getImportedExportedKeys`.
- Fixed: `SELF_REFERENCING_COL_NAME` in `getTables` (#3660).
- Fixed: replication connection to servers < 12.
- Fixed: statement closed by the driver's internal `ResultSet#close()`.
- Fixed: empty metadata for empty catalog names.
- Fixed: class comparison in `PGXmlFactoryFactory`.
- Fixed: timezone test (#3774).

**42.7.9 (2026-01-14)**
- Added: query timeout property (#3705); `PEMKeyManager` (#3700).
- Changed: `PGInterval.getValue()` performance; docs.
- Security hardening: user-supplied classes loaded with 3-argument `Class.forName` without initialisation.
- Fixed: `pg_stat_replication.reply_time` (#3906).
- Fixed: temporary LOB descriptors of `setBlob` closed.
- Fixed: `PGXAConnection.prepare` returns `XA_RDONLY` for read-only connections (#3897).
- Fixed: all `Calendar` instances proleptic Gregorian (#3837/#3887); reverted in 42.7.10.
- Fixed: concurrency guards (#3849).
- Fixed: memory leak on Java ≤ 21 via `Thread.inheritedAccessControlContext` (#3886).
- Fixed: numeric arrays with NaN (#3838).
- Fixed: `ssl_is_used()` (#3867); nullable classloader (#3907).

**42.7.10 (2026-02-11)**
- Changed: Shadow 9; checkstyle; CI randomisation for `autosave`/`cleanupSavepoints`.
- Fixed: non-standard strings test for v19 (#3934).
- Fixed: `ConnectionFactoryImpl` issues (#3929).
- Fixed: pending responses processed before fastpath (#3913).
- Docs.
- Reverted: "make all Calendar instances proleptic Gregorian" (#3932).

**42.7.11 (2026-04-28)**
- Security: CVE-2026-42198, as above.
- Added: `requireAuth` connection property (#3895).
- Changed: CI; Gradle 9.
- Fixed: extended protocol messages end with Sync (#3728).
- Fixed: cursor-based fetching when the transaction was started via SQL (#3996).
- Fixed: SSL retry on `IOException` with `sslMode=ALLOW` (#3973).
- Fixed: `connectTimeout` honoured on retry and non-SSL fallback with `sslMode=prefer` (#3968).
- Fixed: `SecurityException` on ForkJoinPool workers (#3962).
- Fixed: `LogSequenceNumber` comparison (#3961).
- Fixed: COPY lock released on `IOException` (#3957).
- Fixed: jsonb returned as `PGObject` instead of `String` (#3956).
- Fixed: SSL key file permission check (#3952).
- Fixed: concurrent close guarded by lock (#3905).

**42.7.12 (2026-06-29)**
- Security: CVE-2026-54291, as above.

**42.7.13 (2026-07-06)**
- Added: prepared-statement cache invalidated on `search_path` change (PostgreSQL 18+) (#4259).
- Added: `reWriteBatchedInserts` merges up to 32768 rows; new `reWriteBatchedInsertsSize` (#4207).
- Added: cache flushed after CREATE/DROP/ALTER, new property `flushCacheOnDdl` (default `true`) (#4067).
- Added: `connectExecutor` property (#4165).
- Added: `classLoaderStrategy` property; the default `driver-first` now falls back to the context classloader (#4167).
- Added: OID constants (#4220).
- Added: `BlobInputStream` skips by seeking / 64-bit large-object API (#4204).
- Changed: connection attempt under `loginTimeout` is interruptible.
- Changed: `PGXAConnection` rejects `setAutoCommit(false)`/`setSavepoint` in an active XA branch; `XAER_RMFAIL` instead of `XAER_RMERR` (#4114).
- Changed: `getPrimaryKeys` derived from `pg_constraint.conkey` (#4202).
- Fixed: release jar on GitHub (#3814).
- Fixed: `Statement#cancel` state machine (#1827).
- Fixed: deferred simple-query flush (#4196).
- Fixed: `reWriteBatchedInserts` with parameterless INSERT ≥ 256 rows (#4207).
- Fixed: comment before `CALL` in `CallableStatement` (#4209).
- Fixed: `PreparedStatement.toString()` for bytea supplied as text via `PGobject` (#4201).
- Fixed: `contextClassLoader` of common-pool threads no longer nulled (#4156).
- Fixed: `getCharacterStream` (#4063).
- Fixed: XA autoCommit handling and `prepare()` state (#4114).
- Fixed: updatable result set with same-named tables in several schemas (#4214).
- Fixed: `LargeObject.close()` flushes buffered output (#4248).
- Fixed: empty timestamp/date text → SQLState `22007` (#4278).
- Fixed: `CHAR_OCTET_LENGTH` null for non-character columns (#4231).
- Fixed: scale in `getBigDecimal(int,int)` (#4211).
- Fixed: `java.time` in updatable `ResultSet` (#3848).
- Fixed: batching with `RETURNING` (#4014, several items).
- Fixed: `search_path` detection case-insensitive (#4216).
- Fixed: SSL key format auto-detection (#3946); PKIX trust anchors for FIPS (#4193).
- Fixed: `gssResponseTimeout` (#4076).
- Fixed: autosave savepoint skipped for `SET LOCAL`/`SET SESSION TRANSACTION` (#4203).
- Smaller rejects/cleanups: #4187, #4229, #4205, #4163, #4194, #4161, #4038, #4045, #4237, #4190, #4069.

**42.7.14 (2026-10-07)**
- Security: CVE-2026-107314 (two `requireAuth` fixes, SQLState `08004` / `22023`) and CVE-2026-107315 (zero padding), as above.

### Upgrade impact
- Set `org.postgresql:postgresql` to `42.7.14`; no coordinate change. The upgrade closes six CVEs.
- Most relevant for bytea/image storage: CVE-2026-107315 only matters when a declared length exceeds the data (`ByteStreamWriter`, `LargeObject.write`, `Blob.setBytes`, COPY).
- Behaviour changes to check:
  - 42.7.11: `jsonb` now comes back as `PGObject`.
  - 42.7.13: `flushCacheOnDdl=true` by default; `classLoaderStrategy=driver-first` fallback; larger `reWriteBatchedInserts` batches.
  - 42.7.14: connections with an all-excluding or empty `requireAuth` now fail.
- No connection-property renames were found.

---

## 6. Testcontainers Java 1.20.4 → 2.0.5

Sources:
- https://github.com/testcontainers/testcontainers-java/releases/tag/<version> for each version (WebFetch, paraphrased)
- Raw source and build files at tag `2.0.5`
- Maven Central (dates, artifact ids)

I found no separate 2.0 migration document; `mkdocs.yml` at tag `2.0.5` has no migration page. The migration notes are the header of the 2.0.0 release notes.

### 1.x line

**1.20.5 (2025-02-19)**
- New: `ServiceBusEmulatorContainer` (#9795), `EventHubsEmulatorContainer` (#9665), `AzuriteContainer` (#9661).
- New modules: `ldap` (#9987), `scylladb` (#8002), `pinecone` (#9911).
- Feature: `RABBITMQ_DEFAULT_USER` via `withAdminUser` (#9571); Ollama port constant / `getPort` (#9623).
- Fix: reuse for `CouchbaseContainer` (#9957).
- Fix: `SolrContainer` ≥ 9.7.0 (#9926).
- Fix: ClickHouse authentication (#9942).
- Fix: cluster creation with `ConfluentKafkaContainer`/`KafkaContainer` (#9910).
- Deps: docker-java 3.4.1 (#9627); shadow plugin coordinates (#9577).
- Docs and housekeeping.

**1.20.6 (2025-03-04)**
- `confluentinc/cp-kcat` 7.4.1 → 7.9.0 (#10000).
- `spock` module source/target compatibility 1.8.

**1.21.0 (2025-04-23)**
- Breaking: spock-core removed from the spock module (#10069).
- Breaking: `ClickHouseProvider` uses `clickhouse/clickhouse-server` (#8738).
- Feature: Tempo in `LgtmContainer` (#10192).
- Feature: Spock tests skippable without Docker (#10180).
- Feature: new chromadb API (#10170); MongoDB Atlas default database (#10034).
- Feature: servicebus skips SQL wait (#10092); `FirestoreEmulatorContainer` flags (#10067).
- Deprecation: `getUserPass` deprecated, `getPassword` added (#10064).
- Fix: connection leak in `JdbcDatabaseDelegate` (#9662).
- Fix: `AlwaysPullPolicy` configurable (#10188).
- Fix: Solr 10 start command (#10174, #10172).
- Docs: `DockerModelRunnerContainer`.
- Deps: Gradle 8.13, docker-java 3.4.2 (#10071).

**1.21.1 (2025-05-29)**
- Feature: Loki in `LgtmContainer` (#10256); model pulling in `DockerModelRunnerContainer` (#10253).
- Fix: generic init script filename for Cassandra (#9606).
- Docs: ClickHouse JDBC V2; Kafka listener.
- Deps: checkstyle 10.23.0.

**1.21.2 (2025-06-18)**
- Ryuk 0.12.0 (#10357).
- Docs: `DockerMcpGatewayContainer` (#10364).
- Gradle 8.14.2.

**1.21.3 (2025-06-28)**
- `testcontainers/sshd` 1.3.0 (#10377).
- Support for `docker/mcp-gateway` image (#10378).

**1.21.4 (2025-12-16, after 2.0.3)**
- The page has no item list. The only sentence says the release "makes version 1.21.x works with recent Docker Engine changes".

### 2.x line

**2.0.0 (2025-10-14)**

Header / migration notes:
- JUnit 4 support removed.
- Transitive dependencies updated.
- All modules are prefixed with `testcontainers-` (example: `org.testcontainers:mysql` → `org.testcontainers:testcontainers-mysql`).
- Container classes moved to `org.testcontainers.<module-name>` (example: `MySQLContainer` in `org.testcontainers.mysql`).

Breaking API changes:
- Docker image support in `DockerComposeContainer`/`ComposeContainer` (#9871).
- JUnit 4 support removed (#10805).
- `Neo4jContainer` made generic, deprecated method dropped (#10388); `Neo4jContainer#withEnterpriseEdition` removed (#11106).
- Default constructors of modules dropped (#10385).
- `LinkableContainer` removed from `NginxContainer` (#10386).

Other changes:
- Features: gRPC endpoint in `BigQueryEmulator` (#10779); `getHttpUrl` in `ClickHouseContainer` (#10526).
- Fixes: Kafka listeners out of sync (#11068); custom database names in host-less JDBC URLs (#10603); `MySQLContainer` with mysql:9.3.0 (#10185).
- New container implementations under the new packages for Solr, OrientDB, Neo4j, Selenium, MongoDB, DB2, Trino, Toxiproxy, RabbitMQ, Pulsar, PostgreSQL (#11088), Nginx, MySQL, MSSQLServer, MockServer, MariaDB, LocalStack, GCloud, CockroachDB.
- Cassandra delegate and wait strategy moved (#11105).
- `MySQLContainer`/`MariaDBContainer` no longer copy default config (#11107, #11108).
- Switched to `com.mysql:mysql-connector-j` (#11078).
- `testcontainers` prefix added to modules (#10591).
- Ryuk 0.13.0 (#10745).
- dynalite module removed (#10384).
- `docker/agents_gateway` support removed (#10473).
- All internal tests moved to JUnit Jupiter.
- Deps: Jackson, SnakeYAML, docker-java (#10258); Gradle 8.14.3; 39 combined dependency PRs.

**2.0.1 (2025-10-16)**
- Fix: R2DBC containers compatible with the new classes: `PostgreSQLR2DBCDatabaseContainer` for `org.testcontainers.postgresql.PostgreSQLContainer` (#11120), likewise MySQL (#11119), MariaDB (#11117), MSSQL (#11118).
- Fix: `testcontainers-jdbc-test` excluded from publication (#11115).
- Docs: artifact coordinates corrected (#11121, #11113).

**2.0.2 (2025-11-13)**
- Default Docker API version set to 1.44 (#11216); Docker image version 25.0.5 (#11219).
- docker-java 3.7.0 (#11218).
- Fix: `ScriptSplitter` performance (#11130).
- `NginxContainer.getBaseUrl()` (#11137).

**2.0.3 (2025-12-15)**
- Fix: compatibility with older Docker Engines by falling back to API v1.32 (#11346).
- questdb image 9.2.2.
- 7 dependency PRs.

**2.0.4 (2026-03-19)**
- `ServiceBusEmulatorContainer` uses the non-deprecated `MSSQLServerContainer` (#11223).
- `apache/activemq` in `ActiveMQContainer` (#11498).
- Ryuk 0.14.0 (#11486).
- docker-java 3.7.1 (#11572).

**2.0.5 (2026-04-20)**
- Feature: `apache/artemis` in `ArtemisContainer` (#11590).
- Feature: `getHttpPort`/`getGrpcPort` in `WeaviateContainer` (#11712).
- Feature: `!override` docker compose tag (#11490).
- Fix: `jarFileTest` cache relocatability (#11574).
- Housekeeping: Weaviate client v6.
- 16 dependency PRs.

### Verified in sources at tag 2.0.5
- **New PostgreSQL class:** `org.testcontainers.postgresql.PostgreSQLContainer` is non-generic (`public class PostgreSQLContainer extends JdbcDatabaseContainer<PostgreSQLContainer>`). Its constructors take `String` or `DockerImageName`; there is no no-arg constructor.
- **Old PostgreSQL class:** `org.testcontainers.containers.PostgreSQLContainer<SELF extends PostgreSQLContainer<SELF>>` still exists but is `@Deprecated` ("use org.testcontainers.postgresql.PostgreSQLContainer instead").
- **Artifacts:** `org.testcontainers:testcontainers-postgresql`, `testcontainers-junit-jupiter` and `testcontainers-jdbc` first appear on Maven Central on 2025-10-14. The old ids `postgresql` and `junit-jupiter` end at 1.21.4. The core artifact stays `org.testcontainers:testcontainers`.
- **JUnit 4:** core 1.21.4 has `api 'junit:junit:4.13.2'` and `GenericContainer extends FailureDetectingExternalResource`. In 2.0.5 both are gone (`GenericContainer implements Container<SELF>, AutoCloseable, WaitStrategyTarget, Startable`).
- **Minimum Java:** not stated in any release note. The root `build.gradle` at 2.0.5 builds with a Java 17 toolchain but compiles main code with `options.release.set(8)`.

### Upgrade impact
- Dependencies: `org.testcontainers:junit-jupiter` → `org.testcontainers:testcontainers-junit-jupiter`; `org.testcontainers:postgresql` → `org.testcontainers:testcontainers-postgresql`; version `2.0.5` (or the BOM `org.testcontainers:testcontainers-bom`).
- Code: change the import to `org.testcontainers.postgresql.PostgreSQLContainer` and write `new PostgreSQLContainer("postgres:<tag>")` without `<?>`/`<>`. The old import still compiles with a deprecation warning.
- `@Testcontainers`/`@Container` stay in `org.testcontainers.junit.jupiter`.
- Any JUnit 4 `@Rule`/`@ClassRule` use of containers no longer compiles.
- 2.0.2+ defaults to Docker API 1.44, with fallback to 1.32 from 2.0.3.

---

## 7. Hibernate ORM 6.6 → 7.4

Sources:
- https://docs.hibernate.org/orm/7.0/migration-guide/, and likewise /7.1/, /7.2/, /7.3/, /7.4/ (full text read)
- https://docs.hibernate.org/orm/7.x/whats-new/ (headings only)
- https://docs.hibernate.org/orm/7.4/userguide/html_single/ (Maven plugin and settings sections)

Dates from Central POM timestamps: 7.0.0.Final 2025-05-19, 7.1.0.Final 2025-08-08, 7.2.0.Final 2025-12-12, 7.4.0.Final 2026-05-26. The 7.3.0.Final date could not be read.

### 7.0

**Baseline and platform**
- Licence changes to Apache 2.0.
- Requires Java 17 and Jakarta Persistence 3.2.
- Hibernate Models replaces HCANN.

**API removals**
- `Session#load` → `getReference`.
- `Session#save`/`update`/`saveOrUpdate` → `persist`/`merge`.
- `Session#delete` → `remove`.
- `Session#refresh(String entityName, …)` removed.
- `Session#get` deprecated in favour of `find`.
- `CascadeType#SAVE_UPDATE` and `#DELETE` removed. `org.hibernate.annotations.Cascade`/`CascadeType` are fully deprecated.

**Removed annotations**
- `@Persister`, `@Proxy`, `@SelectBeforeUpdate`, `@Loader`, `@GeneratorType`, `@LazyToOne`, `@LazyCollection`.
- Hibernate `@Table` → JPA `@Table`.
- `@Where`/`@WhereJoinTable` → `@SQLRestriction`/`@SQLJoinTableRestriction`.
- Hibernate `@OrderBy` → `@SQLOrder` or JPA `@OrderBy`.
- Hibernate `@ForeignKey`, `@Index` → JPA equivalents; `@IndexColumn` → `@OrderColumn`.
- `@Target` → `@TargetEmbeddable`.
- `@DynamicInsert#value`, `@DynamicUpdate#value`; `@Cache#include` → `includeLazy`.

**SPI**
- `Interceptor` deprecated methods removed; `EmptyInterceptor` removed.
- `Integrator#integrate` old signature removed.
- `UserType#nullSafeGet`/`nullSafeSet` signatures changed.
- `MetadataContributor` → `AdditionalMappingContributor`.
- Dialect-specific JDBC types such as `PostgreSQLEnumJdbcType` and `PostgreSQLUUIDJdbcType` moved to internal packages; use `@JdbcTypeCode`.

**Stricter mapping validation (now errors)**
- Conflicting attribute types, e.g. `@Basic`/`@Lob` together with `@ManyToOne`.
- Misplaced annotations, e.g. annotating a getter under FIELD access.
- `GenerationType.SEQUENCE` only with `@SequenceGenerator`; `TABLE` only with `@TableGenerator`.
- Non-JavaBean names like `String isDefault()`.
- `AttributeConverter` on `@Id`/`@Version`/`@Enumerated`/`@Embedded`/`@Temporal`/associations, including auto-applied ones (suppress with `@Convert(disableConversion=true)`).

**Behaviour**
- Persist/flush with a detached instance reachable via `CascadeType.ALL`/`PERSIST` throws `EntityExistsException`.
- Refreshing or locking detached entities throws; `hibernate.allow_refresh_detached_entity` removed.
- No implicit `cascade=PERSIST` for `@Id`/`@MapsId` associations.
- Native queries return `java.time` types (revert: `hibernate.query.native.prefer_jdbc_datetime_types`).
- Criteria string `get`/`join` on subtype attributes needs explicit `treat`.
- HQL without select list and without result type: ambiguous forms rejected.
- `StatelessSession` uses the second-level cache and ignores `hibernate.jdbc.batch_size`.
- Bulk update/delete on immutable entities fails (`hibernate.query.immutable_entity_update_query_handling_mode=allow`).
- Entity discovery by classpath scanning in standalone use needs the new `hibernate-scan-jandex` module.
- `ValidationMode#AUTO` propagates `ValidatorFactory` errors.
- `@OrderColumn` on unowned `@OneToMany` no longer issues extra UPDATEs when also mapped by a field.
- `SessionFactory` name / JNDI: new `hibernate.session_factory_jndi_name`.

**DDL**
- `char`/`Character` default DDL type `char(1)` → `varchar(1)`.
- Oracle/SQL Server timestamp precision; Oracle `binary_float`/`binary_double`.
- Arrays as `XML_ARRAY` on DB2/HANA/SQL Server/Sybase and `JSON_ARRAY` on MySQL/MariaDB (`hibernate.type.preferred_array_jdbc_type`).

**Settings and pools**
- Removed `hibernate.mapping.precedence` and friends.
- `LockOptions` deprecated.
- Vibur, Proxool and UCP pools dropped; `TeradataDialect` and `hibernate-ucp` dropped.

**Maven plugin**
- "The Maven bytecode enhancement plugin has been completely rewritten"; artifactId and groupId changed to `org.hibernate.orm:hibernate-maven-plugin`, goal `enhance`.
- By regression the defaults of `enableLazyInitialization` and `enableDirtyTracking` were `false` in 7.0.

**What's new (headings)**: `@SoftDelete` with TIMESTAMP, `@EmbeddedColumnNaming`, `@NamedEntityGraph`, `findMultiple()`/`getMultiple()`, `FindOption`/`LockOption`/`RefreshOption`, `QuerySpecification`, JSON/XML and set-returning functions, `@AnyDiscriminatorImplicitValues`, StatelessSession batch operations, `@CollectionIdJavaClass`.

### 7.1
- `LockOptions` constants and alias-specific lock modes removed; use `FindOption`/`LockOption`/`RefreshOption`.
- Bytecode enhancement: bidirectional association management and "extended" enhancement are deprecated and planned for removal.
- Re-enhancing a class with different options now throws `FeatureMismatchException`.
- SPI `EnhancementContext`: `doDirtyCheckingInline()`, `doExtendedEnhancement()`, `doBiDirectionalAssociationManagement()` no longer take class/field arguments.
- `Session#getLobHelper` deprecated in favour of static `Hibernate#getLobHelper`.
- H2 `wait`/`no wait`/`skip locked`.
- New `EventType.FORCE_INCREMENT`; pessimistic locking SPI reworked.
- Oracle OSON (`hibernate.dialect.oracle.oson_format_disabled`).
- DDL: automatic check constraints for `optional=false` subclass attributes in single-table inheritance.
- `hibernate-agroal` brings `agroal-pool` transitively.
- Maven plugin: `enableLazyInitialization` and `enableDirtyTracking` default to `true` again from 7.1.5.Final.
- `@Jpa` test utility methods removed (hibernate-testing).
- What's new: resource discovery in SE environments, locking, Interceptor and merge, exists queries.

### 7.2
- Building Hibernate itself needs Gradle 9 and Java 25; artifacts stay Java 17 compatible.
- `Session.contains(String,Object)` deprecated; `Session.byMultipleIds()` deprecated in favour of `findMultiple`.
- `AzureSQLServerDialect` deprecated.
- Child sessions sharing the transactional context are flushed/closed with the parent; flush events are now always fired.
- `noInterceptor()` semantics aligned (`noSessionInterceptorCreation()` to recover).
- `hibernate-testing` moved to JUnit 6.0; `BytecodeEnhancedTestEngine` is disabled by default (`hibernate.testing.bytecode.enhancement.extension.engine.enabled=true`).
- Registry generic signatures changed (SPI).
- What's new: `@EmbeddedTable`, read-only replicas, `FindMultipleOption`, child `StatelessSession`, vector module, `SchemaManager` enhancements, HQL regular expressions.

### 7.3
- `@Columns` removed.
- Classmate dependency and `ClassmateContext` removed; raw `Map` types removed from `org.hibernate.jpa.boot.spi.Bootstrap`.
- Collections of read-only entities are read-only; modifying them is an error.
- `getSingleResult()`/`getSingleResultOrNull()` always throw on more than one element (use `ResultListTransformer.uniqueResultTransformer()` for dedup).
- Timeouts that mark the transaction for rollback throw `PersistenceException` instead of `QueryTimeoutException`/`LockTimeoutException`.
- Schema actions (including `import.sql`) now run even without mapped entities.
- Envers `RelationTargetAuditMode.NOT_AUDITED` is respected.
- DDL: `@Version` columns are `not null` by default.
- Deps: Byte Buddy 1.18, Hibernate Models 1.1.
- What's new: `KeyType`, `@NaturalIdClass`, tenant-specific credentials, entities/embeddables without default constructor, Jackson 3 support.

### 7.4
- Oracle `current date`/`local date` translate to `trunc(current_date)`.
- `MySQLDialect` no longer defaults `hibernate.max_fetch_depth` to 2.
- Eager `@Any` is join-fetched when loading by id.
- Limit/pagination with collection fetch joins is now applied in SQL (hint `org.hibernate.limitInMemory` to recover).
- `SpannerPostgreSQLDialect` moved to `org.hibernate.dialect` in hibernate-core.
- From 7.4.4: `json_array`/`json_object` encode binary as HEX string on PostgreSQL/H2/CockroachDB/SQL Server; `json_value` expects HEX for binary.
- DDL: unique constraint for `@ElementCollection` sets; `@CreationTimestamp`/`@UpdateTimestamp` columns get NOT NULL; Oracle 23c value-based LOBs (`hibernate.dialect.oracle.value_lob_enabled=false`).
- Optional migration from Envers to core `@org.hibernate.annotations.Audited`.
- What's new: temporal and audited data, `CacheMode.REFRESH_SESSION`, reverse engineering and HBM transformation in the Maven plugin, `@OneToMany` with `@JoinFormula`, package-level `@Nationalized`.

### Maven plugin as documented in the 7.4 user guide (§29.6.1)

```xml
<plugin>
  <groupId>org.hibernate.orm</groupId>
  <artifactId>hibernate-maven-plugin</artifactId>
  <version>7.4.16.Final</version>
  <executions><execution><goals><goal>enhance</goal></goals></execution></executions>
</plugin>
```

| Parameter | Default | Status in 7.4 guide |
|---|---|---|
| `classesDirectory` | `{project.build.directory}/classes` | new |
| `fileSets` | none; takes precedence over `classesDirectory` | new |
| `enableLazyInitialization` | `true` | "deprecated for removal. After this removal, lazy loading will always be enabled" |
| `enableDirtyTracking` | `true` | deprecated for removal; will always be enabled |
| `enableAssociationManagement` | `false` | still documented; the feature itself is deprecated (7.1 guide and user guide §6.2.3) |
| `enableExtendedEnhancement` | `false` | still documented; "extended" enhancement deprecated in 7.1 |

The same goal set also offers reverse engineering and `transformHbm`.

### Settings in the 7.4 user guide
- `hibernate.dialect`: "As of Hibernate 6, this property should not be explicitly specified, except when using a custom user-written implementation of Dialect."
- `hibernate.enable_lazy_load_no_trans`: still present, default `false`, "considered unsafe … Use of this setting is discouraged."
- Not found in the guides: any change specific to `InheritanceType.JOINED`, or to `@Lob` on `byte[]`/bytea for PostgreSQL. They also do not say how `hbm2ddl.auto=update` treats the new constraints on existing tables.

### Upgrade impact
- **Plugin.** Replace `org.hibernate.orm.tooling:hibernate-enhance-maven-plugin` with `org.hibernate.orm:hibernate-maven-plugin` (goal `enhance`), using the Hibernate version Boot manages.
  - `enableLazyInitialization` and `enableDirtyTracking` can be dropped (default true from 7.1.5, deprecated).
  - `enableAssociationManagement` still works but is deprecated: plan to maintain both sides of bidirectional associations in code.
  - Do a clean build, because mixing enhancement options throws `FeatureMismatchException`.
- **Settings.** Remove the explicit `hibernate.dialect`. `hibernate.enable_lazy_load_no_trans` continues to work.
- **Code and mappings.** Check for the removed Hibernate annotations and removed `Session` methods (list under 7.0). Check for the 7.0 validations: `@Lob` combined with an association, annotations on the wrong access type, generator combinations, converters on `@Id`/`@Enumerated`. Check `EntityExistsException` for detached instances under cascade persist, and `getSingleResult()` with duplicates.
- **Schema with `ddl-auto: update`.** Expect DDL differences: `varchar(1)` for `char`, not-null `@Version` and timestamp-generator columns, unique constraints on `@ElementCollection` sets.

---

## 8. maven-compiler-plugin 3.11.0 → 3.16.0

Sources:
- https://github.com/apache/maven-compiler-plugin/releases/tag/maven-compiler-plugin-<version> (WebFetch)
- https://maven.apache.org/plugins/maven-compiler-plugin/compile-mojo.html (states "Version: 3.16.0")
- Dates from Maven Central

**3.12.0 (2023-12-15)**
- MCOMPILER-391: dependency management is used when resolving annotation processors and their dependencies (#180).
- MCOMPILER-381: incremental build detection reworked.
- MCOMPILER-562: `maven.compiler.outputDirectory`.
- MCOMPILER-542: JDK patch version stripped from `module-info.class`.
- MCOMPILER-333: generated source files cleaned up.
- MCOMPILER-544: unused classpath items no longer added.
- plexus-compiler 2.14.1.

**3.12.1 (2023-12-21)**
- MCOMPILER-567: compile failure with `generated-sources/annotations`.
- plexus-compiler 2.14.2.

**3.13.0 (2024-03-15)**
- MCOMPILER-583: requires Maven 3.6.3.
- MCOMPILER-582: automatic detection of the release option for JDK < 9.
- MCOMPILER-577: parameter `forceJavacCompilerUse` renamed.
- MCOMPILER-576: `compilerVersion` deprecated.
- MCOMPILER-548 (docs): JDK 21 annotation-processing warning.
- plexus-compiler 2.15.0.

**3.14.0 (2025-02-17)**
- MCOMPILER-579: module-version configuration.
- JDK 24 support via plexus-java 1.4.0.
- MCOMPILER-591: testCompile target detection fix.
- Build against Maven 3.9.9.

**3.14.1 (2025-09-18)**
- MCOMPILER-538: stop adding `target/generated-sources/annotations` to source roots (#191).
- `generatedSourcesPath` restored in the project (#312).
- `--module-version` optional (#331).
- DeltaList handling for large projects (#335).
- plexus-java 1.5.0.

**3.15.0 (2026-01-27)**
- Java 25 compatibility fix in ITs.
- MCOMPILER-540: `useIncrementalCompilation=false` could add generated sources to the sources list.
- plexus-compiler 2.16.2, asm 9.9.1.

**3.16.0 (2026-08-30)**
- Recompile when dependencies change (#1102).
- Incremental detection of empty sources (#1075).
- MCOMPILER-578: outputs tracked across compiler executions.
- Fix: build failure when the annotation processor list is present but empty (#1077).
- MCOMPILER-374: MR-JAR with `module-info.java`.
- plexus-compiler 2.17.0, plexus-java 1.6.0.

**4.0.0-beta-1 … beta-5** exist on Central (latest 2026-08-26). Their notes were not read; the task said this line is for Maven 4 only.

**Annotation processing (plugin doc for 3.16.0)**
- `<annotationProcessorPaths>`: unchanged semantics. The `<version>` of a path is optional and "taken from dependency management if not specified" (since 3.12.0, MCOMPILER-391). Exclusions are supported since 3.11.0.
- `<annotationProcessorPathsUseDepMgmt>`: since 3.12.0, default `false`. It only controls whether dependency management applies to the transitive dependencies of the processor paths.
- `<proc>`: values `none`, `only`, `full`; "`full` is the default. Starting with JDK 21, this option must be set explicitly." User property `maven.compiler.proc`.

### Upgrade impact
- Set the plugin to `3.16.0`; it needs Maven ≥ 3.6.3.
- The existing `annotationProcessorPaths` (Lombok, `lombok-mapstruct-binding`, `mapstruct-processor`, `mapstruct-spring-extensions`) keep working. Versions may now be omitted if managed.
- When compiling with JDK 21+, the docs say to set `<proc>full</proc>` explicitly. Because processors are given via `annotationProcessorPaths`, this mainly silences the javac note.
- Nothing else in the notes requires POM changes.

---

## 9. GraalVM Native Build Tools 0.10.4 → 1.1.x

Sources:
- https://github.com/graalvm/native-build-tools/releases, pages 1–3 and tag 1.0.0 (WebFetch)
- https://raw.githubusercontent.com/graalvm/native-build-tools/master/docs/src/docs/asciidoc/changelog.adoc (raw)
- `maven-plugin.adoc`

Maven Central's latest `native-maven-plugin` is `1.1.18` (2026-10-29). GitHub already shows a `1.1.19` tag (11 Nov) that was not on Central when checked.

**0.10.5 (2025-02-03, Central)**
- Improved GraalVM version check.
- Fix for `metadataCopy` with custom disabled stages.
- Docs rewritten.

**0.10.6 (2025-03-11, Central)**
- Maven: dependency exclusions.
- SBOM fixes.

**0.11.0 (2025-07-15, Central)**
- Breaking: "the plugins now require Java 17 to run" (#725).
- JUnit support refactored: no build-time-init class list needed from GraalVM for JDK 22; classpath-based fallback mode removed; `@FieldSource` support.
- Maven: integration tests via maven-failsafe-plugin; `provided` scope dependencies now included; `runtimeArgs`.
- Experimental layered images.

**0.11.1 (2025-09-25, Central)**
- `skipTestExecution` and `failNoTests` for `native-maven-plugin`.
- Default Maven phase binding removed from the `metadata-copy` goal (#748).
- JDK toolchain support.
- JUnit 5.14/6.0 types added.
- SBOM prepared for GraalVM 25.

**0.11.2**: missing JUnit types in build-time initialisation.
**0.11.3**: args-file fix when temp dir is on another drive; metadata 0.3.30.
**0.11.4 (2026-01-20, Central)**: dynamic access metadata with `--emit build-report`; compatibility mode support.

**0.11.5 (2026-03-03)**
- Auto-fallback to JVM JUnit when `-H:+CompatibilityMode` is set.
- Fix for JUnit 6 with JDK 21 (#832).
- Environment variables from the POM visible to the image builder (#819).
- Fallback for jarless artifacts.

**1.0.0 (2026-03-27, Central)**
- Breaking: moves to the `reachability-metadata.json` format and the newer metadata repository layout; the global `metadata/index.json` is no longer used.
- "Users may need to update dependencies or wait for dependent projects and metadata to adapt."

**1.0.1**: test resources included in Gradle `nativeTest`.

**1.1.0 (2026-04-22)**
- New `list-libraries-missing-metadata` goal (`./mvnw native:list-libraries-missing-metadata`).
- Metadata 1.0.0.

**1.1.1 – 1.1.5**: metadata handling fixes; Maven native test resource handling (#966); autodetected Jupiter extensions (#919); `systemPropertyVariables` used for test plugin injection (#902); CI on GraalVM for JDK 25.

**1.1.6 (2026-07-21)**
- Maven `skip` applies to all goals (#960).
- Native test goals read the matching Surefire/Failsafe execution.
- `native:metadata-copy` fix for reactor modules.
- Agent prefers GraalVM's Java (#833).

**1.1.8**: fallback plugin options deprecated (#997).
**1.1.9**: named Native Image layers (Maven `layer-create` / `useLayers`); consumption supported on GraalVM 25.1+.
**1.1.12**: Gradle native-image executable discovery.
**1.1.10 – 1.1.18 otherwise**: reachability-metadata bumps only (1.0.11 … 1.0.21).

**Required GraalVM version:** no release note or doc page I read states a minimum GraalVM version. What the docs do state:
- "The plugin requires that you install a GraalVM JDK".
- The plugin itself needs Java 17 since 0.11.0.
- Minimum JUnit is 5.8.1; native tests need Surefire ≥ 3.0.0.
- `--exact-reachability-metadata` exists from GraalVM for JDK 23.
- Layers need GraalVM 25.1+.

### Upgrade impact
- Set `org.graalvm.buildtools:native-maven-plugin` to `1.1.18`. Spring Boot's parent normally manages this version, so check alignment with the Boot line in use.
- Maven must run on JDK 17+.
- 1.0.0's metadata-format switch can change which reachability metadata is picked up: re-run the native build and native tests.
- If `metadata-copy` relied on its default phase, bind it explicitly.
- `provided` dependencies are now on the native-image classpath.

---

## 10. JUnit 5.11 → 6.0

Sources:
- https://docs.junit.org/6.0.0/release-notes.html
- https://docs.junit.org/5.12.0/release-notes/, /5.13.0/, /5.14.0/

Maven Central's latest is 6.1.3.

**5.12.0 (2025-02-21)**
- Jupiter breaking: `TestInfo`/`TestReporter` injected into test-class constructors now refer to the test method, unless the lifecycle is `PER_CLASS`.

**5.13.0 (2025-05-30)**
- Overall breaking: JUnit JARs no longer ship `native-image.properties`. "Please update to the most recent version of GraalVM Native Build Tools prior to upgrading"; the wiki has instructions for NBT 0.10.x or earlier.
- New: `@ClassTemplate`/`@ParameterizedClass`; discovery issue reporting.

**5.14.0 (2025-09-30)**
- Deprecations that ease migration: `org.junit.jupiter.api.extension.MediaType` → `org.junit.jupiter.api.MediaType`; `org.junit.jupiter.params.support.ParameterInfo` → `org.junit.jupiter.params.ParameterInfo`; `OutputDirectoryProvider` → `OutputDirectoryCreator`.

**6.0.0 (2025-09-30)**

Overall:
- Minimum Java 17, minimum Kotlin 2.2.
- Platform, Jupiter and Vintage share one version number.
- JSpecify nullability annotations on all modules.

Platform:
- `junit-platform-runner` (JUnit 4 `JUnitPlatform` runner) removed.
- `junit-platform-jfr` and `junit-platform-suite-commons` removed (merged into launcher / suite).
- Surefire/Failsafe < 3.0.0 unsupported.
- Many deprecated APIs removed.
- `junit.platform.reflection.search.useLegacySemantics` removed.

Jupiter breaking / deprecations:
- `MethodOrderer.Alphanumeric` removed.
- `InvocationInterceptor.interceptDynamicTest(Invocation, ExtensionContext)` removed.
- `junit.jupiter.tempdir.scope` no longer supported.
- `JRE.JAVA_8`…`JAVA_16` deprecated; `@EnabledForJreRange`/`@DisabledForJreRange` default `min` is `JAVA_17`.
- `@Nested` classes are ordered deterministically but intentionally non-obviously; `@TestMethodOrder` is now inherited by `@Nested` classes.
- `junit-jupiter-migrationsupport` deprecated.
- `@CsvSource`/`@CsvFileSource` moved to FastCSV: `lineSeparator` attribute removed, extra characters after a closing quote are rejected, exception types/messages may differ.
- `junit.jupiter.params.arguments.conversion.locale.format` removed.
- `ExtensionContext.Store#getOrComputeIfAbsent` deprecated in favour of `computeIfAbsent`.
- `TestTemplateInvocationContextProvider` return type changed to `Stream<? extends TestTemplateInvocationContext>`.
- Invalid values for enum-based configuration parameters now fail discovery/execution.
- Parameterised display names: `name = value` formatting and quoted text arguments.

Vintage:
- "The JUnit Vintage test engine is now deprecated and will report an INFO level discovery issue if it finds at least one JUnit 4 test class."
- Reporting can be disabled from 6.0.1 with `junit.vintage.discovery.issue.reporting.enabled=false`.

### Upgrade impact
- Plain `org.junit.jupiter.api` tests (`@Test`, `@BeforeEach`, `Assertions`, `@Nested`, `@DisplayName`) need no source changes.
- Check for: `MethodOrderer.Alphanumeric`; `@CsvFileSource(lineSeparator=…)`; `@EnabledOnJre`/`@EnabledForJreRange` with versions below 17; tests depending on `@Nested` class order or on exact parameterised display names; explicitly pinned `junit-platform-*` versions (now the same number as Jupiter).
- JUnit 4 tests still run through `junit-vintage-engine` 6.x, but it is deprecated. Testcontainers 2.x has no JUnit 4 integration anymore (see §6).
- Update Native Build Tools before or with JUnit ≥ 5.13.

---

## Sources that could not be read or only partly
- GitHub API and `github.com` HTML via curl were blocked in this session. All GitHub release notes came through WebFetch summaries, so item wording is paraphrased and dependency-only entries may be collapsed.
- springdoc GitHub releases page: not read; the CHANGELOG files were used instead.
- Testcontainers 1.21.4: the page contains only one sentence.
- maven-compiler-plugin 4.0.0-beta notes: not read.
- pgjdbc 42.7.6: no release date in `CHANGELOG.md`; the per-version pages under jdbc.postgresql.org/changelogs/ were not opened, only the index.
- Hibernate 7.3.0.Final release date and the Maven Central metadata of the old `org.hibernate.orm.tooling:hibernate-enhance-maven-plugin`: the requests returned nothing.
- JUnit 6 migration wiki referenced by the release notes: not read.

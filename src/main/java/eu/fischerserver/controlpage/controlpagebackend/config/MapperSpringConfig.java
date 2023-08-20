package eu.fischerserver.controlpage.controlpagebackend.config;

import eu.fischerserver.controlpage.controlpagebackend.util.mapper.ControlPageConversionServiceAdapter;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.extensions.spring.ExternalConversion;
import org.mapstruct.extensions.spring.SpringMapperConfig;

import java.util.Locale;

@MapperConfig(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        uses = ControlPageConversionServiceAdapter.class
)
@SpringMapperConfig(
        conversionServiceAdapterPackage = "eu.fischerserver.controlpage.controlpagebackend.util.mapper",
        conversionServiceAdapterClassName = "ControlPageConversionServiceAdapter",
        // Use Spring included converters: https://github.com/spring-projects/spring-framework/tree/main/spring-core/src/main/java/org/springframework/core/convert/support
        externalConversions = @ExternalConversion(sourceType = String.class, targetType = Locale.class)
)
public interface MapperSpringConfig {
}

package eu.fischerserver.controlpage.controlpagebackend.util.mapper.view;

import eu.fischerserver.controlpage.controlpagebackend.config.MapperSpringConfig;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import org.mapstruct.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MapperSpringConfig.class)
public interface BasicViewToViewEntityMapper extends Converter<BasicView, ViewEntity> {
    // TODO: javadoc
    @Override
    @Mappings({
            // TODO: implement fetch fields from DB
            @Mapping(target = "fields", ignore = true)
    })
    ViewEntity convert(@Nullable BasicView basicView);

    @Mappings({
            // TODO: implement fetch fields from DB
            @Mapping(target = "fields", ignore = true),
            @Mapping(target = "group", ignore = true)
    })
    @BeanMapping(ignoreUnmappedSourceProperties = "group")
    @Named("BasicViewToViewEntityWithoutGroupMapper")
    ViewEntity mapBasicViewToViewEntityWithoutGroup(@Nullable BasicView basicView);
}

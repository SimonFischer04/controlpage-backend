package eu.fischerserver.controlpage.controlpagebackend.model.dto;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ViewListResponse {
    List<BasicView> views;
}

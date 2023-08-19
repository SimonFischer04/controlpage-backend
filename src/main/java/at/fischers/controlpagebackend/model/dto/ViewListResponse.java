package at.fischers.controlpagebackend.model.dto;

import at.fischers.controlpagebackend.model.domain.view.BasicView;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ViewListResponse {
    List<BasicView> views;
}

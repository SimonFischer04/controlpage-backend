package at.fischers.controlpagebackend.controller.dto.response;

import at.fischers.controlpagebackend.dto.BasicView;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ViewListResponse {
    List<BasicView> views;
}

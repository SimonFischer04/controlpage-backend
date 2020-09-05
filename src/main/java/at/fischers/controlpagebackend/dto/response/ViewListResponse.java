package at.fischers.controlpagebackend.dto.response;

import at.fischers.controlpagebackend.entity.View;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ViewListResponse {
    List<View> views;
}

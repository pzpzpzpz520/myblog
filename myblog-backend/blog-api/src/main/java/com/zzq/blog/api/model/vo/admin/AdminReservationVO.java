package com.zzq.blog.api.model.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReservationVO<T> {

    private String module;
    private Boolean enabled;
    private String message;
    private List<String> plannedActions;
    private T snapshot;

    public static <T> AdminReservationVO<T> reserved(String module, String message, List<String> plannedActions, T snapshot) {
        return new AdminReservationVO<>(module, false, message, plannedActions, snapshot);
    }
}

package com.sumit.tableserve_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class IdsRequest {
    private List<String> ids;
}

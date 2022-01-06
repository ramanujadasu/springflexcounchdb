package com.springflexcounchdb.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractBase {

    private String createdBy;
    private Long createdOn;
    private String lastModifiedBy;
    private Long lastModifiedOn;
    @Min(0)
    @Max(1)
    private Short status;

}

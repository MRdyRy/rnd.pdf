package com.ryan.rnd.pdf.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SampleReq {
    private String fullName;
    private String position;
    private String nationality;
    private String price;
}

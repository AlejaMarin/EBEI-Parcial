package com.dh.series.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Season implements Serializable {

    private Long id;
    private Integer seasonNumber;
    private List<Chapter> chapters = new ArrayList<>();
}
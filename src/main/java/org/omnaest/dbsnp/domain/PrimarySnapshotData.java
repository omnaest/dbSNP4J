package org.omnaest.dbsnp.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrimarySnapshotData
{
    @JsonProperty("allele_annotations")
    private List<AlleleAnnotation> alleleAnnotations;

    public List<AlleleAnnotation> getAlleleAnnotations()
    {
        return this.alleleAnnotations;
    }

    @Override
    public String toString()
    {
        return "PrimarySnapshotData [alleleAnnotations=" + this.alleleAnnotations + "]";
    }

}
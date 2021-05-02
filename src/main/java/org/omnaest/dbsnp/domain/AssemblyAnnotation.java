package org.omnaest.dbsnp.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssemblyAnnotation
{
    @JsonProperty("genes")
    private List<Gene> genes;

    public List<Gene> getGenes()
    {
        return this.genes;
    }

    @Override
    public String toString()
    {
        return "AssemblyAnnotation [genes=" + this.genes + "]";
    }
}
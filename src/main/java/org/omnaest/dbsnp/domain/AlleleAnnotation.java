package org.omnaest.dbsnp.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlleleAnnotation
{
    @JsonProperty("assembly_annotation")
    private List<AssemblyAnnotation> assemblyAnnotations;

    public List<AssemblyAnnotation> getAssemblyAnnotations()
    {
        return this.assemblyAnnotations;
    }

    @Override
    public String toString()
    {
        return "AlleleAnnotation [assemblyAnnotations=" + this.assemblyAnnotations + "]";
    }
}
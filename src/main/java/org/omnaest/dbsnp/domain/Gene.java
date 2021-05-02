package org.omnaest.dbsnp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Gene
{
    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String locus;

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getLocus()
    {
        return this.locus;
    }

    @Override
    public String toString()
    {
        return "Gene [id=" + this.id + ", name=" + this.name + ", locus=" + this.locus + "]";
    }

}
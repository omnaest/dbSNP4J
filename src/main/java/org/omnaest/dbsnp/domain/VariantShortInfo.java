package org.omnaest.dbsnp.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VariantShortInfo
{
    @JsonProperty
    private String rsId;

    @JsonProperty
    private Set<String> mergedRsIds;

    @JsonProperty
    private Set<String> genes;

    protected VariantShortInfo()
    {
        super();
    }

    public VariantShortInfo(String rsId, Set<String> mergedRsIds, Set<String> genes)
    {
        super();
        this.rsId = rsId;
        this.mergedRsIds = mergedRsIds;
        this.genes = genes;
    }

    public String getRsId()
    {
        return this.rsId;
    }

    public Set<String> getMergedRsIds()
    {
        return this.mergedRsIds;
    }

    public Set<String> getGenes()
    {
        return this.genes;
    }

    @Override
    public String toString()
    {
        return "SimpleVariantInfo [rsId=" + this.rsId + ", mergedRsIds=" + this.mergedRsIds + ", genes=" + this.genes + "]";
    }

}
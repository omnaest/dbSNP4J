package org.omnaest.dbsnp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VariantMerge
{
    @JsonProperty("merged_rsid")
    private String mergedRsId;

    @JsonProperty("revision")
    private String revision;

    @JsonProperty("merge_date")
    private String mergeDate;

    public String getMergedRsId()
    {
        return this.mergedRsId;
    }

    public String getRevision()
    {
        return this.revision;
    }

    public String getMergeDate()
    {
        return this.mergeDate;
    }

    @Override
    public String toString()
    {
        return "VariantMerge [mergedRsId=" + this.mergedRsId + ", revision=" + this.revision + ", mergeDate=" + this.mergeDate + "]";
    }

}
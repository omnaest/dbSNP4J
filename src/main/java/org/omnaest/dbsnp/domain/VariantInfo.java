package org.omnaest.dbsnp.domain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VariantInfo
{
    @JsonProperty("refsnp_id")
    private String rsId;

    @JsonProperty("primary_snapshot_data")
    private PrimarySnapshotData primarySnapshotData;

    @JsonProperty("dbsnp1_merges")
    private List<VariantMerge> variantMerges;

    public String getRsId()
    {
        return this.rsId;
    }

    public PrimarySnapshotData getPrimarySnapshotData()
    {
        return this.primarySnapshotData;
    }

    @Override
    public String toString()
    {
        return "VariantInfo [rsId=" + this.rsId + ", primarySnapshotData=" + this.primarySnapshotData + ", variantMerges=" + this.variantMerges + "]";
    }

    public VariantShortInfo asVariantShortInfo()
    {
        Set<String> mergedRsIds = Optional.ofNullable(this.variantMerges)
                                          .orElse(Collections.emptyList())
                                          .stream()
                                          .map(VariantMerge::getMergedRsId)
                                          .collect(Collectors.toSet());
        Set<String> genes = Optional.ofNullable(this.primarySnapshotData)
                                    .map(PrimarySnapshotData::getAlleleAnnotations)
                                    .orElse(Collections.emptyList())
                                    .stream()
                                    .filter(alleleAnnotation -> alleleAnnotation != null)
                                    .map(AlleleAnnotation::getAssemblyAnnotations)
                                    .filter(assemblyAnnotations -> assemblyAnnotations != null)
                                    .flatMap(List<AssemblyAnnotation>::stream)
                                    .map(AssemblyAnnotation::getGenes)
                                    .filter(geneList -> geneList != null)
                                    .flatMap(List<Gene>::stream)
                                    .filter(gene -> gene != null)
                                    .map(Gene::getLocus)
                                    .filter(locus -> locus != null)
                                    .collect(Collectors.toSet());
        return new VariantShortInfo(this.rsId, mergedRsIds, genes);
    }
}
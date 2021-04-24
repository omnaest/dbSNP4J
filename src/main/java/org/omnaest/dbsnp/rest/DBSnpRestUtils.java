package org.omnaest.dbsnp.rest;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.omnaest.utils.CacheUtils;
import org.omnaest.utils.cache.Cache;
import org.omnaest.utils.rest.client.RestClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DBSnpRestUtils
{
    public static DBSnpRestAccessor newInstance()
    {
        return new DBSnpRestAccessor()
        {
            private Cache cache = CacheUtils.newNoOperationCache();

            @Override
            public DBSnpRestAccessor withLocalCache()
            {
                return this.withCache(CacheUtils.newLocalJsonFolderCache("cache/dbSnp/rest"));
            }

            public DBSnpRestAccessor withCache(Cache cache)
            {
                this.cache = cache;
                return this;
            }

            @Override
            public VariantInfo findVariant(String rsId)
            {
                return RestClient.newJSONRestClient()
                                 .withCache(this.cache)
                                 .request()
                                 .toUrl("https://api.ncbi.nlm.nih.gov/variation/v0/beta/refsnp/" + StringUtils.removeStart(rsId, "rs"))
                                 .get(VariantInfo.class);
            }
        };
    }

    public static interface DBSnpRestAccessor
    {
        public DBSnpRestAccessor withLocalCache();

        public VariantInfo findVariant(String rsId);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VariantInfo
    {
        @JsonProperty("refsnp_id")
        private String rsId;

        @JsonProperty("primary_snapshot_data")
        private PrimarySnapshotData primarySnapshotData;

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
            return "VariantInfo [rsId=" + this.rsId + ", primarySnapshotData=" + this.primarySnapshotData + "]";
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PrimarySnapshotData
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AlleleAnnotation
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AssemblyAnnotation
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Gene
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
}

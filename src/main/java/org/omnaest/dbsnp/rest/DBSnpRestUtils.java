package org.omnaest.dbsnp.rest;

import org.apache.commons.lang.StringUtils;
import org.omnaest.dbsnp.domain.VariantInfo;
import org.omnaest.utils.CacheUtils;
import org.omnaest.utils.cache.Cache;
import org.omnaest.utils.rest.client.RestClient;

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
}

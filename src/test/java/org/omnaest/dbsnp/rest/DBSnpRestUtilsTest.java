package org.omnaest.dbsnp.rest;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.dbsnp.rest.DBSnpRestUtils.VariantInfo;

public class DBSnpRestUtilsTest
{
    @Test
    @Ignore
    public void testNewInstance() throws Exception
    {
        VariantInfo variantInfo = DBSnpRestUtils.newInstance()
                                                .findVariant("rs10034957");
        System.out.println(variantInfo);
    }

}

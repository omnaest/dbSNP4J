package org.omnaest.dbsnp;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.dbsnp.DBSnpUtils.GeneAccessor;
import org.omnaest.dbsnp.DBSnpUtils.VariantAccessor;

public class DBSnpUtilsTest
{
    @Test
    @Ignore
    public void testGetGenes() throws Exception
    {
        VariantAccessor variant = DBSnpUtils.newInstance()
                                            .withLocalCache()
                                            .findVariant("rs10034957")
                                            .get();

        List<String> genes = variant.getGenes()
                                    .map(GeneAccessor::getSymbol)
                                    .collect(Collectors.toList());
        System.out.println(genes);
    }

}

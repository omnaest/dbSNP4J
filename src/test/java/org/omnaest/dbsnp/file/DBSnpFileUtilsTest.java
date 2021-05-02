package org.omnaest.dbsnp.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.dbsnp.domain.VariantInfo;
import org.omnaest.dbsnp.domain.VariantShortInfo;
import org.omnaest.utils.ClassUtils;
import org.omnaest.utils.ClassUtils.Resource;
import org.omnaest.utils.FileUtils;
import org.omnaest.utils.JSONHelper;
import org.omnaest.utils.JSONHelper.JsonStringSerializer;
import org.omnaest.utils.PeekUtils;

/**
 * @see DBSnpFileUtils
 * @author omnaest
 */
public class DBSnpFileUtilsTest
{

    @Test
    public void testReadVariantsFromBz2File() throws Exception
    {
        List<VariantInfo> variants = DBSnpFileUtils.readVariantsFromBz2File(ClassUtils.loadResource(this, "/refsnp-sample.json.bz2")
                                                                                      .map(Resource::asInputStream)
                                                                                      .orElseThrow(() -> new IllegalStateException("sample file missing in classpath")))
                                                   .collect(Collectors.toList());

        assertNotNull(variants);
        assertEquals(1, variants.size());

        List<VariantShortInfo> shortVariantInfos = variants.stream()
                                                           .map(VariantInfo::asVariantShortInfo)
                                                           .collect(Collectors.toList());
        assertEquals(1, shortVariantInfos.size());
        assertEquals("268", shortVariantInfos.get(0)
                                             .getRsId());
        assertEquals(Arrays.asList("LPL")
                           .stream()
                           .collect(Collectors.toSet()),
                     shortVariantInfos.get(0)
                                      .getGenes());
        assertEquals(Arrays.asList("17850737", "52818902", "386571803")
                           .stream()
                           .collect(Collectors.toSet()),
                     shortVariantInfos.get(0)
                                      .getMergedRsIds());

    }

    @Test
    @Ignore
    public void testExtract() throws FileNotFoundException, IOException
    {
        File directory = new File("T:\\\\T8\\\\databases\\\\dbsnp");
        File file = new File(directory, "refsnp-chr1.json.bz2");
        Stream<VariantInfo> variants = DBSnpFileUtils.readVariantsFromBz2File(file);

        JsonStringSerializer<Object> serializer = JSONHelper.serializer();
        FileUtils.toLineStreamConsumer(new File(directory, "\\extracted\\refsnp-chr1_extracted.json"))
                 .accept(variants.map(VariantInfo::asVariantShortInfo)
                                 .map(serializer)
                                 .peek(PeekUtils.counter(count -> System.out.println("" + count))));
    }

}

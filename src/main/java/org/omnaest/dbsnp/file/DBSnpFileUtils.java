package org.omnaest.dbsnp.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.omnaest.dbsnp.domain.VariantInfo;
import org.omnaest.utils.IOUtils;
import org.omnaest.utils.JSONHelper;
import org.omnaest.utils.JSONHelper.JsonStringDeserializer;
import org.omnaest.utils.PredicateUtils;
import org.omnaest.utils.zip.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBSnpFileUtils
{
    private static final Logger LOG = LoggerFactory.getLogger(DBSnpFileUtils.class);

    public static Stream<VariantInfo> readVariantsFromBz2File(File file) throws FileNotFoundException, IOException
    {
        return readVariantsFromBz2File(new FileInputStream(file));
    }

    public static Stream<VariantInfo> readVariantsFromBz2File(InputStream inputStream) throws IOException
    {
        Stream<String> lines = IOUtils.toLineStream(ZipUtils.read()
                                                            .fromBZ2(inputStream)
                                                            .asInputStream(),
                                                    StandardCharsets.UTF_8);
        JsonStringDeserializer<VariantInfo> deserializer = JSONHelper.deserializer(VariantInfo.class)
                                                                     .withExceptionHandler(e -> LOG.error("", e));
        return lines.filter(StringUtils::isNotBlank)
                    .map(deserializer)
                    .filter(PredicateUtils.notNull());
    }
}

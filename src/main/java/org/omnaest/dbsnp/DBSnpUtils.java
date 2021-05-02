package org.omnaest.dbsnp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.omnaest.dbsnp.domain.AlleleAnnotation;
import org.omnaest.dbsnp.domain.AssemblyAnnotation;
import org.omnaest.dbsnp.domain.PrimarySnapshotData;
import org.omnaest.dbsnp.domain.VariantInfo;
import org.omnaest.dbsnp.rest.DBSnpRestUtils;
import org.omnaest.dbsnp.rest.DBSnpRestUtils.DBSnpRestAccessor;
import org.omnaest.utils.PredicateUtils;

public class DBSnpUtils
{
    public static DBSnpAccessor newInstance()
    {
        DBSnpRestAccessor restAccessor = DBSnpRestUtils.newInstance();
        return new DBSnpAccessor()
        {
            @Override
            public DBSnpAccessor withLocalCache()
            {
                restAccessor.withLocalCache();
                return this;
            }

            @Override
            public Optional<VariantAccessor> findVariant(String rsId)
            {
                return Optional.ofNullable(restAccessor.findVariant(rsId))
                               .map(VariantAccessorImpl::new);
            }
        };
    }

    private static final class VariantAccessorImpl implements VariantAccessor
    {
        private final VariantInfo variantInfo;

        private VariantAccessorImpl(VariantInfo variantInfo)
        {
            this.variantInfo = variantInfo;
        }

        @Override
        public Stream<GeneAccessor> getGenes()
        {
            return Optional.ofNullable(this.variantInfo.getPrimarySnapshotData())
                           .map(PrimarySnapshotData::getAlleleAnnotations)
                           .map(List::stream)
                           .orElse(Stream.empty())
                           .filter(PredicateUtils.notNull())
                           .map(AlleleAnnotation::getAssemblyAnnotations)
                           .filter(PredicateUtils.notNull())
                           .flatMap(List::stream)
                           .filter(PredicateUtils.notNull())
                           .map(AssemblyAnnotation::getGenes)
                           .filter(PredicateUtils.notNull())
                           .flatMap(List::stream)
                           .filter(PredicateUtils.notNull())
                           .map(gene -> new GeneAccessor()
                           {
                               @Override
                               public String getSymbol()
                               {
                                   return gene.getLocus();
                               }

                               @Override
                               public String getName()
                               {
                                   return gene.getName();
                               }
                           });
        }
    }

    public static interface DBSnpAccessor
    {
        public DBSnpAccessor withLocalCache();

        Optional<VariantAccessor> findVariant(String rsId);
    }

    public static interface VariantAccessor
    {

        public Stream<GeneAccessor> getGenes();

    }

    public static interface GeneAccessor
    {
        public String getName();

        public String getSymbol();
    }
}

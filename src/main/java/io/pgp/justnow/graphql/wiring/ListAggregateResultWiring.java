package io.pgp.justnow.graphql.wiring;

import graphql.schema.idl.TypeRuntimeWiring;
import io.pgp.justnow.graphql.fetcher.ListFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class ListAggregateResultWiring {
    @Autowired
    private ListFetcher lists;

    public TypeRuntimeWiring.Builder getBuilder() {
        return newTypeWiring("ListAggregateResult")
                .dataFetcher("count", lists.getCount());
    }
}
